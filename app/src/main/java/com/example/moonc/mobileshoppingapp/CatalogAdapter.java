package com.example.moonc.mobileshoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CatalogAdapter extends BaseAdapter {

    Context catalog;
    ArrayList<String> image, name, price, type;
    LayoutInflater li;

    public CatalogAdapter(Context con, ArrayList<String> image, ArrayList<String> name,
                          ArrayList<String> price, ArrayList<String> type) {
        this.catalog = con;
        this.image = image;
        this.name = name;
        this.price = price;
        this.type = type;
        li = LayoutInflater.from(catalog);
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

    ImageView itemImage;
    TextView itemName, itemPrice, itemType;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View catalogItem = li.inflate(R.layout.catalog_item, null);
        itemImage = catalogItem.findViewById(R.id.itemImage);
        itemName = catalogItem.findViewById(R.id.itemName);
        itemPrice = catalogItem.findViewById(R.id.itemPrice);
        itemType = catalogItem.findViewById(R.id.itemType);

        GlideApp.with(catalog).load(image.get(i))
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(itemImage);
        itemName.setText(name.get(i).toString());
        itemPrice.setText("Rs. "+price.get(i).toString());
        itemType.setText(type.get(i).toString());

        return catalogItem;

    }
}
