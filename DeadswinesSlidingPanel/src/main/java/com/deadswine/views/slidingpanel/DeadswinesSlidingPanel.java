package com.deadswine.views.slidingpanel;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.02.2016.
 * Deadswine.com
 */

public class DeadswinesSlidingPanel extends FrameLayout implements View.OnTouchListener, View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    boolean isDebug = true;

    private void log(String log) {
        Log.d(TAG, log);
    }

    private int resourceId;

    private boolean isSwipingEnabled;
    private boolean isClickingEnabled;
    private boolean isShowing;

    private int lockCollapse = 1;
    private int lockExpand = 1;

    private GestureDetector gestureDetector;
    private FrameLayout viewToSlide;
    private DeadswinesSlidingPanelChild child;
    private ArrayList<SlidingPanelStep> listSteps;


    {
        isSwipingEnabled = false;
        isClickingEnabled = false;
        isShowing = false;
    }


    public DeadswinesSlidingPanel(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DeadswineViewsSlidingPanel, 0, 0);
        try {
            resourceId = ta.getResourceId(R.styleable.DeadswineViewsSlidingPanel_slidingPanelLayoutResource, 0);
        } finally {
            ta.recycle(); // remember to recycle

            if (resourceId == 0) {
                throw new UnknownError("Provided resource value is 0");
            }
        }

        gestureDetector = new GestureDetector(getContext(), new SingleTapConfirm());

        inflate();

    }

    private void inflate() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_sliding_panel, this, true);
        viewToSlide = (FrameLayout) view.findViewById(R.id.viewToSlide);
        child = (DeadswinesSlidingPanelChild) LayoutInflater.from(getContext()).inflate(resourceId, viewToSlide, false);
        viewToSlide.addView(child);

        child.setOnTouchListener(this);
        child.post(new Runnable() {
            @Override
            public void run() {
                listSteps = child.calculateSteps();
                log(" We have this many views inside: " + listSteps.size());

                for (int i = 0; i < listSteps.size(); i++) {
                    log("We have view at y= " + listSteps.get(i).getPositionY() + " with height= " + listSteps.get(i).getHeight());
                }

                hide();
            }
        });

    }

    boolean isExpanded;

    public void collapse() {
        log("collapse: " + lockCollapse);
        isExpanded = false;

        float position = listSteps.get(listSteps.size() - (lockCollapse + 1)).getPositionY() + listSteps.get(listSteps.size() - (lockCollapse + 1)).getHeight();
        //   float position = listSteps.get(lockCollapse).getPositionY();   // listSteps.get(listSteps.size() - 1).getHeight()

        viewToSlide.animate().y(0 - (child.getHeight() - position)).start();
    }


    public void collapseWitchoutAnimation() {

        viewToSlide.setY(0 - (child.getHeight() - listSteps.get(listSteps.size() - 1).getHeight()));

        isExpanded = false;
    }

    public void expand() {
        log("expand: " + (lockExpand));
        isExpanded = true;


        float position = listSteps.get(listSteps.size() - (lockExpand + 1)).getPositionY() + listSteps.get(listSteps.size() - (lockExpand + 1)).getHeight();
        viewToSlide.animate().y(-(child.getHeight() - position)).start();
    }

    public void toggle() {
        if (isExpanded) {
            collapse();
        } else {
            expand();
        }
    }

    public void show() {
        log("show");
        collapse();
    }

    public void hide() {
        log("hide");
        viewToSlide.animate().y(-child.getHeight() - 10).start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // PARENT VIEW
        return false;
    }

    private float mPreviousX;
    private float mPreviousY;
    private float translate;
    private boolean direction;

    @Override
    public boolean onTouch(View v, MotionEvent e) {

        if (isClickingEnabled && gestureDetector.onTouchEvent(e)) {
            // single tap
            toggle();
            return true;
        } else if (isSwipingEnabled) {

            float x = e.getRawX(); // for onTouch listener we need to use raw data for ovveriding touch method we use getX() insted
            float y = e.getRawY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:

                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    translate = viewToSlide.getY() + dy;

                    if (y <= mPreviousY) {
                        direction = true;
                    } else {
                        direction = false;
                    }

                    if (translate >= -listSteps.get(lockExpand).getPositionY()) {
                        //    viewToSlide.setY(-listSteps.get(lockExpand).getPositionY());
                        translate = listSteps.get(listSteps.size() - (lockExpand + 1)).getPositionY() + listSteps.get(listSteps.size() - (lockExpand + 1)).getHeight();
                        //   viewToSlide.animate().y(0 - (child.getHeight() - position)).start();
                        viewToSlide.setY(0 - (child.getHeight() - translate));

                    } else if (translate <= -listSteps.get(lockCollapse).getPositionY()) {
                        //    viewToSlide.setY(-listSteps.get(lockCollapse).getPositionY());
                        translate = listSteps.get(listSteps.size() - (lockCollapse + 1)).getPositionY() + listSteps.get(listSteps.size() - (lockCollapse + 1)).getHeight();
                        viewToSlide.setY(0 - (child.getHeight() - translate));

                    } else {
                        viewToSlide.setY(translate);
                    }

                    break;

                case MotionEvent.ACTION_UP:

                    if (direction) {
                        collapse();
                    } else {
                        expand();
                    }

                    break;


            }


            mPreviousX = x;
            mPreviousY = y;
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onClick(View v) {
        toggle();
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }


    public boolean isSwipingEnabled() {
        return isSwipingEnabled;
    }

    public void setSwiping(boolean isSwipingEnabled) {
        this.isSwipingEnabled = isSwipingEnabled;
    }

    public boolean isClickingEnabled() {
        return isClickingEnabled;
    }

    public void setClicking(boolean isClickingEnabled) {
        this.isClickingEnabled = isClickingEnabled;
        log("setClicking: " + this.isClickingEnabled);
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean isShowing) {
        this.isShowing = isShowing;
        if (isShowing) {
            show();
        } else {
            hide();
        }
    }


    public int getLockExpand() {
        return lockExpand;
    }

    public void setLockExpand(int lockExpand) {
        this.lockExpand = lockExpand;
        log("LockExpand: " + lockExpand);
        expand();
    }

    public int getLockCollapse() {
        return lockCollapse;
    }

    public void setLockCollapse(int lockCollapse) {
        this.lockCollapse = lockCollapse;
        log("LockExpand: " + lockCollapse);
        collapse();
    }
}
