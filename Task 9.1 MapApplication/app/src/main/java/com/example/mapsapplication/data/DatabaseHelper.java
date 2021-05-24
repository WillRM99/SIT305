package com.example.mapsapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mapsapplication.model.Restaurant;
import com.example.mapsapplication.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_NOTE_TABLE = "CREATE TABLE " + Util.TABLE_NAME +
                "(" +
                Util.RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Util.RESTAURANT_NAME + " TEXT" + "," +
                Util.RESTAURANT_LAT + " TEXT" + "," +
                Util.RESTAURANT_LONG + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);

    }

    public long insetRestaurant(Restaurant restaurant)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Util.RESTAURANT_NAME, restaurant.getRestaurantName());
        contentValues.put(Util.RESTAURANT_LAT, restaurant.getRestaurantLat());
        contentValues.put(Util.RESTAURANT_LONG, restaurant.getRestaurantLong());

        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();

        return newRowId;
    }

/*    public boolean fetchRestaurant(String restaurantName, String contents)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.NOTE_ID}, Util.NOTE_CONTENT + "=?", new String[] {contents}, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();

        if(numberOfRows > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }*/

    public List<Restaurant> fetchAllRestaurants()
    {
        List<Restaurant> restaurantArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantID(cursor.getInt(0));
                restaurant.setRestaurantName(cursor.getString(1));
                restaurant.setRestaurantLat(cursor.getString(2));
                restaurant.setRestaurantLong(cursor.getString(3));

                restaurantArrayList.add(restaurant);

            } while (cursor.moveToNext());

        }

        return restaurantArrayList;
    }

//    public int saveNote(Note note)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(Util.NOTE_CONTENT, note.getNoteContents());
//
//        return db.update(Util.TABLE_NAME, contentValues, Util.NOTE_ID + "=?", new String[]{String.valueOf(note.getNoteID())});
//
//
//    }
//
//    public int deleteNote(Note note)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        return db.delete(Util.TABLE_NAME, Util.NOTE_ID + "=?", new String[]{String.valueOf(note.getNoteID())});
//    }


}
