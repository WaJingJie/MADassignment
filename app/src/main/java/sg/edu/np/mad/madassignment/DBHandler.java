package sg.edu.np.mad.madassignment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{
    private static final String FILENAME = "DBHandler.java";
    public static final String DATABASE_NAME = "NPLibrary.db";
    private static final int DATABASE_VERSION = 8;
    private static final String TAG = "NP Library!";
    public static final String TABLE_USERDATA= "users";
    public static final String COLUMN_EMAIL = "useremail";
    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_PHONENUMBER = "userphoneno";
    public static final String COLUMN_PASSWORD = "userpassword";
    public static final String COLUMN_NO_OF_BOOKS_BORROWED= "noofbooksborrowed";
    public static final String COLUMN_NO_OF_BOOKS_USER_CAN_BORROW = "canborrow";

    public static final String TABLE_STAFFDATA= "staff";
    public static final String COLUMN_STAFFEMAIL = "staffemail";
    public static final String COLUMN_STAFFNAME = "staffname";
    public static final String COLUMN_STAFFPHONENO = "staffphoneno";
    public static final String COLUMN_STAFFPASSWORD = "staffpassword";

    public static final String TABLE_BORROWDATA= "borrowed";
    public static final String COLUMN_BOOKEMAIL = "email";
    public static final String COLUMN_ISBN = "isbn";
    public static final String COLUMN_BOOKNAME = "bookname";
    public static final String COLUMN_BORROWDATE = "borrowdate";
    public static final String COLUMN_DUEDATE= "duedate";

    //db for books
    public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_BISBN = "isbn";
    public static final String COLUMN_BNAME = "bookname";
    public static final String COLUMN_STATUS = "status";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //This method creates a table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERDATA_TABLE = "CREATE TABLE " + TABLE_USERDATA +
                "(" + COLUMN_EMAIL + " TEXT," + COLUMN_NAME
                + " TEXT," + COLUMN_PHONENUMBER
                + " TEXT," + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_NO_OF_BOOKS_BORROWED + " INTEGER,"
                + COLUMN_NO_OF_BOOKS_USER_CAN_BORROW + " INTEGER" + ")";
        db.execSQL(CREATE_USERDATA_TABLE);
        Log.v(TAG, "DB Created: " + CREATE_USERDATA_TABLE);

        String CREATE_STAFFDATA_TABLE = "CREATE TABLE " + TABLE_STAFFDATA +
                "(" + COLUMN_STAFFEMAIL + " TEXT," + COLUMN_STAFFNAME
                + " TEXT," + COLUMN_STAFFPHONENO
                + " TEXT," + COLUMN_STAFFPASSWORD + " TEXT" + ")";
        //initial catalog of staff
        db.execSQL(CREATE_STAFFDATA_TABLE);
        db.execSQL("INSERT INTO staff ('staffemail','staffname', 'staffphoneno','staffpassword') VALUES('npstaff123@gmail.com','April Lim', '87132638','passwordstaff');");
        db.execSQL("INSERT INTO staff ('staffemail','staffname','staffphoneno','staffpassword') VALUES('npstaff234@gmail.com','Steven Tan','8562471','passwordstaff');");
        db.execSQL("INSERT INTO staff ('staffemail','staffname','staffphoneno','staffpassword') VALUES('npstaff345@gmail.com','Kenny Wong','94372821','passwordstaff');");
        Log.v(TAG, "DB Created: " + CREATE_STAFFDATA_TABLE);

        String CREATE_BORROWDATA_TABLE = "CREATE TABLE " + TABLE_BORROWDATA +
                "(" + COLUMN_BOOKEMAIL + " TEXT," + COLUMN_ISBN + " INTEGER,"
                + COLUMN_BOOKNAME + " TEXT," + COLUMN_BORROWDATE + " TEXT,"
                + COLUMN_DUEDATE + " TEXT" + ")";
        db.execSQL(CREATE_BORROWDATA_TABLE);
        Log.v(TAG, "DB Created: " + CREATE_BORROWDATA_TABLE);

        //db for books
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS +
                "("
                + COLUMN_BISBN + " TEXT,"
                + COLUMN_BNAME + " TEXT,"
                + COLUMN_STATUS + " TEXT" +")";
        db.execSQL(CREATE_BOOKS_TABLE);
        //initial catalog of books
        db.execSQL("INSERT INTO books ('isbn','bookname','status') VALUES('978-1-4028-9463-6','Introduction to programming','Available');");
        db.execSQL("INSERT INTO books ('isbn','bookname','status') VALUES('978-1-4028-9463-5','Introduction to android','Available');");
        db.execSQL("INSERT INTO books ('isbn','bookname','status') VALUES('978-1-4028-9463-4','Hello world','Available');");
        db.execSQL("INSERT INTO books ('isbn','bookname','status') VALUES('978-1-4028-9463-3','Hello','Unavailable');");
        //end of initial catalog of books
        Log.v(TAG, "DB Created: " + CREATE_BOOKS_TABLE);

    }

    //This updates the table by dropping the old version of the table and creating the new version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFFDATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BORROWDATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    //This adds new user data to the table
    public void addUser(UserData userData) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, userData.getMyEmail());
        values.put(COLUMN_NAME, userData.getMyName());
        values.put(COLUMN_PHONENUMBER, userData.getMyPhoneNo());
        values.put(COLUMN_PASSWORD, userData.getMyPassword());
        values.put(COLUMN_NO_OF_BOOKS_BORROWED, userData.getBooksBorrowed());
        values.put(COLUMN_NO_OF_BOOKS_USER_CAN_BORROW, userData.getCanborrow());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USERDATA, null, values);
        db.close();
        Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
    }

    //This adds new staff data to the table
    /*public void addStaff(StaffData staffData){
        ContentValues values = new ContentValues();
        values.put(COLUMN_STAFFEMAIL, staffData.getMyStaffEmail());
        values.put(COLUMN_STAFFNAME, staffData.getMyStaffName());
        if(staffData.getMyStaffPhoneNo().equals(null)){
            values.put(COLUMN_STAFFPHONENO, "Add Phone Number");
        }
        else{
            values.put(COLUMN_STAFFPHONENO, staffData.getMyStaffPhoneNo());
        }
        values.put(COLUMN_STAFFPASSWORD, staffData.getMyStaffPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_STAFFDATA, null, values);
        db.close();
        Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
    }*/

    //method to add books and search books
    public void addBook(String isbn, String bookname, String status){
        ContentValues values = new ContentValues();
        values.put(COLUMN_BISBN, isbn);
        values.put(COLUMN_BNAME, bookname);
        values.put(COLUMN_STATUS, status);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BOOKS, null, values);
        db.close();
        Log.v(TAG, FILENAME + ": Adding data for book db: " + values.toString());
    }


    //function to get all books
    public List<Book> getBook(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"ID, isbn, bookname, status"};
        String tablename = "books";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,null,null,null,null,null);
        List<Book> result = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Book booklist= new Book();
                booklist.setIsbn(cursor.getString(cursor.getColumnIndex("isbn")));
                booklist.setBookname(cursor.getString(cursor.getColumnIndex("bookname")));
                booklist.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                result.add(booklist);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //get book name
    public List<String> getBookname(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"bookname"};
        String tablename = "books";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,null,null,null,null,null);
        List<String> result = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(cursor.getColumnIndex("bookname")));
            }while(cursor.moveToNext());
        }
        return result;
    }

    //get book by name
    public List<Book> getBookByName(String bookname){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"ID, isbn, bookname, status"};
        String tablename = "books";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"bookname LIKE ?",new String[]{"%"+bookname+"%"},null,null,null);
        List<Book> result = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Book booklist= new Book();
                booklist.setIsbn(cursor.getString(cursor.getColumnIndex("isbn")));
                booklist.setBookname(cursor.getString(cursor.getColumnIndex("bookname")));
                booklist.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                result.add(booklist);
            }while(cursor.moveToNext());
        }
        return result;
    }
    //end of code for add books and search books

    //get the list of isbn
    public ArrayList<String> getIsbn(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"isbn"};
        String tablename = "books";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"status ='Available'",null,null,null,null);
        ArrayList<String> result = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(cursor.getColumnIndex("isbn")));
            }while(cursor.moveToNext());
        }
        return result;
    }
    //end

    //get the list of isbn
    public ArrayList<String> getAllIsbn(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"isbn"};
        String tablename = "books";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"",null,null,null,null);
        ArrayList<String> result = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(cursor.getColumnIndex("isbn")));
            }while(cursor.moveToNext());
        }
        return result;
    }
    //end

    //get book name from the select isbn
    public String getBookByISBN(String isbn){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"bookname"};
        String tablename = "books";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"isbn = ? AND status ='Available'",new String[]{isbn},null,null,null);
        String result ="";
        if(cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndex("bookname"));
            }while(cursor.moveToNext());
        }
        return result;
    }
    //end

    //get name number
    public String getnamebyemail(String email){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"username"};
        String tablename = "users";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"useremail = ?",new String[]{email},null,null,null);
        String result ="";
        if(cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndex("username"));
            }while(cursor.moveToNext());
        }
        return result;
    }
    //end

    //get phone number
    public String getphonebyemail(String email){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"userphoneno"};
        String tablename = "users";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"useremail = ?",new String[]{email},null,null,null);
        String result ="";
        if(cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndex("userphoneno"));
            }while(cursor.moveToNext());
        }
        return result;
    }
    //end

    //get staff phone number
    public String getphonebystaffemail(String email){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"staffphoneno"};
        String tablename = "staff";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"staffemail = ?",new String[]{email},null,null,null);
        String result ="";
        if(cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndex("staffphoneno"));
            }while(cursor.moveToNext());
        }
        return result;
    }
    //end

    //get book name from the select isbn
    public String getBookName(String isbn){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"bookname"};
        String tablename = "books";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"isbn = ?",new String[]{isbn},null,null,null);
        String result ="";
        if(cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndex("bookname"));
            }while(cursor.moveToNext());
        }
        return result;
    }

    //get book isbn from the select isbn
    public Integer getBookID(String isbn){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"ID"};
        String tablename = "books";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"isbn = ?",new String[]{isbn},null,null,null);
        Integer result = 0;
        if(cursor.moveToFirst()){
            do{
                result = cursor.getInt(cursor.getColumnIndex("ID"));
            }while(cursor.moveToNext());
        }
        return result;
    }

    //get book isbn from the select isbn
    public String getBookISBN(String isbn){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"isbn"};
        String tablename = "books";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"isbn = ?",new String[]{isbn},null,null,null);
        String result ="";
        if(cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndex("isbn"));
            }while(cursor.moveToNext());
        }
        return result;
    }

    //get book status from the select isbn
    public String getBookStatus(String isbn){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"status"};
        String tablename = "books";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"isbn = ?",new String[]{isbn},null,null,null);
        String result ="";
        if(cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndex("status"));
            }while(cursor.moveToNext());
        }
        return result;
    }


    //adding borrow book function
    public void addBorrowedBook(String email, String isbn, String bookname, String borrowdate, String duedate) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKEMAIL, email);
        values.put(COLUMN_ISBN, isbn);
        values.put(COLUMN_BOOKNAME, bookname);
        values.put(COLUMN_BORROWDATE, borrowdate);
        values.put(COLUMN_DUEDATE, duedate);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BORROWDATA, null, values);
        db.close();
        Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
    }
    //adding borrow book function end

    //updating book status
    public boolean updatebookStatus(String isbn){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ISBN, isbn);
        values.put(COLUMN_STATUS, "Unavaliable");

        db.update(TABLE_BOOKS, values, "isbn =?",new String[]{isbn});
        return true;
    }
    //end

    //select all book borrowed based on the user's email
    public List<BorrowData> getborrowbyEmail(String email){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"isbn, bookname, borrowdate, duedate"};
        String tablename = "borrowed";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"email =?",new String[]{email},null,null,null);
        List<BorrowData> result = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                BorrowData borrowlist = new BorrowData();
                borrowlist.setISBN(cursor.getString(cursor.getColumnIndex("isbn")));
                borrowlist.setMyBookName(cursor.getString(cursor.getColumnIndex("bookname")));
                borrowlist.setBorrowDate(cursor.getString(cursor.getColumnIndex("borrowdate")));
                borrowlist.setDueDate(cursor.getString(cursor.getColumnIndex("duedate")));
                result.add(borrowlist);
            }while(cursor.moveToNext());
        }
        return result;
    }
    //end

    //add/update name function goes here
    public boolean updateName(String email, String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_NAME, name);

        db.update(TABLE_USERDATA, values, "useremail =?",new String[]{email});
        return true;
    }
    //add/update name function end

    //add/update phone number function goes here
    public boolean updatePhonenum(String email, String phoneno){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONENUMBER, phoneno);

        db.update(TABLE_USERDATA, values, "useremail =?",new String[]{email});
        return true;
    }
    //add/update phone number function end

    //update user password function
    public boolean updatePwd(String email, String pwd){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, pwd);
        db.update(TABLE_USERDATA, values, "useremail =?",new String[]{email});
        return true;
    }
    //update user password end

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
            userData.setMyPhoneNo(cursor.getString(2));
            userData.setMyPassword(cursor.getString(3));
            userData.setBooksBorrowed(cursor.getInt(4));
            userData.setCanborrow(cursor.getInt(5));
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

    //This searches the table for the staff using the email entered
    public StaffData findStaff(String email) {
        String query = "SELECT * FROM " + TABLE_STAFFDATA + " WHERE "
                + COLUMN_STAFFEMAIL
                + " = \"" + email + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        StaffData staffData = new StaffData();
        Log.v(TAG, FILENAME +": Find user from database: " + query);

        if(cursor.moveToFirst()){
            staffData.setMyStaffEmail(cursor.getString(0));
            staffData.setMyStaffName(cursor.getString(1));
            staffData.setMyStaffPhoneNo(cursor.getString(2));
            staffData.setMyStaffPassword(cursor.getString(3));
            cursor.close();
            Log.v(TAG, FILENAME + ": QueryData: " + staffData.getMyStaffName() + staffData.getMyStaffPassword());
        }
        else{
            staffData = null;
            Log.v(TAG, FILENAME+ ": No data found!");
        }
        db.close();
        return staffData;
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
            borrowData.setISBN(cursor.getString(1));
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

    //This searches the table for the book selected by the staff
    /*public Book findBook(Integer id) {
        String query = "SELECT * FROM " + TABLE_BOOKS + " WHERE "
                + COLUMN_BID
                + " = \"" + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Book bookdata = new Book();
        Log.v(TAG, FILENAME +": Find user from database: " + query);

        if(cursor.moveToFirst()){
            bookdata.setIsbn(cursor.getString(0));
            bookdata.setBookname(cursor.getString(1));
            bookdata.setStatus(cursor.getString(2));
            cursor.close();
            Log.v(TAG, FILENAME + ": QueryData: " + bookdata.getBookname() + bookdata.getStatus());
        }
        else{
            bookdata = null;
            Log.v(TAG, FILENAME+ ": No data found!");
        }
        db.close();
        return bookdata;
    }*/

    //This updates staff update to the table
    public boolean staffUpdateName(String e, String n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STAFFEMAIL, e);
        values.put(COLUMN_STAFFNAME, n);
        db.update(TABLE_STAFFDATA, values, "staffemail =?",new String[]{e});
        Log.v(TAG, FILENAME + ": Updating data for Database: " + values.toString());
        return true;
    }

    //add/update phone number function goes here
    public boolean staffUpdatePhonenum(String e, String pn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STAFFEMAIL, e);
        values.put(COLUMN_STAFFPHONENO, pn);
        db.update(TABLE_STAFFDATA, values, "staffemail =?",new String[]{e});
        Log.v(TAG, FILENAME + ": Updating data for Database: " + values.toString());
        return true;
    }
    //update phone number function end
    //update user password function
    public boolean staffUpdatePwd(String e, String pwd){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STAFFEMAIL, e);
        values.put(COLUMN_STAFFPASSWORD, pwd);
        db.update(TABLE_STAFFDATA, values, "staffemail =?",new String[]{e});
        return true;
    }

    //get staff name
    public String getstaffname(String email){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"staffname"};
        String tablename = "staff";

        qb.setTables(tablename);

        Cursor cursor = qb.query(db, sqlSelect,"staffemail = ?",new String[]{email},null,null,null);
        String result ="";
        if(cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndex("staffname"));
            }while(cursor.moveToNext());
        }
        return result;
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
            borrowData.setISBN(cursor.getString(1));
            db.delete(TABLE_BORROWDATA, COLUMN_ISBN + " = ?",
                    new String[] { String.valueOf(borrowData.getISBN()) });
            cursor.close();
            result = true;
        }
        Log.v(TAG, FILENAME+ ": Deleting data from Database: " + query);
        db.close();
        return result;
    }

    //This deletes the data of the book entered by the user
    /*public boolean deleteBook(Integer id) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_BOOKS + " WHERE "
                + COLUMN_BID + " = \""
                + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Book bookData = new Book();
        if (cursor.moveToFirst()) {
            bookData.setId(cursor.getInt(0));
            db.delete(TABLE_BOOKS, COLUMN_BID + " = ?",
                    new String[] { String.valueOf(bookData.getId()) });
            cursor.close();
            result = true;
        }
        Log.v(TAG, FILENAME+ ": Deleting data from Database: " + query);
        db.close();
        return result;
    }*/
}
