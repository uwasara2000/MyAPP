package com.s23010541.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log; // Added for logging

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper"; // Tag for Logcat
    private static final String DATABASE_NAME = "UserAccounts.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";

    // SQL statement to create the users table
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_USERNAME + " TEXT," +
                    COL_EMAIL + " TEXT UNIQUE," + // Email should be unique for user accounts
                    COL_PASSWORD + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating table: " + SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ". Dropping old table.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    /**
     * Adds a new user to the database.
     * @param username The username of the user.
     * @param email The email of the user (must be unique).
     * @param password The password of the user.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     */
    public long addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password); // WARNING: Storing plain text password! Hash passwords for security.

        Log.d(TAG, "Attempting to add user: " + email);
        long result = db.insert(TABLE_USERS, null, values);
        if (result == -1) {
            Log.e(TAG, "Failed to add user: " + email);
        } else {
            Log.d(TAG, "User added successfully with ID: " + result);
        }
        db.close();
        return result;
    }

    /**
     * Checks if a user exists with the given email and password.
     * @param email The email to check.
     * @param password The password to check.
     * @return true if a user exists with the matching credentials, false otherwise.
     */
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID}; // Only need to select one column to check for existence
        String selection = COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = null;
        boolean isValid = false;
        try {
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            int count = cursor.getCount();
            isValid = count > 0;
            Log.d(TAG, "checkUser for " + email + ": result count = " + count);
        } catch (Exception e) {
            Log.e(TAG, "Error checking user: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return isValid;
    }

    /**
     * Checks if an email already exists in the database.
     * @param email The email to check.
     * @return true if the email exists, false otherwise.
     */
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = null;
        boolean exists = false;
        try {
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            int count = cursor.getCount();
            exists = count > 0;
            Log.d(TAG, "checkEmailExists for " + email + ": result count = " + count);
        } catch (Exception e) {
            Log.e(TAG, "Error checking email existence: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return exists;
    }
}