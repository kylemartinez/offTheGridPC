package com.ironcoder.participantcenter;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.ironcoder.participantcenter.API.PCLoginAPI;


public class PCLogin extends Activity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    private static final String TAG = "OFFTHEGRID";

    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL =
            MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;

    // Define an object that holds accuracy and frequency parameters
    private LocationRequest locationRequest;
    private boolean updatesRequested = true;

    private TextView longitude;
    private TextView latitude;

    private LocationClient locationClient;
    private Location currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pclogin);

        locationRequest = LocationRequest.create();
        // Use high accuracy
        locationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        locationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        longitude = (TextView)findViewById(R.id.longitude);
        latitude = (TextView)findViewById(R.id.latitude);

        locationClient = new LocationClient(this, this, this);
        Log.d(TAG, "connecting location client");
        locationClient.connect();

    }


    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        locationClient.connect();
    }

    @Override
    protected void onStop() {
        if (locationClient.isConnected()) {
            /*
             * Remove location updates for a listener.
             * The current Activity is the listener, so
             * the argument is "this".
             */
            locationClient.removeLocationUpdates(this);
        }
        locationClient.disconnect();
        super.onStop();
    }

    private void updateLocation(){
        Log.d(TAG, Double.toString(currentLocation.getLatitude()));
        longitude.setText(Double.toString(currentLocation.getLongitude()));
        latitude.setText(Double.toString(currentLocation.getLatitude()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pclogin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates",
                    "Google Play services is available.");
            return true;

        }

        Log.d(TAG, "services not connected");
        return false;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (updatesRequested) {
            locationClient.requestLocationUpdates(locationRequest, this);
        }
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        currentLocation = locationClient.getLastLocation();
        updateLocation();
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
        locationClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            //showErrorDialog(connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        currentLocation = location;
        updateLocation();
    }


}
