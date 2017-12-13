package com.example.alina.waterserviceplatform;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        if (kenyanID == null || kenyanID.isEmpty() || !kenyanID.matches("[0-9]*") || kenyanID.length() != 8) {
                            til.setError("You need to enter your id like 12345678");
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

        setupUI(findViewById(R.id.parent));

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(Main.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

}
