package com.deadswine.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button btnFabMorph;
    Button btnFabMorphGeocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnFabMorph = (Button) findViewById(R.id.btnFabMorph);
        btnFabMorphGeocoder = (Button) findViewById(R.id.btnFabMorphGeocoder);

        btnFabMorph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FabMorphActivity.class);

                startActivity(intent);
            }
        });

        btnFabMorphGeocoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FabMorphGeocoderActivity.class);

                startActivity(intent);
            }
        });
    }
}
