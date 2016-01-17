package com.deadswine.geocoderaddressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.deadswine.geocoder.address.view.R;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.12.2015.
 * Deadswine.com
 */

public class DeadswinesGeocoderAddressView extends FrameLayout implements AutoCompleteTextView.OnDismissListener, View.OnFocusChangeListener, TextWatcher, View.OnClickListener {
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

    AutoCompleteQuerrer mAutoCompleteQuerrer;


    boolean isTextEntered;
    boolean isVoiceInputInProgress;

    private void inflate() {

        viewBase = LayoutInflater.from(getContext()).inflate(resourceId, this, true);

        viewBackArrow = (ImageView) viewBase.findViewById(R.id.address_back_arrow);
        viewVoiceInput = (ImageView) viewBase.findViewById(R.id.address_voice);

        viewAutoComplete = (AutoCompleteTextView) viewBase.findViewById(R.id.address_autocomplete);

        viewAutoComplete.setOnDismissListener(this);
        viewAutoComplete.addTextChangedListener(this);
        viewBase.setOnFocusChangeListener(this);

        viewVoiceInput.setOnClickListener(this);


        viewAutoComplete.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private void init() {
        mAutoCompleteQuerrer = new AutoCompleteQuerrer(viewAutoComplete);
    }


    public void clear() {

        if (isVoiceInputInProgress) {
            voiceInputClear();

        }

        if (isTextEntered) {
            clearText();

        }

    }

    boolean isClearInProgress;

    public void clearText() {
        isClearInProgress = true;
        viewAutoComplete.setText("");
        isTextEntered = false;
        viewVoiceInput.setImageResource(R.drawable.ic_keyboard_voice_black_24dp);

        isClearInProgress = false;
    }


    public void startVoiceInput() {
        isVoiceInputInProgress = true;
        Toast.makeText(getContext(), "startVoiceInput - not finished", Toast.LENGTH_SHORT).show();
    }

    public void voiceInputClear() {
        isVoiceInputInProgress = false;
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        log("afterTextChanged: " + s.toString());
        if (s.length() == 0) {

            if (!isClearInProgress)
                clearText();

        } else if (s.length() == 1) {
            isTextEntered = true;
            viewVoiceInput.setImageResource(R.drawable.ic_close_black_24dp);

        }

    }

    @Override
    public void onClick(View v) {
        if (isTextEntered) {
            isClearInProgress = true;
            clearText();
        } else {
            startVoiceInput();
        }
    }
}
