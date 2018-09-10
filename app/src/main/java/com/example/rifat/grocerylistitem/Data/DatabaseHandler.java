package com.example.rifat.grocerylistitem.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rifat.grocerylistitem.Model.Grocery;
import com.example.rifat.grocerylistitem.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rifat on 9/1/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;
    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION_NUMBER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.KEY_GROCERY_ITEM + " TEXT,"
                + Constants.KEY_QUANTITY_NUMBER + " TEXT,"
                + Constants.KEY_DATE_NAME + " LONG);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +Constants.TABLE_NAME);
        onCreate(db);
    }

    /*
        CRUD Operation: Create,Read,Update,Delete method.
    */

    // Add grocery
    public void addGrocery(Grocery grocery){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
        contentValues.put(Constants.KEY_QUANTITY_NUMBER,grocery.getQuantity());
        contentValues.put(Constants.KEY_DATE_NAME,java.lang.System.currentTimeMillis());

        sqLiteDatabase.insert(Constants.TABLE_NAME,null,contentValues);
    }
    // Get grocery
    public Grocery getGrocery(int id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        Cursor cursor=sqLiteDatabase.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_GROCERY_ITEM,Constants.KEY_QUANTITY_NUMBER
        ,Constants.KEY_DATE_NAME},Constants.KEY_ID+"=?",new String[]{String.valueOf(id)},
                null,null,null,null);

        if (cursor!=null){
            cursor.moveToNext();
        }

        Grocery grocery=new Grocery();
        grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
        grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QUANTITY_NUMBER)));

        //convert timestamp to something readable
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                .getTime());

        grocery.setDateItemadded(formatedDate);

        return grocery;
    }

    // Get all Grocery
    public List<Grocery> getAllGrocery() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<Grocery> groceryList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(Constants.TABLE_NAME, new String[]{
                        Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_QUANTITY_NUMBER,
                        Constants.KEY_DATE_NAME}, null, null, null, null,
                Constants.KEY_DATE_NAME + "DESC");

        if (cursor.moveToFirst()) {

            do {
                Grocery grocery=new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QUANTITY_NUMBER)));

                //convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                        .getTime());

                grocery.setDateItemadded(formatedDate);

                //Add to the grocery List
                groceryList.add(grocery);
            } while (cursor.moveToNext());


        }
        return groceryList;
    }

    //Update Grocery
    public int updateGrocery(Grocery grocery){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constants.KEY_QUANTITY_NUMBER, grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());//get system time


        //update row
        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?", new String[] { String.valueOf(grocery.getId())} );

    }

    //Delete Grocery
    public void deleteGrocery(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?",
                new String[] {String.valueOf(id)});

        db.close();
    }
    // get Count
    public int getGroceriesCount(){
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
