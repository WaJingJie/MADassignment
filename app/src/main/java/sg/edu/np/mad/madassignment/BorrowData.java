package sg.edu.np.mad.madassignment;

import java.io.Serializable;

public class BorrowData implements Serializable {
    private String bookemail;
    private String isbn;
    private String bookname;
    private String borrowdate;
    private String duedate;

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





