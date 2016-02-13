package com.deadswine.views.slidingpanel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.02.2016.
 * Deadswine.com
 */

public abstract class DeadswinesSlidingPanelChild extends LinearLayout {
    private final String TAG = this.getClass().getSimpleName();

    boolean isDebug = true;

    public DeadswinesSlidingPanelChild(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void log(String log) {
        Log.d(TAG, log);
    }

    public ArrayList<SlidingPanelStep> calculateSteps() {

        return onCalculateSteps();
    }

    /**
     * This is called when panel calculates positions at witch it should stop <br><br>
     *     Note that handling gone views might be tricky so you have to test that<br>
     *         It would probably be best if you would have all children visible and then hide them
     * @return
     */
    public abstract ArrayList<SlidingPanelStep> onCalculateSteps();



}
