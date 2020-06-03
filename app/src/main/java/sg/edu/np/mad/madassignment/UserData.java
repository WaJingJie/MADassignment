package sg.edu.np.mad.madassignment;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;

public class UserData {
    private String email;
    private String name;
    private String password;
    private int booksborrowed;
    private int canborrow;
    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_BOOKSBORROWED = "No Of Books Borrowed";
    public static final String COLUMN_CANBORROW = "No of Books User Can Borrow";

    //public static final String;


    public UserData()
    {
    }

    public UserData(String myEmail, String myName, String myPassword,
                    int booksBorrowed, int canBorrow) {
        this.email = myEmail;
        this.name = myName;
        this.password = myPassword;
        this.booksborrowed = booksBorrowed;
        this.canborrow = canBorrow;
    }

    public String getMyEmail() { return this.email; }

    public void setMyEmail(String myEmail) {
        this.email = myEmail;
    }

    public String getMyPassword() {
        return this.password;
    }

    public void setMyPassword(String myPassword) {
        this.password = myPassword;
    }

    public String getMyName() { return this.name; }

    public void setMyName(String myName) { this.name = myName; }

    public int getBooksBorrowed() {
        return this.booksborrowed;
    }

    public void setBooksBorrowed(int booksBorrowed) {
        this.booksborrowed = booksBorrowed;
    }

    public int getCanborrow() {
        return this.canborrow;
    }

    public void setCanborrow(int canBorrow) {
        this.canborrow = canBorrow;
    }
}
