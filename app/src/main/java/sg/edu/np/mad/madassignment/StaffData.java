package sg.edu.np.mad.madassignment;

public class StaffData {
    private String staffemail;
    private String staffname;
    private String staffpassword;
    public static final String TABLE_NAME = "Staff";
    public static final String COLUMN_STAFFEMAIL = "Staff Email";
    public static final String COLUMN_STAFFNAME = "Staff Name";
    public static final String COLUMN_STAFFPASSWORD = "Staff Password";
    private static final String FILENAME = "StaffData.java";
    private static final String TAG = "NP Library";

    public StaffData()
    {

    }

    public StaffData(String myStaffEmail, String myStaffName, String myStaffPassword) {
        this.staffemail = myStaffEmail;
        this.staffname = myStaffName;
        this.staffpassword = myStaffPassword;

    }

    public void setMyStaffEmail(String myStaffEmail) {
        this.staffemail = myStaffEmail;
    }

    public String getMyStaffEmail() { return this.staffemail; }

    public void setMyStaffPassword(String myPassword) {
        this.staffpassword = myPassword;
    }

    public String getMyStaffPassword() {
        return this.staffpassword;
    }

    public void setMyStaffName(String myStaffName) { this.staffname = myStaffName; }

    public String getMyStaffName() { return this.staffname; }

}


