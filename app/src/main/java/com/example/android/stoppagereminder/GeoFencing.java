package com.example.android.stoppagereminder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.Place;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static timber.log.Timber.i;

/**
 * Created by HP on 13-01-2018.
 */

public class GeoFencing implements ResultCallback {

    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    private PendingIntent mGeofencePendingIntent;
    ArrayList<Geofence> mGeofenceList = new ArrayList<>();

    public GeoFencing(Context context, GoogleApiClient client){
        mGoogleApiClient = client;
        mContext = context;
    }

    public void registerGeofence(){
        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    getGeofencingRequest(),
                    getGeofencePendingIntent()).setResultCallback(this);
            Log.i("GeoFencing","Sucessful Register");

        }catch (SecurityException securityException){
            Log.i("GeoFencing","Register unsucessful");
        }
    }

    public void updateGeofence(Place place){
        String placeUID = place.getId();
        double placeLat = place.getLatLng().latitude;
        double placeLng = place.getLatLng().longitude;
        Geofence geofence = new Geofence.Builder()
                .setRequestId(placeUID)
                .setCircularRegion(placeLat,placeLng,50)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL)
                .setExpirationDuration(20000000)
                .setLoiteringDelay(1)
                .build();
        mGeofenceList.add(geofence);
        Log.i("GeoFencing","Updating Geofence");
    }

    private GeofencingRequest getGeofencingRequest(){
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.addGeofences(mGeofenceList);
        return builder.build();

    }

    private PendingIntent getGeofencePendingIntent(){
        if(mGeofencePendingIntent != null){
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(mContext,GeofenceBroadcastReciever.class);
        mGeofencePendingIntent = PendingIntent.getBroadcast(mContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }

    @Override
    public void onResult(@NonNull Result result) {

    }
}