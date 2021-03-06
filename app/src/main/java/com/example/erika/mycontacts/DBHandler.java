package com.example.erika.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 11;
    private static final String DATABASE_NAME = "contacts.db";

    private static final String TABLE_CONTACT_LIST = "contactlist";
    private static final String COLUMN_LIST_ID = "_id";
    private static final String COLUMN_LIST_NAME = "name";
    private static final String COLUMN_LIST_ADDRESS = "address";
    private static final String COLUMN_LIST_PHONE = "phone";
    private static final String COLUMN_LIST_EMAIL = "email";

    private static final String TABLE_GROUP_LIST = "grouplist";
    private static final String COLUMN_LIST_GROUP_ID = "_id";
    private static final String COLUMN_LIST_GROUP_NAME = "groupName";

    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //creates a table for contacts and their information
        String query1 = "CREATE TABLE " + TABLE_CONTACT_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_ADDRESS + " TEXT, " +
                COLUMN_LIST_PHONE + " TEXT, " +
                COLUMN_LIST_EMAIL + " TEXT, " +
                COLUMN_LIST_GROUP_NAME + " TEXT " +
                "); ";

        sqLiteDatabase.execSQL(query1);

        //creates a table for groups that holds the different contacts
        String query2 = "CREATE TABLE " + TABLE_GROUP_LIST + "(" +
                COLUMN_LIST_GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_GROUP_NAME + " TEXT " +
                ");";

        sqLiteDatabase.execSQL(query2);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT_LIST);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_LIST);

        onCreate(sqLiteDatabase);
    }

    public void addContactList(String name, String address, String phone, String email, String group) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        //edits the appropriate value with the information input by the user
        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_ADDRESS, address);
        values.put(COLUMN_LIST_PHONE, phone);
        values.put(COLUMN_LIST_EMAIL, email);
        values.put(COLUMN_LIST_GROUP_NAME, group);

        //insert values into the contact list table
        db.insert(TABLE_CONTACT_LIST, null, values);

        //close reference to contact database
        db.close();
    }

    public Cursor getContactLists() {
        SQLiteDatabase db = getWritableDatabase();

        //execute select statement that selects all rows from the contact list table and returns them as a cursor
        return db.rawQuery("SELECT * FROM " + TABLE_CONTACT_LIST, null);
    }


    public String getContactListName(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        //moves the cursor to the first position
        cursor.moveToFirst();

        //cycles through the column and sets indexes in the string equal to the corresponding space in the database
        if (cursor.getString(cursor.getColumnIndex("name")) != null) {
            dbString = cursor.getString(cursor.getColumnIndex("name"));
        }

        //close reference to contact database
        db.close();

        //return the name of the given id
        return dbString;
    }

    public String getContactListAddress(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("address")) != null) {
            dbString = cursor.getString(cursor.getColumnIndex("address"));
        }

        db.close();

        //return the address of the given id
        return dbString;
    }

    public String getContactListNumber(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("phone")) != null) {
            dbString = cursor.getString(cursor.getColumnIndex("phone"));
        }

        db.close();
        //return the phone number of the given id
        return dbString;
    }

    public String getContactListEmail(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        //selects all information from the Contact List table row that matches the id sent
        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("email")) != null) {
            dbString = cursor.getString(cursor.getColumnIndex("email"));
        }

        //close reference to contact database
        db.close();

        //return the email address of the given id
        return dbString;
    }

    public String getContactListGroup(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        //selects all information from the Contact List table row that matches the id sent
        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

            dbString = cursor.getString(cursor.getColumnIndex("groupName"));
        if (cursor.getString(cursor.getColumnIndex("groupName")) == null){
            dbString = "";
        }


        //close reference to contact database
        db.close();

        //return the email address of the given id
        return dbString;
    }

    public void updateContactList(int id, String name, String address, String phone, String email, String group) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        //set values equal to the column names
        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_ADDRESS, address);
        values.put(COLUMN_LIST_PHONE, phone);
        values.put(COLUMN_LIST_EMAIL, email);
        values.put(COLUMN_LIST_GROUP_NAME, group);

        //update values in the contact list table based on the id sent
        db.update(TABLE_CONTACT_LIST, values, COLUMN_LIST_ID + " = " + id, null);

        //close reference to contact database
        db.close();
    }

    public void deleteContactList(int id) {
        SQLiteDatabase db = getWritableDatabase();

        //delete all values from the contact list table based on the id sent
        db.delete(TABLE_CONTACT_LIST, COLUMN_LIST_ID + " = " + id, null);

        //close reference to contact database
        db.close();
    }
/*works but wrong
    public void setGroupNames(String[] list, int groupCount){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        int counter = 0;

        //as long as the counter is less than the length of the String[] of groupnames, this will load the values into the database
        while (counter < groupCount) {
      //      db.rawQuery("SELECT COUNT(" + COLUMN_LIST_GROUP_NAME + ") INTO @C FROM " +
        //            TABLE_GROUP_LIST +
          //          " WHERE " + COLUMN_LIST_GROUP_NAME + " = " list[counter], null);
            if(COLUMN_LIST_GROUP_NAME != list[counter]){
                values.put(COLUMN_LIST_GROUP_NAME, list[counter]);
                db.insert(TABLE_GROUP_LIST, null, values);
                counter++;
            }
        }
    }*/

    public void setGroupNames(String[] list, int groupCount){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "SELECT " + COLUMN_LIST_GROUP_NAME + " FROM " + TABLE_GROUP_LIST;
        Cursor cursor = db.rawQuery(query, null);
        int counter = 0;

        while(counter < cursor.getCount()){
            if (cursor.getString(cursor.getColumnIndex("groupName")) != list[counter]) {
                cursor.moveToNext();
            }
        }


        //as long as the counter is less than the length of the String[] of groupnames, this will load the values into the database
        while (counter < groupCount) {
            //      db.rawQuery("SELECT COUNT(" + COLUMN_LIST_GROUP_NAME + ") INTO @C FROM " +
            //            TABLE_GROUP_LIST +
            //          " WHERE " + COLUMN_LIST_GROUP_NAME + " = " list[counter], null);

            if(cursor.getString(cursor.getColumnIndex("groupName")) != list[counter]){
                values.put(COLUMN_LIST_GROUP_NAME, list[counter]);
                db.insert(TABLE_GROUP_LIST, null, values);
                counter++;
            }
        }
    }
/* Works but must have an already established database
    public String[] getGroupNames(){
        SQLiteDatabase db = getWritableDatabase();

        String dbString[];
        int counter = 0;

        String query = "SELECT " + COLUMN_LIST_GROUP_NAME + " FROM " + TABLE_GROUP_LIST;
        dbString = new String[COLUMN_LIST_GROUP_NAME.length()];

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        //cycles through the column and sets indexes in the string equal to the corresponding space in the database
        while(counter < COLUMN_LIST_GROUP_NAME.length()) {
            dbString[counter] = cursor.getString(cursor.getColumnIndex("groupName"));
            cursor.moveToNext();
            counter++;
        }

        db.close();

        //return the address of the given id
        return dbString;
    }*/

    public Cursor getGroupLists() {
        SQLiteDatabase db = getWritableDatabase();

        //execute select statement that selects all rows from the group list table and returns them as a cursor
        return db.rawQuery("SELECT " + COLUMN_LIST_GROUP_NAME +  " FROM " + TABLE_GROUP_LIST, null);
    }

    public void addContactToGroup(){

    }
}

/*
public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "contacts.db";

    private static final String TABLE_CONTACT_LIST = "contactlist";
    private static final String COLUMN_LIST_ID = "_id";
    private static final String COLUMN_LIST_NAME = "name";
    private static final String COLUMN_LIST_ADDRESS = "address";
    private static final String COLUMN_LIST_PHONE = "phone";
    private static final String COLUMN_LIST_EMAIL = "email";

    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_CONTACT_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_ADDRESS + " TEXT, " +
                COLUMN_LIST_PHONE + " TEXT, " +
                COLUMN_LIST_EMAIL + " TEXT " +
                ");";

        sqLiteDatabase.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT_LIST);

        onCreate(sqLiteDatabase);
    }

    public void addContactList(String name, String address, String phone, String email) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_ADDRESS, address);
        values.put(COLUMN_LIST_PHONE, phone);
        values.put(COLUMN_LIST_EMAIL, email);

        //insert values into the contact list table
        db.insert(TABLE_CONTACT_LIST, null, values);

        //close reference to contact database
        db.close();
    }

    public Cursor getContactLists() {
        SQLiteDatabase db = getWritableDatabase();

        //execute select statement that selects all rows from the contact list table and returns them as a cursor
        return db.rawQuery("SELECT * FROM " + TABLE_CONTACT_LIST, null);
    }


    public String getContactListName(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("name")) != null) {
            dbString = cursor.getString(cursor.getColumnIndex("name"));
        }

        //close reference to contact database
        db.close();

        //return the name of the given id
        return dbString;
    }

    public String getContactListAddress(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("address")) != null) {
            dbString = cursor.getString(cursor.getColumnIndex("address"));
        }

        db.close();

        //return the address of the given id
        return dbString;
    }

    public String getContactListNumber(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("phone")) != null) {
            dbString = cursor.getString(cursor.getColumnIndex("phone"));
        }

        db.close();
        //return the phone number of the given id
        return dbString;
    }

    public String getContactListEmail(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        //selects all information from the Contact List table row that matches the id sent
        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("email")) != null) {
            dbString = cursor.getString(cursor.getColumnIndex("email"));
        }

        //close reference to contact database
        db.close();

        //return the email address of the given id
        return dbString;
    }

    public void updateContactList(int id, String name, String address, String phone, String email) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        //set values equal to the column names
        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_ADDRESS, address);
        values.put(COLUMN_LIST_PHONE, phone);
        values.put(COLUMN_LIST_EMAIL, email);

        //update values in the contact list table based on the id sent
        db.update(TABLE_CONTACT_LIST, values, COLUMN_LIST_ID + " = " + id, null);

        //close reference to contact database
        db.close();
    }

    public void deleteContactList(int id) {
        SQLiteDatabase db = getWritableDatabase();

        //delete all values from the contact list table based on the id sent
        db.delete(TABLE_CONTACT_LIST, COLUMN_LIST_ID + " = " + id, null);

        //close reference to contact database
        db.close();
    }
}
*/