package com.example.bakalabazar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bakalabazar.Models.BakalaModel;

public class DatabaseHalper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "cart.dp";
    public static int DATABASE_VERSION = 1;

    public static String TABLE_NAME = "carttable";
    public static String KEY_ID = "id";
    public static String NAME = "name";
    public static String PRICE = "price";
    public static String PHOTO = "photo";

    public DatabaseHalper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME +"( " +
                KEY_ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT , " +
                PRICE + " TEXT , " +
                PHOTO + " TEXT ) ";
                db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+ TABLE_NAME);
        onCreate(db);
    }

    public void insert(BakalaModel bakalaModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID , bakalaModel.getId());
        contentValues.put(NAME , bakalaModel.getName());
        contentValues.put(PRICE , bakalaModel.getPrice());
        contentValues.put(PHOTO , bakalaModel.getPhoto());
        db.insert(TABLE_NAME,null,contentValues);
    }


    public boolean serachID(String id){

        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM " + TABLE_NAME + "WHERE" + KEY_ID + "  =" + id;
        Cursor cursor = db.rawQuery(q,null);
                if(cursor != null && cursor.getCount() >0)
                    return true;

        return false;
    }


    public Cursor getData(){
        SQLiteDatabase db = getWritableDatabase();
       Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return cursor;

    }

    public void deleteItem(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+" = ?",new String[]{id});
    }

    public void deletecart(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
    }


}
