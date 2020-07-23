package sg.edu.np.mad.madassignment;

public class BorrowData {
    private String bookemail;
    private String isbn;
    private String bookname;
    private String borrowdate;
    private String duedate;
    public static final String TABLE_NAME = "Books Borrowed";
    public static final String COLUMN_BOOKEMAIL = "User Email";
    public static final String COLUMN_ISBN = "ISBN";
    public static final String COLUMN_BOOKNAME = "Book Name";
    public static final String COLUMN_BORROWDATE = "Borrow Date";
    public static final String COLUMN_DUEDATE = "Due Date";
    private static final String FILENAME = "BorrowData.java";
    private static final String TAG = "NP Library";

    public BorrowData()
    {
    }

    public BorrowData(String myBookEmail, String ISBN,
                      String bookName, String borrowDate, String dueDate) {
        this.bookemail = myBookEmail;
        this.isbn = ISBN;
        this.bookname = bookName;
        this.borrowdate = borrowDate;
        this.duedate = dueDate;
    }

    public void setMyBookEmail(String myBookEmail) {
        this.bookemail = myBookEmail;
    }

    public String getMyBookEmail(){ return this.bookemail; }

    public void setISBN(String ISBN) {
        this.isbn = ISBN;
    }

    public String getISBN(){
        return this.isbn;
    }

    public void setMyBookName(String bookName) { this.bookname = bookName; }

    public String getMyBookName() { return this.bookname; }

    public void setBorrowDate(String borrowDate) { this.borrowdate = borrowDate; }

    public String getBorrowDate() {
        return this.borrowdate;
    }

    public void setDueDate(String dueDate) { this.duedate = dueDate; }

    public String getDueDate() {
        return this.duedate;
    }
}





