package com.example.alina.waterserviceplatform;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
            Bitmap bitmap = BitmapFactory.decodeFile(path + "/leakage.png");
            if (flag == false) {
                bitmap = BitmapFactory.decodeFile(path + "/leakage2.png");
            }
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Form.this, "Your report was sent successfully!",
                        Toast.LENGTH_LONG*2).show();
                Intent i = new Intent(Form.this, DrawerActivity.class);
                startActivity(i);
            }
        });

        ImageView fab = (ImageView) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Form.this,MapActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");

        // save photo to file
        FileOutputStream out = null;
        try {
            File file;
            String path = Environment.getExternalStorageDirectory().toString();
            file = new File(path, "leakage.png");
            if (file.exists()) {
                file = new File(path, "leakage2.png");
            }
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent i = new Intent(Form.this, Form.class);
        startActivity(i);
    }

}
