/*package sg.edu.np.mad.madassignment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
public class DBHandler extends SQLiteOpenHelper{
    private static final String FILENAME = "DBHandler.java";
    public static final String DATABASE_NAME = "NPLibrary.db";
    private static final String TAG = "NP Library!";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        // HINT:
            //This is used to init the database.
         //
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ///HINT:
            //This is triggered on DB creation.
            //Log.v(TAG, "DB Created: " + CREATE_ACCOUNTS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //HINT:
            //This is triggered if there is a new version found. ALL DATA are replaced and irreversible.
         //
    }

    public void addUser(UserData userData) {
            // HINT:
                //This adds the user to the database based on the information given.
                //Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
             //
    }

    public UserData findUser(String username) {
        //HINT:
           // This finds the user that is specified and returns the data information if it is found.
           // If not found, it will return a null.
           // Log.v(TAG, FILENAME +": Find user form database: " + query);

           // The following should be used in getting the query data.
           // you may modify the code to suit your design.

            if(cursor.moveToFirst()){
                do{
                    ...
                    .....
                    ...
                }while(cursor.moveToNext());
                Log.v(TAG, FILENAME + ": QueryData: " + queryData.getLevels().toString() + queryData.getScores().toString());
            }
            else{
                Log.v(TAG, FILENAME+ ": No data found!");
            }

    }

    public boolean deleteAccount(String username) {
        //HINT:
            //This finds and delete the user data in the database.
            //This is not reversible.
            //Log.v(TAG, FILENAME + ": Database delete user: " + query);
         //

    }
}*/
