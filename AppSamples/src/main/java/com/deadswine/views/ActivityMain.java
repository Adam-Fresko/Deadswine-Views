package com.deadswine.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.deadswine.views.SampleSlidingPanel.SlidingPanelActivity;

public class ActivityMain extends AppCompatActivity {


    Button btnFabMorph;
    Button btnFabMorphGeocoder;
    Button btnSlidingPanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnFabMorph = (Button) findViewById(R.id.btnFabMorph);
        btnFabMorphGeocoder = (Button) findViewById(R.id.btnFabMorphGeocoder);
        btnSlidingPanel= (Button) findViewById(R.id.btnSlidingPanel);

        btnFabMorph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this, FabMorphActivity.class);

                startActivity(intent);
            }
        });

        btnFabMorphGeocoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this, FabMorphGeocoderActivity.class);

                startActivity(intent);
            }
        });

        btnSlidingPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this, SlidingPanelActivity.class);

                startActivity(intent);
            }
        });



    }
}
