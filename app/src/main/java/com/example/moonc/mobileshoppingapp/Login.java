package com.example.moonc.mobileshoppingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    LinearLayout appName;
    EditText lgUsername, lgPassword;
    Button lgSubmit, lgRegister;

    private static int count = 0;
    private static long startTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        appName = findViewById(R.id.appName);
        lgUsername = findViewById(R.id.lgUsername);
        lgPassword = findViewById(R.id.lgPassword);
        lgSubmit = findViewById(R.id.lgSubmit);
        lgRegister = findViewById(R.id.lgRegister);


        appName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = System.currentTimeMillis();

                if (startTime == 0 || (time - startTime > 3000)) {
                    startTime = time;
                    count = 1;
                } else
                    count++;
                if (count == 2)
                    Toast.makeText(Login.this, "Tap 3 more times for logging in as administrator.", Toast.LENGTH_SHORT).show();
                else if (count == 3)
                    Toast.makeText(Login.this, "Tap 2 more times for logging in as administrator.", Toast.LENGTH_SHORT).show();
                else if (count == 4)
                    Toast.makeText(Login.this, "Tap 1 more time for logging in as administrator.", Toast.LENGTH_SHORT).show();
                else if (count == 5) {

                    lgUsername.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    lgPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

                    if (lgUsername.getText().toString().isEmpty() || lgPassword.getText().toString().isEmpty()) {

                        if (lgUsername.getText().toString().isEmpty())
                            lgUsername.setError("Username cannot be empty!");

                        if (lgPassword.getText().toString().isEmpty())
                            lgPassword.setError("Password cannot be empty!");

                    } else {

                        DBHelper helper = new DBHelper(Login.this);

                        if (helper.checkValidAdmin(lgUsername.getText().toString(), lgPassword.getText().toString())) {

                            CurrentUser.setCurrentUser(lgUsername.getText().toString());
                            startActivity(new Intent(Login.this, AdminPanel.class));
                            finish();
                            Toast.makeText(Login.this, "Administrator Login Successful.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(Login.this, "Invalid Login.", Toast.LENGTH_SHORT).show();

                        helper.close();
                    }
                }
            }
        });

        lgSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lgUsername.onEditorAction(EditorInfo.IME_ACTION_DONE);
                lgPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

                if (lgUsername.getText().toString().isEmpty() || lgPassword.getText().toString().isEmpty()) {

                    if (lgUsername.getText().toString().isEmpty())
                        lgUsername.setError("Username cannot be empty!");

                    if (lgPassword.getText().toString().isEmpty())
                        lgPassword.setError("Password cannot be empty!");

                } else {

                    DBHelper helper = new DBHelper(Login.this);

                    if (helper.checkValidUser(lgUsername.getText().toString(), lgPassword.getText().toString())) {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        CurrentUser.setCurrentUser(lgUsername.getText().toString());
                        startActivity(new Intent(Login.this, Catalog.class));
                        finish();
                    } else
                        Toast.makeText(Login.this, "Invalid Login", Toast.LENGTH_SHORT).show();

                    helper.close();
                }

            }
        });

        lgRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }
}
