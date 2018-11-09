package com.nicolemosiexample.motor;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nicolemosiexample.motor.AppConfig.GEOMETRY;
import static com.nicolemosiexample.motor.AppConfig.GOOGLE_BROWSER_API_KEY;
import static com.nicolemosiexample.motor.AppConfig.LATITUDE;
import static com.nicolemosiexample.motor.AppConfig.LOCATION;
import static com.nicolemosiexample.motor.AppConfig.LONGITUDE;
import static com.nicolemosiexample.motor.AppConfig.NAME;
import static com.nicolemosiexample.motor.AppConfig.OK;
import static com.nicolemosiexample.motor.AppConfig.PROXIMITY_RADIUS;
import static com.nicolemosiexample.motor.AppConfig.RESULTS;
import static com.nicolemosiexample.motor.AppConfig.STATUS;
import static com.nicolemosiexample.motor.AppConfig.VICINITY;
import static com.nicolemosiexample.motor.AppConfig.ZERO_RESULTS;

public class Garages extends AppCompatActivity implements OnMapReadyCallback  {

    MapView map;
    GoogleMap gmap;
    RecyclerView recyclerView;
    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mFusedLocationProviderClient;
    ArrayList<String> npNames, npCords, npLocation, npVicinity;
    boolean mLocationPermissionGranted = false;
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 21;
    private Location mLastKnownLocation;
    private static final int DEFAULT_ZOOM = 15;
    private static final String TAG = Garages.class.getSimpleName();
    private final LatLng mDefaultLocation = new LatLng(1.2921, 36.8219);
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_gyms);

        // Get bundle
        extras = getIntent().getExtras();

        // What's needed
        // Needed manenos
        mGeoDataClient = Places.getGeoDataClient(this);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Init
        map = findViewById(R.id.mapView);
        map.onCreate( savedInstanceState );
        map.getMapAsync( this );

        // Arraylists
        npNames = new ArrayList<>();
        npCords = new ArrayList<>();
        npLocation = new ArrayList<>();
        npVicinity = new ArrayList<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        // Manenos
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
    }

    public void showResults() {
        // Recycler view
        recyclerView = findViewById(R.id.recyclerview_nearbyplaces);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomNearbyGarages customNearbyGara = new CustomNearbyGarages(this, npNames, npCords, npLocation, npLocation, gmap, extras);
        recyclerView.setAdapter(customNearbyGara);
    }

    private void showOnMap(){
        gmap.clear();

        for( int i=0; i < npCords.size(); ++i ) {
            String coords[] = npCords.get( i ).split(",");

            MarkerOptions markerOptions = new MarkerOptions();
            LatLng latLng = new LatLng(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
            markerOptions.position(latLng);
            markerOptions.title(npNames.get(i));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_report_black_24dp));

            gmap.addMarker(markerOptions);
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private void updateLocationUI() {
        if (gmap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                gmap.setMyLocationEnabled(true);
                gmap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                gmap.setMyLocationEnabled(true);
                gmap.getUiSettings().setMyLocationButtonEnabled(true);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            getCloseGyms();
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            gmap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void getCloseGyms() {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(mLastKnownLocation.getLatitude()).append(",").append(mLastKnownLocation.getLongitude());
        googlePlacesUrl.append("&radius=").append("50000");
        googlePlacesUrl.append("&types=").append("gym");
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);

        RequestQueue queue = Volley.newRequestQueue(this );
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, googlePlacesUrl.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        parseLocationResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Garages.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);
    }

    public void parseLocationResult(JSONObject result) {
        try {
            JSONArray jsonArray = result.getJSONArray(RESULTS);

            if (result.getString(STATUS).equalsIgnoreCase(OK)) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place = jsonArray.getJSONObject(i);

                    npNames.add(place.getString(NAME));
                    npCords.add(Double.toString(place.getJSONObject(GEOMETRY).getJSONObject(LOCATION).getDouble(LATITUDE)) + "," + Double.toString(place.getJSONObject(GEOMETRY).getJSONObject(LOCATION).getDouble(LONGITUDE)));
                    npLocation.add("");
                    npVicinity.add(place.getString(VICINITY));
                }

                showOnMap();
                showResults();
            } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
                Toast.makeText(Garages.this, "No gyms available", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(Garages.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }
    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        map.onStop();
    }
    @Override
    protected void onPause() {
        map.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        map.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }
}