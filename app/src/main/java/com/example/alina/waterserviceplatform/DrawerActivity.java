package com.example.alina.waterserviceplatform;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.*;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private final String[] reqPermissions =
            new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE };
    int requestCode = 2;

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;

    int resume_var = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


        // if permissions are not already granted, request permission from the user
        if (!(ContextCompat.checkSelfPermission(DrawerActivity.this, reqPermissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(DrawerActivity.this, reqPermissions[1])
                == PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(DrawerActivity.this, reqPermissions, requestCode);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 0);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView[] images = new ImageView[6];
        images[0] = (ImageView) findViewById(R.id.cardView1);
        images[1] = (ImageView) findViewById(R.id.cardView2);
        int cnt = 0;

        String path = Environment.getExternalStorageDirectory().toString();
        File[] directoryListing = Environment.getExternalStorageDirectory().listFiles();
        for (File file : directoryListing) {
            if (file.isFile()) {
                try {
                    MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

                    if (file.getName().equals("leakage.png")) {
                        CardView cd1 = (CardView) findViewById(R.id.cd1);
                        cd1.setVisibility(View.VISIBLE);
                    }

                    if (file.getName().equals("leakage2.png")) {
                        CardView cd2 = (CardView) findViewById(R.id.cd2);
                        cd2.setVisibility(View.VISIBLE);
                        cd2.setCardBackgroundColor(Color.parseColor("#32CD32"));
                        TextView cd2_txt1 = (TextView) findViewById(R.id.cd2_txt1);
                        TextView cd2_txt2 = (TextView) findViewById(R.id.cd2_txt2);
                        cd2_txt1.setText("Solved");
                        cd2_txt2.setText("12/10/2017: Severe leakage damage in Nairobi");

                        CardView cd1 = (CardView) findViewById(R.id.cd1);
                        cd1.setCardBackgroundColor(Color.parseColor("#FFFF00"));
                        TextView cd1_txt1 = (TextView) findViewById(R.id.cd1_txt1);
                        TextView cd1_txt2 = (TextView) findViewById(R.id.cd1_txt2);
                        cd1_txt1.setText("In progress");
                        cd1_txt2.setText("Today: We will attend to your problem as soon as possible");

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        Bitmap bitmap = BitmapFactory.decodeFile(path + "/leakage.png");
                        ImageView image = images[1];
                        image.setImageBitmap(bitmap);
                        image.setScaleType(ImageView.ScaleType.FIT_XY);

                        bitmap = BitmapFactory.decodeFile(path + "/leakage2.png");
                        image = images[0];
                        image.setImageBitmap(bitmap);
                        image.setScaleType(ImageView.ScaleType.FIT_XY);
                    } else {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        Bitmap bitmap = BitmapFactory.decodeFile(path + "/" + file.getName());
                        ImageView image = images[cnt];
                        image.setImageBitmap(bitmap);
                        image.setScaleType(ImageView.ScaleType.FIT_XY);
                        cnt += 1;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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

        Intent i = new Intent(DrawerActivity.this, Form.class);
        startActivity(i);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

//        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

//        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            captureScreenshotAsync();
        } else {
            // If permission was denied, show toast to inform user what was chosen. If LocationDisplay is started again,
            // request permission UX will be shown again, option should be shown to allow never showing the UX again.
            // Alternative would be to disable functionality so request is not shown again.
            Toast.makeText(DrawerActivity.this, "Camera permission denied", Toast
                    .LENGTH_SHORT).show();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        String path = Environment.getExternalStorageDirectory().toString();
        File file = new File(path + "/leakage");
        file.delete();

        file = new File(path + "/leakage2");
        file.delete();
    }
}
