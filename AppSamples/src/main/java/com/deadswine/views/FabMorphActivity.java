package com.deadswine.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.12.2015.
 * Deadswine.com
 */

public class FabMorphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_morph);
    }


    private final String TAG = this.getClass().getSimpleName();


    boolean isDebug = true;

    public void log(String log) {
        Log.d(TAG, log);
    }
}
