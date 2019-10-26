package com.example.moonc.mobileshoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class Catalog extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener {

    Button catMenu;
    ListView catCatalog;
    ArrayList<String> imgsrc, name, price, type;
    DBHelper helper;
    private static final String TAG = Catalog.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        catMenu = findViewById(R.id.catMenu);
        catCatalog = findViewById(R.id.catCatalog);
        helper = new DBHelper(Catalog.this);

        imgsrc = helper.getData().get("IMGSRC");
        name = helper.getData().get("NAME");
        price = helper.getData().get("PRICE");
        type = helper.getData().get("TYPE");

        CatalogAdapter ca = new CatalogAdapter(Catalog.this, imgsrc, name, price, type);
        catCatalog.setAdapter(ca);

        helper.close();

        catCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Catalog.this, ProductPage.class);
                intent.putExtra("IMGSRC", imgsrc.get(i));
                intent.putExtra("NAME", name.get(i));
                intent.putExtra("PRICE", price.get(i));
                intent.putExtra("TYPE", type.get(i));
                startActivity(intent);
            }
        });

        catMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(Catalog.this, view);
                popup.setOnMenuItemClickListener(Catalog.this);
                popup.inflate(R.menu.catalog_menu);
                popup.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuMyAccount: startActivity(new Intent(Catalog.this, MyAccount.class));
                return true;
            case R.id.menuViewCart: startActivity(new Intent(Catalog.this, Cart.class));
                return true;
            case R.id.menuLogout: CurrentUser.setCurrentUser(null);
                startActivity(new Intent(Catalog.this, Login.class));
                return true;
        }
        return false;
    }
}
