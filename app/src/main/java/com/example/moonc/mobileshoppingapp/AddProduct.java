package com.example.moonc.mobileshoppingapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddProduct extends AppCompatActivity {

    EditText addPName, addPPrice, addPImage, addPType;
    Button addPSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addPName = findViewById(R.id.addPName);
        addPPrice = findViewById(R.id.addPPrice);
        addPType = findViewById(R.id.addPType);
        addPImage = findViewById(R.id.addPImage);
        addPSubmit = findViewById(R.id.addPSubmit);

        addPSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper helper = new DBHelper(AddProduct.this);

                helper.insertProduct(addPImage.getText().toString(),
                        addPName.getText().toString(),
                        Integer.parseInt(addPPrice.getText().toString()),
                        addPType.getText().toString());

                helper.close();
                finish();
            }
        });
    }
}
