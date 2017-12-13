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

import java.io.File;
import java.util.ArrayList;

public class CardView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        ImageView[] images = new ImageView[6];
        images[0] = (ImageView) findViewById(R.id.cardView1);
        images[1] = (ImageView) findViewById(R.id.cardView2);
        int cnt = 0;

        try {
            String path = Environment.getExternalStorageDirectory().toString();
            File[] directoryListing = Environment.getExternalStorageDirectory().listFiles();
            for (File file : directoryListing) {
                if (file.isFile()) {
                    MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(path + "/" + file.getName());
                    ImageView image = images[cnt];
                    image.setImageBitmap(bitmap);
                    image.setScaleType(ImageView.ScaleType.FIT_XY);
                    cnt += 1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        FloatingActionButton submit = (FloatingActionButton) findViewById(R.id.fab);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CardView.this, CameraView.class);
                startActivity(i);
            }
        });

    }
}
