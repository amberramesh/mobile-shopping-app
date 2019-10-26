package com.example.moonc.mobileshoppingapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyAccount extends AppCompatActivity {

    EditText maCurrentPass, maNewPass, maConfirmPass;
    Button maSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        maCurrentPass = findViewById(R.id.maCurrentPass);
        maNewPass = findViewById(R.id.maNewPass);
        maConfirmPass = findViewById(R.id.maConfirmPass);
        maSubmit = findViewById(R.id.maSubmit);

        maSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!maNewPass.getText().toString().equals(maConfirmPass.getText().toString()))
                    maNewPass.setError("Passwords do not match.");

                if(maNewPass.getText().toString().length() < 6)
                    maNewPass.setError("Minimum 6 characters required.");

                if(maCurrentPass.getText().toString().isEmpty())
                    maCurrentPass.setError("Cannot be blank!");

                if(maNewPass.getText().toString().isEmpty())
                    maNewPass.setError("Cannot be blank!");

                if(maConfirmPass.getText().toString().isEmpty())
                    maConfirmPass.setError("Cannot be blank!");

                if(maNewPass.getError() == null) {
                    DBHelper helper = new DBHelper(MyAccount.this);
                    if(helper.checkPassword(maCurrentPass.getText().toString())){
                        helper.updateUser(CurrentUser.getCurrentUser(), maNewPass.getText().toString());
                        Toast.makeText(MyAccount.this, "Updated Password.", Toast.LENGTH_LONG).show();
                        
                        finish();
                    }
                    helper.close();
                }
            }
        });
    }
}
