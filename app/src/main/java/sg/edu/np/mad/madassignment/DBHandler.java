package sg.edu.np.mad.madassignment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
public class DBHandler extends SQLiteOpenHelper{
    private static final String FILENAME = "DBHandler.java";
    public static final String DATABASE_NAME = "NPLibrary.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TAG = "NP Library!";
    public static final String TABLE_USERDATA= "users";
    public static final String COLUMN_EMAIL = "useremail";
    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_PASSWORD = "userpassword";
    public static final String COLUMN_NO_OF_BOOKS_BORROWED= "noofbooksborrowed";
    public static final String COLUMN_NO_OF_BOOKS_USER_CAN_BORROW = "canborrow";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //This method creates a table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERDATA_TABLE = "CREATE TABLE " + TABLE_USERDATA +
                "(" + COLUMN_EMAIL + " TEXT," + COLUMN_NAME
                + " TEXT," + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_NO_OF_BOOKS_BORROWED + " INTEGER,"
                + COLUMN_NO_OF_BOOKS_USER_CAN_BORROW + " INTEGER" + ")";
        db.execSQL(CREATE_USERDATA_TABLE);
        Log.v(TAG, "DB Created: " + CREATE_USERDATA_TABLE);


    }

    //This updates the table by dropping the old version of the table and creating the new version

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
        onCreate(db);
    }

    //This adds new user data to the table
    public void addUser(UserData userData) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, userData.getMyEmail());
        values.put(COLUMN_NAME, userData.getMyName());
        values.put(COLUMN_PASSWORD, userData.getMyPassword());
        values.put(COLUMN_NO_OF_BOOKS_BORROWED, userData.getBooksBorrowed());
        values.put(COLUMN_NO_OF_BOOKS_USER_CAN_BORROW, userData.getCanborrow());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USERDATA, null, values);
        db.close();
        Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
    }

    //This searches the table for the user using the email entered
    public UserData findUser(String email) {
        String query = "SELECT * FROM " + TABLE_USERDATA + " WHERE "
                + COLUMN_EMAIL
                + " = \"" + email + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserData userData = new UserData();
        Log.v(TAG, FILENAME +": Find user from database: " + query);
        /*String password;
        int booksborrow;
        int canborrow;*/

        if(cursor.moveToFirst()){
            userData.setMyEmail(cursor.getString(0));
            userData.setMyName(cursor.getString(1));
            userData.setMyPassword(cursor.getString(2));
            userData.setBooksBorrowed(cursor.getInt(3));
            userData.setCanborrow(cursor.getInt(4));
            cursor.close();
            Log.v(TAG, FILENAME + ": QueryData: " + userData.getBooksBorrowed() + userData.getCanborrow());
        }
        else{
            userData = null;
            Log.v(TAG, FILENAME+ ": No data found!");
        }
        db.close();
        return userData;
    }

    //This deletes the data of the user with the email entered from the table
    public boolean deleteAccount(String email) {
        boolean result = false;

        String query = "SELECT * FROM " + TABLE_USERDATA + " WHERE "
                + COLUMN_EMAIL + " = \""
                + email + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        UserData userData = new UserData();
        if (cursor.moveToFirst()) {
            userData.setMyEmail(cursor.getString(0));
            db.delete(TABLE_USERDATA, COLUMN_EMAIL + " = ?",
            new String[] { String.valueOf(userData.getMyEmail()) });
            cursor.close();
            result = true;
        }
        Log.v(TAG, FILENAME+ ": Deleting data from Database: " + query);
        db.close();
        return result;
    }

}
