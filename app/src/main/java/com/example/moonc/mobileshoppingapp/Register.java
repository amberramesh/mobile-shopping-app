package com.example.moonc.mobileshoppingapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText rgUsername, rgPassword, rgConfirmPass;
    Button rgSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rgUsername = findViewById(R.id.rgUsername);
        rgPassword = findViewById(R.id.rgPassword);
        rgConfirmPass = findViewById(R.id.rgConfirmPass);
        rgSubmit = findViewById(R.id.rgSubmit);

        rgUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    DBHelper helper = new DBHelper(Register.this);
                    if(helper.checkUsernameTaken(rgUsername.getText().toString()))
                        rgUsername.setError("Username already taken");
                    helper.close();
                }
            }
        });

        rgSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rgUsername.onEditorAction(EditorInfo.IME_ACTION_DONE);
                rgPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
                rgConfirmPass.onEditorAction(EditorInfo.IME_ACTION_DONE);

                if(!rgPassword.getText().toString().equals(rgConfirmPass.getText().toString()))
                    rgPassword.setError("Passwords do not match!");

                if(rgUsername.getText().toString().length() < 6)
                    rgUsername.setError("Minimum 6 characters needed");

                if(rgPassword.getText().toString().length() < 6)
                    rgPassword.setError("Minimum 6 characters needed");

                if(rgUsername.getError() == null && rgPassword.getError() == null) {

                    DBHelper helper = new DBHelper(Register.this);
                    helper.insertUser(rgUsername.getText().toString(),
                            rgPassword.getText().toString());
                    helper.close();

                    Toast.makeText(Register.this, "Account Created.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
