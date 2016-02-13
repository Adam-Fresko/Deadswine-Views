package com.deadswine.views.slidingpanel;

import android.util.Log;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.02.2016.
 * Deadswine.com
 */

public class SlidingPanelStep {


    float positionY;
    int height;

    public SlidingPanelStep(float positionY, int height) {
        this.positionY = positionY;
        this.height = height;
    }


    public float getPositionY() {
        return positionY;
    }

    public int getHeight() {
        return height;
    }
}
