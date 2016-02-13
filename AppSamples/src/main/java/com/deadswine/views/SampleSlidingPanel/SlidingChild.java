package com.deadswine.views.SampleSlidingPanel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.deadswine.views.slidingpanel.DeadswinesSlidingPanelChild;
import com.deadswine.views.slidingpanel.SlidingPanelStep;

import java.util.ArrayList;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.02.2016.
 * Deadswine.com
 */

public class SlidingChild extends DeadswinesSlidingPanelChild {
    private final String TAG = this.getClass().getSimpleName();
    boolean isDebug = true;


    public SlidingChild(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void log(String log) {
        Log.d(TAG, log);
    }



    @Override
    public ArrayList<SlidingPanelStep> onCalculateSteps() {

        ArrayList<SlidingPanelStep> list = new ArrayList<>();
        for (int i = 0; i <getChildCount() ; i++) {

            list.add(new SlidingPanelStep(getChildAt(i).getY(),getChildAt(i).getHeight()));
        }

        return list;
    }
}
