package in.grappes.ecommerce.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.utils.SharedPrefUtils;
import in.grappes.ecommerce.utils.ToolbarUtils;

public class LocationDetectionActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final int LOCATION_PERMISSION_CODE = 12212;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private DatabaseReference mDatabase;

    LocationManager locationManager ;
    boolean GpsStatus ;
    View proceedBtn;
    View useGpsBtn;
    AutoCompleteTextView acTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detection);

        setViews();

        ToolbarUtils.setToolbar((ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)), LocationDetectionActivity.this, getResources().getString(R.string.location));

        setupGoogleAPIClient();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                Map<String, String> post = (HashMap<String, String>) dataSnapshot.getValue(Map.class);

                Map<String, String> localities = new Gson().fromJson(new Gson().toJson(dataSnapshot.getValue()), HashMap.class);
                ArrayList<String> list = new ArrayList<String>();
                if(localities!=null) {
                    list.addAll(localities.values());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(LocationDetectionActivity.this,android.R.layout.select_dialog_singlechoice, list);
                //Find TextView control
                //Set the number of characters the user must type before the drop down list is shown
                acTextView.setThreshold(1);
                //Set the adapter
                acTextView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(LocationDetectionActivity.this,""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        };
        mDatabase.child("Localities").addListenerForSingleValueEvent(postListener);

    }

    private void setViews() {
        acTextView = (AutoCompleteTextView) findViewById(R.id.location_edit_text);
        proceedBtn = findViewById(R.id.proceed_btn);
        useGpsBtn = findViewById(R.id.use_gps_btn);
        useGpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGpsStatus();
            }
        });

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(acTextView.getText()!=null)
                {
                    SharedPrefUtils.setUserLocation(LocationDetectionActivity.this, acTextView.getText().toString());
                    startHomeActivity();
                }
                else
                {
                    Toast.makeText(LocationDetectionActivity.this, getResources().getString(R.string.enter_location), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupGoogleAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onResume() {
        mGoogleApiClient.connect();
        super.onResume();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("onConnected", "true");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_CODE);
            Log.e("No Accesss", "1");
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null) {

            Geocoder gcd = new Geocoder(LocationDetectionActivity.this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses.size() > 0)
            {
                SharedPrefUtils.setUserLocation(LocationDetectionActivity.this, addresses.get(0).getLocality());
                startHomeActivity();
            }
            else
            {
                Toast.makeText(LocationDetectionActivity.this,getResources().getString(R.string.error_location), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Log.e("location", "last location null");
        }
    }

    private void startHomeActivity() {
        Intent i = new Intent(LocationDetectionActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    public void checkGpsStatus(){

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(GpsStatus == true)
        {
            mGoogleApiClient.connect();
        }
        else
        {
            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);
        }

    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.e("onConnectionSuspended", ""+i);
    }
}
