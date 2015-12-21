package com.deadswine.geocoderaddressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.12.2015.
 * Deadswine.com
 */

public class DeadswinesGeocoderAddressView extends FrameLayout implements AutoCompleteTextView.OnDismissListener, View.OnFocusChangeListener {
    private final String TAG = this.getClass().getSimpleName();
    boolean isDebug = true;

    public void log(String log) {
        Log.d(TAG, log);
    }
//

    int resourceId;

    public DeadswinesGeocoderAddressView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DeadswineViewsGeocoder, 0, 0);
        try {
            resourceId = ta.getResourceId(R.styleable.DeadswineViewsGeocoder_geocoderLayoutResource, 0);
        } finally {
            ta.recycle(); // remember to recycle
        }


        inflate();
    }

    View viewBase;
    ImageView viewBackArrow;
    ImageView viewVoiceInput;
    AutoCompleteTextView viewAutoComplete;

    AutoCompleteQuerrer  mAutoCompleteQuerrer;

    private void inflate() {
        viewBase = LayoutInflater.from(getContext()).inflate(resourceId, this, true);

        viewBackArrow = (ImageView) viewBase.findViewById(R.id.address_back_arrow);
        viewVoiceInput = (ImageView) viewBase.findViewById(R.id.address_voice);

        viewAutoComplete = (AutoCompleteTextView) viewBase.findViewById(R.id.address_autocomplete);
        viewAutoComplete.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });

       viewAutoComplete.setOnDismissListener(this);
        viewBase.setOnFocusChangeListener(this);
    }

    private void init(){
        mAutoCompleteQuerrer = new AutoCompleteQuerrer(viewAutoComplete);
    }

    @Override
    public void onDismiss() {

//        if(viewBase!=null) {
//            viewBase.setBackgroundResource(R.drawable.drawable_rounded_corners);
//        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

//        if(viewBase!=null) {
//            if ( viewBase.hasFocus()){
//
//                viewBase.setBackgroundResource(R.drawable.drawable_rounded_corners_bottom);
//
//            }
//
//        }

    }
}
