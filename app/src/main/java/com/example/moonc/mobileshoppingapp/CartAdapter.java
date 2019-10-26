package com.example.moonc.mobileshoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    Context cart;
    ArrayList<String> image, name, price, qty;
    LayoutInflater li;

    public CartAdapter(Context con, ArrayList<String> image, ArrayList<String> name,
                          ArrayList<String> price, ArrayList<String> qty) {
        this.cart = con;
        this.image = image;
        this.name = name;
        this.price = price;
        this.qty = qty;
        li = LayoutInflater.from(cart);
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return name;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    ImageView ctmImage;
    TextView ctmName, ctmPrice, ctmQty;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View cartItem = li.inflate(R.layout.cart_item, null);
        ctmImage = cartItem.findViewById(R.id.ctmImage);
        ctmName = cartItem.findViewById(R.id.ctmName);
        ctmPrice = cartItem.findViewById(R.id.ctmPrice);
        ctmQty = cartItem.findViewById(R.id.ctmQty);

        GlideApp.with(cart).load(image.get(i))
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(ctmImage);
        ctmName.setText(name.get(i).toString());
        ctmPrice.setText("Rs. "+price.get(i).toString());
        ctmQty.setText("Qty: "+qty.get(i).toString());

        return cartItem;

    }
}
