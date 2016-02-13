package com.deadswine.views.SampleSlidingPanel;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.deadswine.views.R;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.02.2016.
 * Deadswine.com
 */

public class SlidingPanelActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();


    boolean isDebug = true;

    public void log(String log) {
        Log.d(TAG, log);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sample_sliding_panel);
    }
}
