package com.example.erika.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHandler extends SQLiteOpenHelper {
  private static final int DATABASE_VERSION = 1;
private static final String DATABASE_NAME = "contacts.db";

private static final String TABLE_CONTACT_LIST = "contactlist";
private static final String COLUMN_LIST_ID = "_id";
private static final String COLUMN_LIST_NAME = "name";
private static final String COLUMN_LIST_ADDRESS = "address";
private static final String COLUMN_LIST_PHONE = "phone";
private static final String COLUMN_LIST_EMAIL = "email";

public DBHandler(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
}

public void onCreate(SQLiteDatabase sqLiteDatabase){
        String query = "CREATE TABLE " + TABLE_CONTACT_LIST + "(" +
        COLUMN_LIST_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_LIST_NAME + " TEXT, " +
        COLUMN_LIST_ADDRESS + " TEXT, " +
        COLUMN_LIST_PHONE + " TEXT, " +
        COLUMN_LIST_EMAIL + " TEXT" +
        ");";

        sqLiteDatabase.execSQL(query);
        }

public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT_LIST);

        onCreate(sqLiteDatabase);
        }

public void addContactList(String name, String address, String phone, String email){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_ADDRESS, address);
        values.put(COLUMN_LIST_PHONE, phone);

        //insert values into the shopping list table
        db.insert(TABLE_CONTACT_LIST, null, values);

        //close reference to shopper database
        db.close();
        }

public Cursor getContactLists(){
        SQLiteDatabase db = getWritableDatabase();

        //execute select statement that selects all rows from the shopping list table and returns them as a cursor
        return db.rawQuery("SELECT * FROM " + TABLE_CONTACT_LIST, null);
        }


public String getContactListName (int id){
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
        " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if(cursor.getString(cursor.getColumnIndex("name")) != null){
        dbString = cursor.getString(cursor.getColumnIndex("name"));
        }

        db.close();

        return dbString;
        }


        }