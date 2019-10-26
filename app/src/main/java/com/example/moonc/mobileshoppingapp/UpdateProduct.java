package com.example.moonc.mobileshoppingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateProduct extends AppCompatActivity {

    Spinner updPName;
    EditText updPPrice, updPType, updPImage;
    Button updPSubmit;
    HashMap<String, ArrayList<String>> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        updPName = findViewById(R.id.updPName);
        updPPrice = findViewById(R.id.updPPrice);
        updPType = findViewById(R.id.updPType);
        updPImage = findViewById(R.id.updPImage);
        updPSubmit = findViewById(R.id.updPSubmit);

        DBHelper helper = new DBHelper(UpdateProduct.this);
        items = helper.getData();
        ArrayAdapter<String> ad = new ArrayAdapter<>(UpdateProduct.this, R.layout.support_simple_spinner_dropdown_item, items.get("NAME"));
        updPName.setAdapter(ad);
        helper.close();

        updPName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updPPrice.setText(items.get("PRICE").get(i));
                updPType.setText(items.get("TYPE").get(i));
                updPImage.setText(items.get("IMGSRC").get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        updPSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper helper = new DBHelper(UpdateProduct.this);
                helper.updateProduct(updPName.getSelectedItem().toString(),
                        updPPrice.getText().toString(),
                        updPType.getText().toString(),
                        updPImage.getText().toString());
                helper.close();
                finish();
            }
        });
    }
}
