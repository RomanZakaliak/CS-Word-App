package com.example.CSWordApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("FieldCanBeLocal")
public class RegistrationActivity extends AppCompatActivity {

    private Button btnRegistration;
    private TextView btnTextLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        btnRegistration = findViewById(R.id.registrationButton);

        btnRegistration.setOnClickListener(v -> {
                    Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
                    startActivity(intent);
                }
        );

        btnTextLogin = findViewById(R.id.loginRegistration);
        btnTextLogin.setOnClickListener(v -> {
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
        );
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {    //закрити віртуальну клаву, якщо клік за межі поля вводу
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int[] scrCoordinates = new int[2];
            w.getLocationOnScreen(scrCoordinates);
            float x = event.getRawX() + w.getLeft() - scrCoordinates[0];
            float y = event.getRawY() + w.getTop() - scrCoordinates[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }


}