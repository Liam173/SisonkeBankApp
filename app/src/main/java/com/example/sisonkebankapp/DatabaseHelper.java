package com.example.sisonkebankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Store all the names of each individual attribute that is found in the database, to avoid naming mistakes
    public static final String users = "users";
    public static final String colid = "id";
    public static final String colfirst = "firstName";
    public static final String collast = "lastName";
    public static final String colemail = "email";
    public static final String colpass = "password";
    public static final String colphone = "phoneNumber";
    public static final String colgender = "gender";
    public static final String colcurrentbalance = "currentBalance";
    public static final String colsavingsbalance = "savingsBalance";

    //Create the database
    public DatabaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create the table within the database
        String createUserStatement = "CREATE TABLE " + users + " (" + colid + " INTEGER PRIMARY KEY AUTOINCREMENT, " + colfirst+ " TEXT, " + collast + " TEXT, " + colemail + " TEXT, " + colpass + " TEXT, " + colphone + " TEXT, " + colgender + " TEXT, " + colcurrentbalance + " INTEGER, " + colsavingsbalance + " INTEGER)";
        db.execSQL(createUserStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the table if it exists, to allow for a new version
        db.execSQL("DROP TABLE IF EXISTS " + users);
        onCreate(db);
    }

    public boolean addUser(UserDetails userDetails){
        //Insert the specified data that is temporarily stored inside the userDetails class
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colfirst, userDetails.getFirstName());
        cv.put(collast, userDetails.getLastName());
        cv.put(colemail, userDetails.getEmail());
        cv.put(colpass, userDetails.getPassword());
        cv.put(colphone, userDetails.getPhoneNumber());
        cv.put(colgender, userDetails.getGender());
        cv.put(colcurrentbalance, userDetails.getCurrentBalance());
        cv.put(colsavingsbalance, userDetails.getSavingsBalance());
        long insert = db.insert(users, null, cv);
        //The if specifies that the data could not be stored inside the database, whereas the else shows that the data was entered successfully
        if(insert == -1){
            return false;
        }else return true;
    }

    //This method makes sure that the entered email matches that of one inside the database, to check if it exists
    public String findEmail(SQLiteDatabase db, String email){
        String queryStatement = "SELECT * FROM " + users + " WHERE " + colemail + " = ?";
        Cursor cursor = db.rawQuery(queryStatement, new String[]{email});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(colemail));
        }else return "";
    }

    //This method is used to find the specified data that is being looked for, the "condition" specifies what data should be returned
    public String getUserDetails(SQLiteDatabase db, String email, String condition){
        String queryStatement = "SELECT * FROM " + users + " WHERE " + colemail + " = ?";
        Cursor cursor = db.rawQuery(queryStatement, new String[]{email});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(condition));
        }else return "";
    }

    //This method updates the current and savings of the specified user on the database
    public void updateBalance(SQLiteDatabase db, int id, int newCurrent, int newSavings){
        String query = "UPDATE "+ users + " SET " + colcurrentbalance + " = " + newCurrent + ", " + colsavingsbalance + " = " + newSavings + " WHERE " + colid + " = " + id;
        db.execSQL(query);
    }
}
