package com.example.moonc.mobileshoppingapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAdmin extends AppCompatActivity {

    EditText addAName, addAPass, addAConfirmPass;
    Button addASubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addAName = findViewById(R.id.addAName);
        addAPass = findViewById(R.id.addAPass);
        addAConfirmPass = findViewById(R.id.addAConfirmPass);
        addASubmit = findViewById(R.id.addASubmit);

        addASubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                addAPass.onEditorAction(EditorInfo.IME_ACTION_DONE);
                addAConfirmPass.onEditorAction(EditorInfo.IME_ACTION_DONE);

                if(!addAPass.getText().toString().equals(addAConfirmPass.getText().toString()))
                    addAPass.setError("Passwords do not match!");

                if(addAName.getText().toString().length() < 6)
                    addAName.setError("Minimum 6 characters needed");

                if(addAPass.getText().toString().length() < 6)
                    addAPass.setError("Minimum 6 characters needed");

                if(addAName.getError() == null && addAPass.getError() == null) {

                    DBHelper helper = new DBHelper(AddAdmin.this);
                    helper.insertAdmin(addAName.getText().toString(),
                            addAPass.getText().toString());
                    helper.close();

                    Toast.makeText(AddAdmin.this, "Account Created.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
