package com.example.moonc.mobileshoppingapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductPage extends AppCompatActivity {

    ImageView ppImg;
    TextView ppType, ppName, ppPrice;
    EditText ppQty;
    Button ppSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ppImg = findViewById(R.id.ppImg);
        ppType = findViewById(R.id.ppType);
        ppName = findViewById(R.id.ppName);
        ppPrice = findViewById(R.id.ppPrice);
        ppQty = findViewById(R.id.ppQty);
        ppSubmit = findViewById(R.id.ppSubmit);

        final Bundle extras = getIntent().getExtras();

        GlideApp.with(ProductPage.this).load(extras.getString("IMGSRC"))
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(ppImg);

        ppType.setText("Browsing "+extras.getString("TYPE"));
        ppName.setText(extras.getString("NAME"));
        ppPrice.setText("Rs. "+extras.getString("PRICE"));

        ppSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ppQty.getText().toString().isEmpty())
                    ppQty.setError("Select a quantity!");
                else {
                    if(Integer.parseInt(ppQty.getText().toString()) > 5)
                        ppQty.setError("Cannot purchase more than 5!");

                    if(Integer.parseInt(ppQty.getText().toString()) < 1)
                        ppQty.setError("Minimum quantity is 1!");
                }

                if(ppQty.getError() == null) {
                    ppQty.onEditorAction(EditorInfo.IME_ACTION_DONE);

                    DBHelper helper = new DBHelper(ProductPage.this);
                    helper.insertCart(CurrentUser.getCurrentUser(),
                            extras.getString("IMGSRC"),
                            extras.getString("NAME"),
                            Integer.parseInt(extras.getString("PRICE")),
                            Integer.parseInt(ppQty.getText().toString()));
                    helper.close();
                    finish();
                    Toast.makeText(ProductPage.this, "Item added to cart", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
