package com.example.moonc.mobileshoppingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ListView ctCart;
    LinearLayout cartTotal;
    TextView ctTotal;
    DBHelper helper;
    ArrayList<String> imgsrc, ct_name, price, qty;
    CartAdapter cta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ctCart = findViewById(R.id.ctCart);
        cartTotal = findViewById(R.id.cartTotal);
        helper = new DBHelper(Cart.this);

        imgsrc = helper.getCart().get("IMGSRC");
        ct_name = helper.getCart().get("NAME");
        price = helper.getCart().get("PRICE");
        qty = helper.getCart().get("QTY");

        cta = new CartAdapter(Cart.this, imgsrc, ct_name, price, qty);
        ctCart.setAdapter(cta);
        helper.close();

        View footer = getLayoutInflater().inflate(R.layout.cart_total, null);
        ctCart.addFooterView(footer);

        ctTotal = footer.findViewById(R.id.ctTotal);

        int total = 0;
        for(String itemPrice : price)
            total += Integer.parseInt(itemPrice);

        ctTotal.setText("Rs. "+total);

        ctCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                DBHelper helper = new DBHelper(Cart.this);
                helper.deleteCart(ct_name.get(i));
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                return false;
            }
        });
    }
}
