package com.example.employeeprogram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "thelist.db";
    public static final String TABLE_NAME = "thelist_data";
    public static final String COL1="ID";
    public static final String COL2="NAME";

    public static final String COL3="EMAIL"; //extra
    public static final String COL4="PHONE";
    public static final String COL5="COMPANY";
    public static final String COL6="DATETIME";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("+COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +COL2+ " TEXT ," +COL3+ " TEXT," +COL4+ " TEXT," +COL5+ " TEXT," +COL6+ " TEXT)"; //add other props
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item1,String item2,String item3,String item4,String item5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);

        contentValues.put(COL3, item2); //extra
        contentValues.put(COL4, item3);
        contentValues.put(COL5, item4);
        contentValues.put(COL6, item5);

        long result = db.insert(TABLE_NAME, null, contentValues);
        //db.close(); //Zadvice

        //if data is inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    Cursor getListContents(){
        SQLiteDatabase db = this.getReadableDatabase();
        Character filter = 'R';
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + "WHERE" + COL2 + "LIKE _"+filter,null);
        //data.close(); //Zadvice
        return data;
    }


}
