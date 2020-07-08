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
    private static final int DATABASE_VERSION = 6;
    private static final String TAG = "NP Library!";
    public static final String TABLE_USERDATA= "users";
    public static final String COLUMN_EMAIL = "useremail";
    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_PASSWORD = "userpassword";
    public static final String COLUMN_NO_OF_BOOKS_BORROWED= "noofbooksborrowed";
    public static final String COLUMN_NO_OF_BOOKS_USER_CAN_BORROW = "canborrow";

    public static final String TABLE_BORROWDATA= "borrowed";
    public static final String COLUMN_BOOKEMAIL = "email";
    public static final String COLUMN_ISBN = "isbn";
    public static final String COLUMN_BOOKNAME = "bookname";
    public static final String COLUMN_BORROWDATE = "borrowdate";
    public static final String COLUMN_DUEDATE= "duedate";

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

        String CREATE_BORROWDATA_TABLE = "CREATE TABLE " + TABLE_BORROWDATA +
                "(" + COLUMN_BOOKEMAIL + " TEXT," + COLUMN_ISBN + " INTEGER,"
                + COLUMN_BOOKNAME + " TEXT," + COLUMN_BORROWDATE + " TEXT,"
                + COLUMN_DUEDATE + " TEXT" + ")";

        db.execSQL(CREATE_BORROWDATA_TABLE);
        Log.v(TAG, "DB Created: " + CREATE_BORROWDATA_TABLE);

    }

    //This updates the table by dropping the old version of the table and creating the new version

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BORROWDATA);
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

    public void addBorrowedBook(BorrowData borrowData) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKEMAIL, borrowData.getMyBookEmail());
        values.put(COLUMN_ISBN, borrowData.getISBN());
        values.put(COLUMN_BOOKNAME, borrowData.getMyBookName());
        values.put(COLUMN_BORROWDATE, borrowData.getBorrowDate());
        values.put(COLUMN_DUEDATE, borrowData.getDueDate());
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

    //This searches the table for the books borrowed by the user using the ISBN
    public BorrowData findBorrowedBooks(String bookemail, String isbn) {
        String query = "SELECT * FROM " + TABLE_BORROWDATA + " WHERE "
                + COLUMN_ISBN
                + " = \"" + isbn + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BorrowData borrowData = new BorrowData();
        Log.v(TAG, FILENAME +": Find user from database: " + query);
        /*String password;
        int booksborrow;
        int canborrow;*/

        if(cursor.moveToFirst()){
            borrowData.setMyBookEmail(cursor.getString(0));
            borrowData.setISBN(cursor.getInt(1));
            borrowData.setMyBookName(cursor.getString(2));
            borrowData.setBorrowDate(cursor.getString(3));
            borrowData.setDueDate(cursor.getString(4));
            cursor.close();
            Log.v(TAG, FILENAME + ": QueryData: " + borrowData.getBorrowDate() + borrowData.getDueDate());
        }
        else{
            borrowData = null;
            Log.v(TAG, FILENAME+ ": No data found!");
        }
        db.close();
        return borrowData;
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

    //This deletes the data of the user with the email entered from the table
    public boolean deleteBorrowedBook(String isbn) {
        boolean result = false;

        String query = "SELECT * FROM " + TABLE_BORROWDATA + " WHERE "
                + COLUMN_ISBN + " = \""
                + isbn + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        BorrowData borrowData = new BorrowData();
        if (cursor.moveToFirst()) {
            borrowData.setMyBookEmail(cursor.getString(0));
            db.delete(TABLE_BORROWDATA, COLUMN_BOOKEMAIL + " = ?",
                    new String[] { String.valueOf(borrowData.getMyBookEmail()) });
            cursor.close();
            result = true;
        }
        Log.v(TAG, FILENAME+ ": Deleting data from Database: " + query);
        db.close();
        return result;
    }

}
