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

public class DeadswinesSlidingPanel extends FrameLayout implements View.OnClickListener, View.OnTouchListener {
    private final String TAG = this.getClass().getSimpleName();
    boolean isDebug = true;

    private void log(String log) {
        Log.d(TAG, log);
    }

    private int resourceId;


    private GestureDetector gestureDetector;
    private FrameLayout viewToSlide;

    private DeadswinesSlidingPanelChild child;


    private ArrayList<SlidingPanelStep> list;

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

        // child.setOnClickListener(this);
        child.setOnTouchListener(this);
        child.post(new Runnable() {
            @Override
            public void run() {
                list = child.calculateSteps();
                log(" We have this many views inside: " + list.size());

                for (int i = 0; i < list.size(); i++) {
                    log("We have view at y= " + list.get(i).getPositionY() + " with height= " + list.get(i).getHeight());
                }

                collapseWitchoutAnimation();

            }
        });

//        try {
//            child = (DeadswinesSlidingPanelChild) view;
//            addView(child);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new UnknownError("Provided view needs to be an instance of DeadswinesSlidingPanelChild this means you need to extend it");
//        }

    }

    public void setPositionInitial() {

    }

    boolean isExpanded;

    public void collapse() {

        viewToSlide.animate().y(0 - (child.getHeight() - list.get(list.size() - 1).getHeight())).start();

        isExpanded = false;
    }

    public void collapseWitchoutAnimation() {

        viewToSlide.setY(0 - (child.getHeight() - list.get(list.size() - 1).getHeight()));

        isExpanded = false;
    }

    public void expand() {
        viewToSlide.animate().y(0).start();
        isExpanded = true;
    }


    public void toggle() {
        if (isExpanded) {
            collapse();
        } else {
            expand();
        }
    }


    @Override
    public void onClick(View v) {
        toggle();
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // PARENT VIEW
        return false;
    }

    private float mPreviousX;
    private float mPreviousY;
    private float translate;
    @Override
    public boolean onTouch(View v, MotionEvent e) {

        if (gestureDetector.onTouchEvent(e)) {
            // single tap
            toggle();
            return true;
        } else {

            float x = e.getRawX(); // for onTouch listener we need to use raw data for ovveriding touch method we use getX() insted
            float y = e.getRawY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:

                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    translate = viewToSlide.getY() + dy;

                    if (translate >= -list.get(2).getPositionY()){
                        viewToSlide.setY(-list.get(2).getPositionY());
                    }else{
                        viewToSlide.setY(translate);
                    }

            }

            mPreviousX = x;
            mPreviousY = y;
            return true;
        }


    }


    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
}
