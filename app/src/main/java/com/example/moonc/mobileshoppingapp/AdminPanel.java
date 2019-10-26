package com.example.moonc.mobileshoppingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPanel extends AppCompatActivity {

    Button apAddProduct, apUpdProduct, apDelProduct, apAddAdmin, apDelUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        apAddProduct = findViewById(R.id.apAddProduct);
        apUpdProduct = findViewById(R.id.apUpdProduct);
        apDelProduct = findViewById(R.id.apDelProduct);
        apAddAdmin = findViewById(R.id.apAddAdmin);
        apDelUser = findViewById(R.id.apDelUser);

        apAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, AddProduct.class));
            }
        });

        apUpdProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, UpdateProduct.class));
            }
        });

        apDelProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, DeleteProduct.class));
            }
        });

        apAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, AddAdmin.class));
            }
        });

        apDelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, DeleteUser.class));
            }
        });
    }
}
