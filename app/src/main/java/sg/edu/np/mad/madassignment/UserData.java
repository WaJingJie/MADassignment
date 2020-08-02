package sg.edu.np.mad.madassignment;

public class UserData {
    private String email, name, phoneno, password;
    private int booksborrowed;
    private int canborrow;

    public UserData()
    {
    }

    public UserData(String myEmail, String myName, String phoneNumber, String myPassword,
                    int booksBorrowed, int canBorrow) {
        this.email = myEmail;
        this.name = myName;
        this.phoneno = phoneNumber;
        this.password = myPassword;
        this.booksborrowed = booksBorrowed;
        this.canborrow = canBorrow;
    }

    public void setMyEmail(String myEmail) {
        this.email = myEmail;
    }

    public String getMyEmail() { return this.email; }

    public void setMyPassword(String myPassword) {
        this.password = myPassword;
    }

    public String getMyPassword() {
        return this.password;
    }

    public void setMyName(String myName) { this.name = myName; }

    public String getMyName() { return this.name; }

    public void setMyPhoneNo(String phoneNumber) { this.phoneno = phoneNumber; }

    public String getMyPhoneNo() { return this.phoneno; }

    public void setBooksBorrowed(int booksBorrowed) { this.booksborrowed = booksBorrowed; }

    public int getBooksBorrowed() {
        return this.booksborrowed;
    }

    public void setCanborrow(int canBorrow) { this.canborrow = canBorrow; }

    public int getCanborrow() {
        return this.canborrow;
    }


}
