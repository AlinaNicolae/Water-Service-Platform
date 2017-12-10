package com.example.alina.waterserviceplatform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;

public class MapActivity extends AppCompatActivity {

    private MapView mMapView;
    private LocationDisplay mLocationDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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

}
