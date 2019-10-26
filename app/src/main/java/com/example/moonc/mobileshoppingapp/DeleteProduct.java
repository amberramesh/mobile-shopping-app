package com.example.moonc.mobileshoppingapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class DeleteProduct extends AppCompatActivity {

    Spinner delPName;
    Button delPSubmit;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        delPName = findViewById(R.id.delPName);
        delPSubmit = findViewById(R.id.delPSubmit);

        DBHelper helper = new DBHelper(DeleteProduct.this);
        items = helper.getData().get("NAME");

        ArrayAdapter<String> ad = new ArrayAdapter<>(DeleteProduct.this, R.layout.support_simple_spinner_dropdown_item, items);
        delPName.setAdapter(ad);
        helper.close();

        delPSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper helper = new DBHelper(DeleteProduct.this);
                helper.deleteProduct(delPName.getSelectedItem().toString());
                helper.close();
                finish();
            }
        });
    }
}
