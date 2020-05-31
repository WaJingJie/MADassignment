package sg.edu.np.mad.madassignment;

import java.util.ArrayList;
import java.util.Date;

public class UserData {
    private String MyEmail;
    private String MyPassword;
    private ArrayList<Integer> ISBN = new ArrayList<>();
    private ArrayList<String> Name = new ArrayList<>();
    private ArrayList<Date> DateBorrowed = new ArrayList<>();
    private ArrayList<Date> DueDate = new ArrayList<>();

    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String;


    public UserData()
    {
    }

    public UserData(String myEmail, String myPassword, ArrayList<Integer> myISBN,
                    ArrayList<String> myName, ArrayList<Date> dateBorrowed, ArrayList<Date> dueDate) {
        this.MyEmail = myEmail;
        this.MyPassword = myPassword;
        this.ISBN = myISBN;
        this.Name = myName;
        this.DateBorrowed = dateBorrowed;
        this.DueDate= dueDate;
    }

    public ArrayList<Integer> getISBN() {
        return this.ISBN;
    }

    public void setISBN(ArrayList<Integer> isbn) {
        this.ISBN = isbn;
    }

    public ArrayList<String> getName() { return this.Name; }

    public void setName(ArrayList<String> name) { this.Name = name; }

    public ArrayList<Date> getDateBorrowed() {
        return this.DateBorrowed;
    }

    public void setDateBorrowed(ArrayList<Date> dateborrowed) {
        this.DateBorrowed = dateborrowed;
    }

    public ArrayList<Date> getDueDate() { return this.DueDate; }

    public void setDueDate(ArrayList<Date> duedate) { this.DueDate = duedate; }

    public String getMyEmail() { return this.MyEmail; }

    public void setMyEmail(String myEmail) {
        this.MyEmail = myEmail;
    }

    public String getMyPassword() {
        return this.MyPassword;
    }

    public void setMyPassword(String myPassword) {
        this.MyPassword = myPassword;
    }
}
