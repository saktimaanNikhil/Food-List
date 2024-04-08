package com.example.foodlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FoodList.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "AllFood";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ID = "id";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_PRICE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void addNote(String name, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PRICE, price);
        long result = db.insert(TABLE_NAME,null,cv);

        if (result ==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAlData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null){
            cursor =  db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateContact(String id, String name, String price) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PRICE, price);

        Log.d("UpdateContact", "ID: " + id + ", Name: " + name + ", Price: " + price);


        long result = database.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
            Log.d("UpdateContact", "Successfully updated row with ID: " + id);
        }
    }

    public void deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_ID + "= ?",new String[]{String.valueOf(id)});
    }


}
