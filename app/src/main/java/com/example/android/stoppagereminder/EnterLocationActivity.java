package com.example.android.stoppagereminder;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import timber.log.Timber;

public class EnterLocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView txtLocationName, txtLocationAddress;
    private final String TAG = "StoppageReminder";
    GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    Place place;
    GeoFencing mGeoFencing;
    private final int PLACE_PICKER = 100;
    public static final int RC_SIGN_IN = 1;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Button btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_location);
        //Initialize Auth State Listener


        txtLocationName = (TextView) findViewById(R.id.txt_location_name);
        txtLocationAddress = (TextView) findViewById(R.id.txt_location_address);
        btnHelp = (Button) findViewById(R.id.btn_help);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();

        mGeoFencing = new GeoFencing(this, mGoogleApiClient);


        //Creating the action for HELP button
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnterLocationActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        btnHelp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.i("EnterAboutActivity","Long Clicked");
                Intent intent = new Intent(EnterLocationActivity.this,DomesticViolenceIntroActivity.class);
                startActivity(intent);
                return true;
            }
        });
        //sendNotification(EnterLocationActivity.this,1);
    }

    @Override
    public void onConnected(Bundle bundle) {
        //Implement
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Implement
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Implement
    }

    public void addPlace(View view) throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent i = builder.build(this);
        startActivityForResult(i, PLACE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PLACE_PICKER && resultCode == RESULT_OK) {
            place = PlacePicker.getPlace(this, data);
            if (place == null)
                Toast.makeText(this, "NO PLACE SELECTED", Toast.LENGTH_SHORT).show();
            else {
                String placeName = place.getName().toString();
                String placeAddress = place.getAddress().toString();
                String placeId = place.getId();
                txtLocationName.setText(placeName);
                txtLocationAddress.setText(placeAddress);
                mGeoFencing.updateGeofence(place);
                mGeoFencing.registerGeofence();
                Log.i("EnterLocationActivity", "Registered Sucessfully");
            }
        }
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED )
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET},
                    123);
        }
    }

    private void sendNotification(Context context, int transitionType) {
        // Create an explicit content Intent that starts the Application.
        Intent notificationIntent = new Intent(context, EnterLocationActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Add the launching Activity to the task stack as the parent.
        stackBuilder.addParentStack(EnterLocationActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        // Check the transition
        if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER || transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.ic_launcher))
                    .setContentTitle(" Chosen Location about to arrive");
        }

        // Continue building the notification
        builder.setContentText("Touch to Re-launch");
        builder.setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }
}