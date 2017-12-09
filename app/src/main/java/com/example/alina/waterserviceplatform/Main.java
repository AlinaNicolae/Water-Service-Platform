package com.example.alina.waterserviceplatform;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt = (EditText) findViewById(R.id.kenyanID);
                String kenyanID = txt.getText().toString();
                TextInputLayout til = (TextInputLayout) findViewById(R.id.textInputLayout3);
                if (kenyanID == null || kenyanID.isEmpty() || !kenyanID.matches("[a-zA-Z0-9]*")) {
                    til.setError("You need to enter your id");
                } else {
                    til.setError(null);
                    Intent i = new Intent(Main.this, DrawerActivity.class);
//                EditText editText = (EditText) findViewById(R.id.kenyanID);
//                i.putExtra("kenyanID", editText.getText().toString());
                    startActivity(i);
                }
            }
        });

        EditText txt = (EditText) findViewById(R.id.kenyanID);
        txt.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            Intent i = new Intent(Main.this, DrawerActivity.class);
                            EditText editText = (EditText) findViewById(R.id.kenyanID);
                            String kenyanID = editText.getText().toString();
                            TextInputLayout til = (TextInputLayout) findViewById(R.id.textInputLayout3);
                            if (kenyanID == null || kenyanID.isEmpty() || !kenyanID.matches("[a-zA-Z0-9]*")) {
                                til.setError("You need to enter your id");
                            } else {
                                til.setError(null);
                                i.putExtra("kenyanID", kenyanID);
                                startActivity(i);
                                return true;
                            }
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }

}
