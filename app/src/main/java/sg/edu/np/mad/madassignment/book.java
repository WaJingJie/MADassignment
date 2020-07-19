package sg.edu.np.mad.madassignment;

public class book {
    public int id;
    public String isbn, bookname, status;

    public book(int id, String isbn, String bookname, String status){
        this.id = id;
        this.isbn = isbn;
        this.bookname = bookname;
        this.status = status;
    }

    public book(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
