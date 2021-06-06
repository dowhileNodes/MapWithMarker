package com.example.swabtesttracker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.swabtesttracker.databinding.ActivityMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private DatabaseReference reference;
    private LocationManager manager;

   private final int MIN_TIME = 1000;
    private final int MIN_DISTANCE = 1;

    ArrayList<LatLng>arrayList=new ArrayList<LatLng>();
    LatLng Marikina = new LatLng(14.6507, 121.1029);
    LatLng Muntinlupa = new LatLng(14.4081, 121.0415);
    LatLng Manila = new LatLng(14.5995, 120.9842);
    LatLng Mandaluyong = new LatLng(14.5794, 121.0359);
    LatLng QuezonCity = new LatLng(14.6760, 121.0437);
    LatLng Makati = new LatLng(14.5547, 121.0244);
    LatLng Taguig = new LatLng(14.5176, 121.0509);
    LatLng Pasig = new LatLng(14.5764, 121.0851);
    LatLng Laspinas = new LatLng(14.4445, 120.9939);
    LatLng Valenzuela = new LatLng(14.7011, 120.9830);

    ArrayList<String>title=new ArrayList<String>();

    Marker myMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        reference = FirebaseDatabase.getInstance().getReference().child("SWAB");
        //FirebaseDatabase.getInstance().getReference().setValue("This is swab testing tracker");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        arrayList.add(Marikina);
        arrayList.add(Muntinlupa);
        arrayList.add(Manila);
        arrayList.add(Mandaluyong);
        arrayList.add(QuezonCity);
        arrayList.add(Taguig);
        arrayList.add(Makati);
        arrayList.add(Pasig);
        arrayList.add(Laspinas);
        arrayList.add(Valenzuela);


        title.add("Marikina");
        title.add("Muntinlupa");
        title.add("Manila");
        title.add("Mandaluyong");
        title.add("QuezonCity");
        title.add("Taguig");
        title.add("Makati");
        title.add("Pasig");
        title.add("Las Pinas");
        title.add("Valenzuela");

        getLocationUpdates();
        readChanges();
    }

    private void readChanges() {
      reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        MyLocation location = dataSnapshot.getValue(MyLocation.class);
                        if (location != null) {
                               myMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                        }
                    } catch (Exception e) {
                        Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getLocationUpdates() {
        if(manager != null){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                   manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, (LocationListener) this);

                } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, (LocationListener) this);

                } else {
                    Toast.makeText(this, "No provider Enables", Toast.LENGTH_SHORT).show();
      }
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
           }
       }
  }
   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 101){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocationUpdates();
     }else{
              Toast.makeText(this, "permission required", Toast.LENGTH_SHORT).show();
          }
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      for (int i=0;i<arrayList.size();i++){
          //mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Marker"));
          //mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
         // mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
          for (int j=0;j<title.size();j++){
              mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(j))));
          }
          mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
      }
       //mMap.setMinZoomPreference(12);
       mMap.getUiSettings().setZoomControlsEnabled(true);
       mMap.getUiSettings().setAllGesturesEnabled(true);

       mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
           @Override
           public boolean onMarkerClick(Marker marker) {

               String markertitle = marker.getTitle();

               Intent i = new Intent(MapsActivity.this, DetailsActivity.class);
               i.putExtra("title",markertitle);
               startActivity(i);
               return false;
           }
       });

    }

   //@Override
   public void onLocationChanged(@NonNull Location location) {
        if(location != null) {
            saveLocation(location);
        }else{
          Toast.makeText(this,"No Location", Toast.LENGTH_SHORT).show();
       }
   }

   private void saveLocation(Location location) {
        reference.setValue(location);
    }
}