package com.example.moonc.mobileshoppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    private Context con;
    public DBHelper(Context context) {
        super(context, "MobileShoppingDB", null, 1);
        con = context;
    }

    public boolean checkValidUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query("USER", new String[] {"USERNAME", "PASSWORD"},
                "USERNAME = ? AND PASSWORD = ?", new String[] {username, password},
                null, null, null);

        boolean check = cur.moveToFirst();
        cur.close();
        db.close();
        return check;
    }

    public boolean checkValidAdmin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query("ADMIN", new String[] {"USERNAME", "PASSWORD"},
                "USERNAME = ? AND PASSWORD = ?", new String[] {username, password},
                null, null, null);

        boolean check = cur.moveToFirst();
        cur.close();
        db.close();
        return check;
    }

    public boolean checkUsernameTaken(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query("USER", new String[] {"USERNAME"},
                "USERNAME = ?", new String[] {username} ,
                null, null, null);

        boolean check = cur.moveToFirst();
        cur.close();
        db.close();
        return check;
    }

    public boolean checkPassword(String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur =db.query("USER", new String[] {"USERNAME","PASSWORD"},
                "USERNAME = ? AND PASSWORD = ?", new String[] {CurrentUser.getCurrentUser(), password},
                null, null, null);

        boolean check = cur.moveToFirst();
        cur.close();
        db.close();
        return check;
    }

    public HashMap<String, ArrayList<String>> getData() {

        HashMap<String, ArrayList<String>> hm = new HashMap<>();
        ArrayList<String> imgsrc = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> price = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {"IMGSRC","NAME","PRICE","TYPE"};
        Cursor cur = db.query("PRODUCT", cols,
                null, null, null,null, "TYPE");

        if (cur.getCount() != 0) {
            if(cur.moveToFirst())
                do {
                    imgsrc.add(cur.getString(0));
                    name.add(cur.getString(1));
                    price.add(String.valueOf(cur.getInt(2)));
                    type.add(cur.getString(3));
                } while(cur.moveToNext());
            cur.close();
        }
        else
            Toast.makeText(con, "No Products Available!", Toast.LENGTH_SHORT).show();

        hm.put("IMGSRC", imgsrc);
        hm.put("NAME", name);
        hm.put("PRICE", price);
        hm.put("TYPE", type);
        db.close();

        return hm;
    }

    public HashMap<String, ArrayList<String>> getCart() {

        HashMap<String, ArrayList<String>> hm = new HashMap<>();
        ArrayList<String> imgsrc = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> price = new ArrayList<>();
        ArrayList<String> qty = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {"UNAME","IMGSRC","NAME","PRICE","QTY"};
        Cursor cur = db.query("CART", cols,
                "UNAME = ?", new String[]{CurrentUser.getCurrentUser()}, null,null, null);

        if (cur.getCount() != 0) {
            if(cur.moveToFirst())
                do {
                    imgsrc.add(cur.getString(1));
                    name.add(cur.getString(2));
                    price.add(String.valueOf(cur.getInt(3) * cur.getInt(4)));
                    qty.add(String.valueOf(cur.getInt(4)));
                } while(cur.moveToNext());
            cur.close();
        }

        hm.put("IMGSRC", imgsrc);
        hm.put("NAME", name);
        hm.put("PRICE", price);
        hm.put("QTY", qty);
        db.close();

        return hm;
    }

    public ArrayList<String> getUsers() {
        ArrayList<String> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query("USER", new String[] {"USERNAME"}, null, null,
                null, null, null);
        if(cur.getCount() != 0) {
            if(cur.moveToFirst()) {
                do {
                    list.add(cur.getString(0));
                } while (cur.moveToNext());
            }
        }
        cur.close();
        db.close();

        return list;
    }

    public void updateUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("PASSWORD", password);
        db.update("USER", cv, "USERNAME = ?", new String[]{username});
        db.close();
    }

    public void updateProduct(String name, String price, String type, String imgsrc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("PRICE", price);
        cv.put("TYPE", type);
        cv.put("IMGSRC", imgsrc);
        db.update("PRODUCT", cv, "NAME = ?", new String[]{name});
        db.close();
        Toast.makeText(con, "Updated details for "+name+".", Toast.LENGTH_SHORT).show();
    }

    public void deleteCart(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("CART", "UNAME = ? AND NAME = ?", new String[]{CurrentUser.getCurrentUser(), name});
        db.close();
        Toast.makeText(con, "Removed "+name+" from cart.", Toast.LENGTH_SHORT).show();
    }

    public void deleteProduct(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("PRODUCT", "NAME = ?", new String[]{name});
        db.delete("CART", "NAME = ?", new String[]{name});
        db.close();
        Toast.makeText(con, "Deleted "+name+" from database.", Toast.LENGTH_SHORT).show();
    }

    public void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("USER", "USERNAME = ?", new String[]{username});
        db.delete("CART", "UNAME = ?", new String[]{username});
        db.close();
        Toast.makeText(con, "Deleted "+username+" from database.", Toast.LENGTH_SHORT).show();
    }

    public void insertAdmin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("USERNAME", username);
        cv.put("PASSWORD", password);
        db.insert("ADMIN", null, cv);
        db.close();
    }

    public void insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("USERNAME", username);
        cv.put("PASSWORD", password);
        db.insert("USER", null, cv);
        db.close();
    }

    public void insertProduct(String imgsrc, String name, int price, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("IMGSRC", imgsrc);
        cv.put("NAME", name);
        cv.put("PRICE", price);
        cv.put("TYPE", type);
        db.insert("PRODUCT", null, cv);
        db.close();
        Toast.makeText(con, "Added "+name+" to database.", Toast.LENGTH_SHORT).show();
    }

    public void insertCart(String uname, String imgsrc, String name, int price, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("UNAME", uname);
        cv.put("IMGSRC", imgsrc);
        cv.put("NAME", name);
        cv.put("PRICE", price);
        cv.put("QTY", qty);
        db.replace("CART", null, cv);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE ADMIN (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT UNIQUE, PASSWORD TEXT);";
        String table2 = "CREATE TABLE USER (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT UNIQUE, PASSWORD TEXT);";
        String table3 = "CREATE TABLE PRODUCT (ID INTEGER PRIMARY KEY AUTOINCREMENT, IMGSRC TEXT, NAME TEXT UNIQUE, PRICE INTEGER, TYPE TEXT);";
        String table4 = "CREATE TABLE CART (UNAME TEXT, IMGSRC, NAME UNIQUE, PRICE, QTY INTEGER, FOREIGN KEY(IMGSRC) REFERENCES PRODUCT(IMGSRC), FOREIGN KEY(NAME) REFERENCES PRODUCT(NAME), FOREIGN KEY(PRICE) REFERENCES PRODUCT(PRICE));";

        // Initial users
        String admin1 = "INSERT INTO ADMIN(USERNAME, PASSWORD) VALUES('admin','admin');";
        String user1 = "INSERT INTO USER(USERNAME, PASSWORD) VALUES('amberramesh','helloworld');";

        // Demo products added initially
        String product1 = "INSERT INTO PRODUCT(IMGSRC, NAME, PRICE, TYPE) VALUES('https://images-na.ssl-images-amazon.com/images/I/91EEDbs1nLL._SX385_.jpg','Star Wars: The Complete Saga', 3200, 'Movies');";
        String product2 = "INSERT INTO PRODUCT(IMGSRC, NAME, PRICE, TYPE) VALUES('https://www.zoom.co.uk/assets/images/site/product/buh0287401_1.jpg','MCU Phase One (Blu-ray Box Set)', 3500, 'Movies');";
        String product3 = "INSERT INTO PRODUCT(IMGSRC, NAME, PRICE, TYPE) VALUES('https://images-na.ssl-images-amazon.com/images/I/51-vlplSVCL._SY445_.jpg','The Lord of the Rings Collection', 1200, 'Movies');";

        String product4 = "INSERT INTO PRODUCT(IMGSRC, NAME, PRICE, TYPE) VALUES('https://images-na.ssl-images-amazon.com/images/I/71kimpmAaSL._SY606_.jpg', 'Mattel UNO Card Game', 300, 'Games');";
        String product5 = "INSERT INTO PRODUCT(IMGSRC, NAME, PRICE, TYPE) VALUES('https://images-na.ssl-images-amazon.com/images/I/818FNW15GxL._SL1500_.jpg', 'Jenga Classic Game', 500, 'Games');";

        String product6 = "INSERT INTO PRODUCT(IMGSRC, NAME, PRICE, TYPE) VALUES('https://images-na.ssl-images-amazon.com/images/I/81s7B+Als-L._AC_UX500_SY400_.jpg', 'NES Classic Edition', 6000, 'Electronics');";
        String product7 = "INSERT INTO PRODUCT(IMGSRC, NAME, PRICE, TYPE) VALUES('https://images-na.ssl-images-amazon.com/images/I/71PGvPXpk5L._AC_UX500_SY400_.jpg', 'PlayStation 4 Slim 1 TB', 18000, 'Electronics');";

        String product8 = "INSERT INTO PRODUCT(IMGSRC, NAME, PRICE, TYPE) VALUES('https://images-na.ssl-images-amazon.com/images/I/71qI3Yg5E1L._AC_UX500_SY400_.jpg', 'West Loop Stainless Steel Travel Mug', 600, 'Kitchenware');";
        String product9 = "INSERT INTO PRODUCT(IMGSRC, NAME, PRICE, TYPE) VALUES('https://images-na.ssl-images-amazon.com/images/I/71OYPL+UynL._AC_UX500_SY400_.jpg', 'Instant Pot Programmable Pressure Cooker', 5000, 'Kitchenware');";
        String product10 = "INSERT INTO PRODUCT(IMGSRC, NAME, PRICE, TYPE) VALUES('https://images-na.ssl-images-amazon.com/images/I/81EuYeSMMaL._SY445_.jpg', 'The Witcher 3: Wild Hunt', 1000, 'Video Games');";

        db.execSQL(table1);
        db.execSQL(table2);
        db.execSQL(table3);
        db.execSQL(table4);

        db.execSQL(admin1);
        db.execSQL(user1);

        db.execSQL(product1);
        db.execSQL(product2);
        db.execSQL(product3);
        db.execSQL(product4);
        db.execSQL(product5);
        db.execSQL(product6);
        db.execSQL(product7);
        db.execSQL(product8);
        db.execSQL(product9);
        db.execSQL(product10);

    }
}
