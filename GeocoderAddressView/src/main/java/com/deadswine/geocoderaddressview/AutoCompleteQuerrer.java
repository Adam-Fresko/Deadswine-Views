package com.deadswine.geocoderaddressview;

import android.content.Context;
import android.util.Log;
import android.widget.AutoCompleteTextView;


import com.deadswine.geocoder.address.view.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Adam Fręśko - Deadswine Studio on 13.12.2015.
 * Deadswine.com
 */

public class AutoCompleteQuerrer implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, AdapterView.OnItemClickListener, ResultCallback<PlaceBuffer> {
    private final static String TAG = "AutoCompleteQuerrer";
    boolean isDebug = true;

    public static void log(String log) {
        Log.d(TAG, log);
    }


    /**
     * GoogleApiClient wraps our service connection to Google Play Services and provides access
     * to the user's sign in state as well as the Google's APIs.
     */
    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mAdapter;

    private AutoCompleteTextView mAutocompleteView;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

//

    AutoCompleteTextView autoTv;
    Context mContext;

    public AutoCompleteQuerrer(AutoCompleteTextView tv) {

        mAutocompleteView = tv;
        mContext = mAutocompleteView.getContext();

        // Construct a GoogleApiClient for the {@link Places#GEO_DATA_API} using AutoManage
        // functionality, which automatically sets up the API client to handle Activity lifecycle
        // events. If your activity does not extend FragmentActivity, make sure to call connect()
        // and disconnect() explicitly.
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                // .enableAutoManage(mContext, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .build();

        mGoogleApiClient.connect();


    }

//    private AdapterView.OnItemClickListener mAutocompleteClickListener
//            = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            /*
//             Retrieve the place ID of the selected item from the Adapter.
//             The adapter stores each Place suggestion in a AutocompletePrediction from which we
//             read the place ID and title.
//              */
//            final AutocompletePrediction item = mAdapter.getItem(position);
//            final String placeId = item.getPlaceId();
//            final CharSequence primaryText = item.getPrimaryText(null);
//
//            Log.i(TAG, "Autocomplete item selected: " + primaryText);
//
//
//            /*
//             Issue a request to the Places Geo Data API to retrieve a Place object with additional
//             details about the place.
//              */
//            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
//                    .getPlaceById(mGoogleApiClient, placeId);
//            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
//
//            Toast.makeText(mContext, "Clicked: " + primaryText,
//                    Toast.LENGTH_SHORT).show();
//            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
//        }
//    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
//    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
//            = new ResultCallback<PlaceBuffer>() {
//        @Override
//        public void onResult(PlaceBuffer places) {
//            if (!places.getStatus().isSuccess()) {
//                // Request did not complete successfully
//                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
//                places.release();
//                return;
//            }
//            // Get the Place object from the buffer.
//            final Place place = places.get(0);
//
//
//            Log.i(TAG, "Place details received: " + place.getName());
//            Log.i(TAG, "Place details received: " + place.getLatLng());
//            Log.i(TAG, "Place details received: " + place.getAddress());
//            places.release();
//        }
//    };
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        log("details: " + name + "  id: " + id + "  address: " + address + "  phoneNumber: " + phoneNumber + " website: " + websiteUri);
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(mContext,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(mContext,
                "onConnected ",
                Toast.LENGTH_SHORT).show();

        // Register a listener that receives callbacks when a suggestion has been selected
        mAutocompleteView.setOnItemClickListener(this);

        mAdapter = new PlaceAutocompleteAdapter(mContext, mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);
        mAutocompleteView.setAdapter(mAdapter);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
        final AutocompletePrediction item = mAdapter.getItem(position);
        final String placeId = item.getPlaceId();
        final CharSequence primaryText = item.getPrimaryText(null);

        Log.i(TAG, "Autocomplete item selected: " + primaryText);


            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(mGoogleApiClient, placeId);
        placeResult.setResultCallback(this);

        Toast.makeText(mContext, "Clicked: " + primaryText,
                Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
    }

    @Override
    public void onResult(PlaceBuffer places) {
        if (!places.getStatus().isSuccess()) {
            // Request did not complete successfully
            Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
            places.release();
            return;
        }
        // Get the Place object from the buffer.
        final Place place = places.get(0);

        Log.i(TAG, "Place details received: " + place.getName());
        Log.i(TAG, "Place details received: " + place.getLatLng());
        Log.i(TAG, "Place details received: " + place.getAddress());
        places.release();
    }
}
