package com.deadswine.views.SampleSlidingPanel;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.deadswine.views.R;
import com.deadswine.views.slidingpanel.DeadswinesSlidingPanel;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.02.2016.
 * Deadswine.com
 */

public class SlidingPanelActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();

    boolean isDebug = true;

    public void log(String log) {
        Log.d(TAG, log);
    }

    DeadswinesSlidingPanel panel;

    Switch cbSwiping;
    Switch cbShowing;
    Switch cbClicking;

    Button btnTop1;
    Button btnTop2;
    Button btnTop3;
    Button btnTop4;

    Button btnBottom1;
    Button btnBottom2;
    Button btnBottom3;
    Button btnBottom4;

    Button btnDisabledTop;
    Button btnDisabledBottom;

    Button btnExpand;
    Button btnColapse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sample_sliding_panel);
        panel = (DeadswinesSlidingPanel) findViewById(R.id.panel);

        cbSwiping = (Switch) findViewById(R.id.checkboxSwiping);
        cbShowing = (Switch) findViewById(R.id.checkboxShowing);
        cbClicking = (Switch) findViewById(R.id.checkboxClicking);
        cbSwiping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                panel.setSwiping(isChecked);
            }
        });

        cbShowing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                panel.setShowing(isChecked);
            }
        });

        cbClicking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                panel.setClicking(isChecked);
            }
        });

        btnTop1 = (Button) findViewById(R.id.btnTop1);
        btnTop1.setOnClickListener(this);
        btnTop2 = (Button) findViewById(R.id.btnTop2);
        btnTop2.setOnClickListener(this);
        btnTop3 = (Button) findViewById(R.id.btnTop3);
        btnTop3.setOnClickListener(this);
        btnTop4 = (Button) findViewById(R.id.btnTop4);
        btnTop4.setOnClickListener(this);

        btnBottom1 = (Button) findViewById(R.id.btnBottom1);
        btnBottom1.setOnClickListener(this);
        btnBottom2 = (Button) findViewById(R.id.btnBottom2);
        btnBottom2.setOnClickListener(this);
        btnBottom3 = (Button) findViewById(R.id.btnBottom3);
        btnBottom3.setOnClickListener(this);
        btnBottom4 = (Button) findViewById(R.id.btnBottom4);
        btnBottom4.setOnClickListener(this);


        btnExpand =(Button) findViewById(R.id.btnExpand);
        btnColapse =(Button) findViewById(R.id.btnCollapse);

        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel.expand();
                btnColapse.setEnabled(true);
                btnExpand.setEnabled(false);
            }
        });

        btnColapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel.collapse();
                btnColapse.setEnabled(false);
                btnExpand.setEnabled(true);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnTop1:
                panel.setLockExpand(0);
                if(btnDisabledTop !=null){
                    btnDisabledTop.setEnabled(true);
                }

                btnTop1.setEnabled(false);
                btnDisabledTop = btnTop1;
                break;

            case R.id.btnTop2:
                panel.setLockExpand(1);
                if(btnDisabledTop !=null){
                    btnDisabledTop.setEnabled(true);
                }

                btnTop2.setEnabled(false);
                btnDisabledTop = btnTop2;
                break;

            case R.id.btnTop3:
                panel.setLockExpand(2);

                if(btnDisabledTop !=null){
                    btnDisabledTop.setEnabled(true);
                }

                btnTop3.setEnabled(false);
                btnDisabledTop = btnTop3;
                break;

            case R.id.btnTop4:
                panel.setLockExpand(3);

                if(btnDisabledTop !=null){
                    btnDisabledTop.setEnabled(true);
                }

                btnTop4.setEnabled(false);
                btnDisabledTop = btnTop4;
                break;

            ////////////////

            case R.id.btnBottom1:
                panel.setLockCollapse(0);

                if(btnDisabledBottom !=null){
                    btnDisabledBottom.setEnabled(true);
                }

                btnBottom1.setEnabled(false);
                btnDisabledBottom = btnBottom1;

                break;

            case R.id.btnBottom2:
                panel.setLockCollapse(1);

                if(btnDisabledBottom !=null){
                    btnDisabledBottom.setEnabled(true);
                }

                btnBottom2.setEnabled(false);
                btnDisabledBottom = btnBottom2;
                break;

            case R.id.btnBottom3:
                panel.setLockCollapse(2);

                if(btnDisabledBottom !=null){
                    btnDisabledBottom.setEnabled(true);
                }

                btnBottom3.setEnabled(false);
                btnDisabledBottom = btnBottom3;
                break;

            case R.id.btnBottom4:
                panel.setLockCollapse(3);

                if(btnDisabledBottom !=null){
                    btnDisabledBottom.setEnabled(true);
                }

                btnBottom4.setEnabled(false);
                btnDisabledBottom = btnBottom4;
                break;

        }

    }
}
