package com.myfit.brownies.myfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FoodDatabase extends SQLiteOpenHelper {

    Calendar calendar;

    // sql.UserDatabase Version
    private static final int DATABASE_VERSION = 2;

    // sql.UserDatabase Name
    private static final String DATABASE_NAME = "FoodManager.db";

    // model.User table name
    private static final String TABLE_FOOD = "food";

    // model.User Table Columns names
    private static final String COLUMN_FOOD_ID = "food_id";
    private static final String COLUMN_ITEM_NAME = "food_item";
    private static final String COLUMN_BRAND_NAME = "food_brand";
    private static final String COLUMN_CALORIES = "food_calories";
    private static final String COLUMN_FATS = "food_fats";
    private static final String COLUMN_PROTEIN = "food_protein";
    private static final String COLUMN_CARBS = "food_carbs";
    private static final String COLUMN_DAY = "food_day";


    // create table sql query
    private String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + " ("
            + COLUMN_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ITEM_NAME + " TEXT,"
            + COLUMN_BRAND_NAME + " TEXT," + COLUMN_CALORIES + " TEXT," + COLUMN_FATS + " TEXT," + COLUMN_PROTEIN + " TEXT," + COLUMN_CARBS + " TEXT," + COLUMN_DAY + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_FOOD;

    /**
     * Constructor
     *
     * @param context
     */
    public FoodDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FOOD_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop model.User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    public int getDay(){

        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * This method is to create food record
     *
     * @param food
     */
    public void addFood(Food food) {

        calendar = Calendar.getInstance();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, food.getIName());
        values.put(COLUMN_BRAND_NAME, food.getBName());
        values.put(COLUMN_CALORIES, food.getCalories());
        values.put(COLUMN_FATS, food.getFats());
        values.put(COLUMN_PROTEIN, food.getProtein());
        values.put(COLUMN_CARBS, food.getCarbs());
        values.put(COLUMN_DAY, Integer.toString(getDay()));

        // Inserting Row
        db.insert(TABLE_FOOD, null, values);
        db.close();
    }

    /**
     * This method is to fetch all foods and return the list of food records based on a day
     *
     * @return list
     */
    /*
    public List <Food> getDayFood(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FOOD + " WHERE " + COLUMN_DAY + " = " + getDay();
        Cursor cursor = db.rawQuery(query, null);

        List<Food> DayFoodList = new ArrayList<Food>();

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();

                food.setIName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
                food.setBName(cursor.getString(cursor.getColumnIndex(COLUMN_BRAND_NAME)));
                food.setCalories(cursor.getString(cursor.getColumnIndex(COLUMN_CALORIES)));
                food.setFats(cursor.getString(cursor.getColumnIndex(COLUMN_FATS)));
                food.setProtein(cursor.getString(cursor.getColumnIndex(COLUMN_PROTEIN)));
                food.setCarbs(cursor.getString(cursor.getColumnIndex(COLUMN_CARBS)));

                // Adding food record to list
                DayFoodList.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return DayFoodList;
    }
    */

    /**
     * This method is to fetch all foods and return the list of food records
     *
     * @return list
     */
    public List<Food> getAllFood(int day) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_FOOD_ID,
                COLUMN_ITEM_NAME,
                COLUMN_BRAND_NAME,
                COLUMN_CALORIES,
                COLUMN_FATS,
                COLUMN_PROTEIN,
                COLUMN_CARBS

        };

        String whereClause = COLUMN_DAY + "=?";

        String[] whereValue = { Integer.toString(day) };

        List<Food> foodList = new ArrayList<Food>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FOOD, //Table to query
                columns,    //columns to return
                whereClause,        //columns for the WHERE clause
                whereValue,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_FOOD_ID)));
                food.setIName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
                food.setBName(cursor.getString(cursor.getColumnIndex(COLUMN_BRAND_NAME)));
                food.setCalories(cursor.getString(cursor.getColumnIndex(COLUMN_CALORIES)));
                food.setFats(cursor.getString(cursor.getColumnIndex(COLUMN_FATS)));
                food.setProtein(cursor.getString(cursor.getColumnIndex(COLUMN_PROTEIN)));
                food.setCarbs(cursor.getString(cursor.getColumnIndex(COLUMN_CARBS)));

                // Adding food record to list
                foodList.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return food list
        return foodList;
    }

    public int checkEmpty(){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FOOD;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    /**
     * This method is to delete food record
     *
     * @param ID
     */
    public void deleteFood(int ID) {
        // delete user record by id

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_FOOD + " WHERE " + COLUMN_FOOD_ID + " = " + ID;
        db.rawQuery(query, null).moveToFirst();

        db.close();
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

}