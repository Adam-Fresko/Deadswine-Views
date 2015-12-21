package com.deadswine.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.deadswine.geocoderaddressview.DeadswinesGeocoderAddressView;
import com.deadswine.views.fab.morph.DeadswineFabMorphLayoutAdvanced;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.12.2015.
 * Deadswine.com
 */

public class FabMorphGeocoderActivity extends AppCompatActivity implements DeadswineFabMorphLayoutAdvanced.DeadswineFabMorphInterface {


    DeadswineFabMorphLayoutAdvanced deadswineFabMorphLayoutAdvanced;
    DeadswinesGeocoderAddressView deadswinesGeocoderAddressView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geocoder_sample);


        deadswineFabMorphLayoutAdvanced = (DeadswineFabMorphLayoutAdvanced) findViewById(R.id.deadswineFabMorphAddress);
        deadswineFabMorphLayoutAdvanced.setInterface(this);
    }


    private final String TAG = this.getClass().getSimpleName();


    boolean isDebug = true;

    public void log(String log) {
        Log.d(TAG, log);
    }

    @Override
    public void onDeadswineMorphInitialized() {
       // deadswinesGeocoderAddressView = (DeadswinesGeocoderAddressView) ;


        deadswineFabMorphLayoutAdvanced.getTargetView().findViewById(R.id.address_back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deadswineFabMorphLayoutAdvanced.toggle();
            }
        });
    }
}
