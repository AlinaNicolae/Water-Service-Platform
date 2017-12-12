package com.example.alina.waterserviceplatform;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.Map;

public class MapActivity extends AppCompatActivity {

    private final String[] reqPermissions =
            new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION };
    private final int requestCode = 2;

    private MapView mMapView;
    private LocationDisplay mLocationDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // if permissions are not already granted, request permission from the user
        if (!(ContextCompat.checkSelfPermission(MapActivity.this, reqPermissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(MapActivity.this, reqPermissions[1])
                == PackageManager.PERMISSION_GRANTED)) {
            int requestCode = 2;
            ActivityCompat.requestPermissions(MapActivity.this, reqPermissions, requestCode);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // inflate MapView from layout
        mMapView = (MapView) findViewById(R.id.mapView);
        // create a map with the BasemapType topographic
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 60.184140, 24.830084, 16);
        // set the map to be displayed in this view
        mMapView.setMap(map);

        // get the MapView's LocationDisplay
        mLocationDisplay = mMapView.getLocationDisplay();

        LinearLayout locationLayout= findViewById(R.id.location);
        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapActivity.this, Form.class);
                startActivity(i);
            }
        });
        TextView locationTextOne= findViewById(R.id.editText);
        locationTextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapActivity.this, Form.class);
                startActivity(i);
            }
        });
        TextView locationTextTwo= findViewById(R.id.info);
        locationTextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapActivity.this, Form.class);
                startActivity(i);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Form.this,MapActivity.class);
                //Intent i = new Intent(SecondActivity.this,MainActivity.class);
//                startActivity(i);
                 mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
                 if (!mLocationDisplay.isStarted())
                    mLocationDisplay.startAsync();
            }
        });
    }

    private void onClickLocation(){


    };

      @Override
      protected void onPause(){
    mMapView.pause();
     super.onPause();
      }

      @Override
     protected void onResume(){
         super.onResume();
     mMapView.resume();
     }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Location permission was granted. This would have been triggered in response to failing to start the
            // LocationDisplay, so try starting this again.
            mLocationDisplay.startAsync();
            //captureScreenshotAsync();
        } else {
            // If permission was denied, show toast to inform user what was chosen. If LocationDisplay is started again,
            // request permission UX will be shown again, option should be shown to allow never showing the UX again.
            // Alternative would be to disable functionality so request is not shown again.
            Toast.makeText(MapActivity.this, "Location permission denied", Toast
                    .LENGTH_SHORT).show();

        }
    }

}
