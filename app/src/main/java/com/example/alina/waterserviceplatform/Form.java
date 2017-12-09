package com.example.alina.waterserviceplatform;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;


import java.io.File;

public class Form extends AppCompatActivity {

    private MapView mMapView;
    private LocationDisplay mLocationDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        boolean flag = false;

        try {
            File file;
            String path = Environment.getExternalStorageDirectory().toString();
            file = new File(path, "leakage2.png");
            if (!file.exists()) {
                file = new File(path, "leakage.png");
                flag = true;
            }
            MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            if (flag == false) {
                Bitmap bitmap = BitmapFactory.decodeFile(path + "/leakage2.png");
            }
            Bitmap bitmap = BitmapFactory.decodeFile(path + "/leakage.png");
            ImageView image = (ImageView) findViewById(R.id.imageView);
            image.setImageBitmap(bitmap);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView next = (ImageView) findViewById(R.id.imageView);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Form.this, CameraView.class);
                startActivity(i);
            }
        });

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Form.this, SuccessfulSubmission.class);
////                EditText editText = (EditText) findViewById(R.id.kenyanID);
////                i.putExtra("kenyanID", editText.getText().toString());
//                startActivity(i);
                Toast.makeText(Form.this, "Your report was send successfully!",
                        Toast.LENGTH_LONG*2).show();
                Intent i = new Intent(Form.this, DrawerActivity.class);
                startActivity(i);
            }
        });

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
                //Intent i = new Intent(SecondActivity.this,MainActivity.class);
                //startActivity(i);
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
