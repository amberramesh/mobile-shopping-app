package com.example.moonc.mobileshoppingapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class DeleteUser extends AppCompatActivity {

    Spinner delUName;
    Button delUSubmit;
    ArrayList<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        delUName = findViewById(R.id.delUName);
        delUSubmit =findViewById(R.id.delUSubmit);

        DBHelper helper = new DBHelper(DeleteUser.this);
        users = helper.getUsers();

        ArrayAdapter<String> ad = new ArrayAdapter<>(DeleteUser.this, R.layout.support_simple_spinner_dropdown_item, users);
        delUName.setAdapter(ad);

        helper.close();

        delUSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper helper = new DBHelper(DeleteUser.this);
                helper.deleteUser(delUName.getSelectedItem().toString());
                helper.close();
                finish();
            }
        });
    }
}
