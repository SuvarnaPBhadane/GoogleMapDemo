package com.example.googlemapdemo;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapMarkerActivity extends FragmentActivity implements OnMapReadyCallback {


    GoogleMap mMap;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_marker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        SupportMapFragment mapFrag = (SupportMapFragment)
                getSupportFragmentManager().
                        findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        if(mMap != null) {

            mMap.setOnMapLongClickListener(new
                                                   GoogleMap.OnMapLongClickListener() {
                                                       @Override
                                                       public void onMapLongClick (LatLng latLng){
                                                           Geocoder geocoder =
                                                                   new Geocoder(MapMarkerActivity.this);
                                                           List<Address> list;
                                                           try {
                                                               list = geocoder.getFromLocation(latLng.latitude,
                                                                       latLng.longitude, 1);
                                                           } catch (IOException e) {
                                                               return;
                                                           }
                                                           Address address = list.get(0);
                                                           if (marker != null) {
                                                               marker.remove();
                                                           }

                                                           MarkerOptions options = new MarkerOptions()
                                                                   .title(address.getLocality())
                                                                   .position(new LatLng(latLng.latitude,
                                                                           latLng.longitude));

                                                           marker = mMap.addMarker(options);
                                                       }
                                                   });
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final  GoogleMap googleMap) {
    this.mMap = googleMap;
    }


    public void findLocation(View v) throws IOException {

        EditText et = (EditText)findViewById(R.id.editText);
        String location = et.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        LatLng ll = new LatLng(add.getLatitude(), add.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
        mMap.moveCamera(update);
        if(marker != null)
            marker.remove();
        MarkerOptions markerOptions = new MarkerOptions()
                .title(locality)
                .position(new LatLng(add.getLatitude(), add.getLongitude()));
        marker = mMap.addMarker(markerOptions);

    }
}
