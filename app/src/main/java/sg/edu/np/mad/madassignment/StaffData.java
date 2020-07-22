package sg.edu.np.mad.madassignment;

public class StaffData {
    private String staffemail, staffname, staffphoneno, staffpassword;
    public static final String TABLE_NAME = "Staff";
    public static final String COLUMN_STAFFEMAIL = "Staff Email";
    public static final String COLUMN_STAFFNAME = "Staff Name";
    public static final String COLUMN_STAFFPHONENO = "Staff Phone Number";
    public static final String COLUMN_STAFFPASSWORD = "Staff Password";
    private static final String FILENAME = "StaffData.java";
    private static final String TAG = "NP Library";

    public StaffData()
    {

    }

    public StaffData(String myStaffEmail, String myStaffName, String staffPhoneNo, String myStaffPassword) {
        this.staffemail = myStaffEmail;
        this.staffname = myStaffName;
        this.staffphoneno = staffPhoneNo;
        this.staffpassword = myStaffPassword;

    }

    public void setMyStaffEmail(String myStaffEmail) {
        this.staffemail = myStaffEmail;
    }

    public String getMyStaffEmail() { return this.staffemail; }

    public void setMyStaffPhoneNo(String staffPhoneNo) {
        this.staffpassword = staffPhoneNo;
    }

    public String getMyStaffPhoneNo() {
        return this.staffpassword;
    }

    public void setMyStaffPassword(String myPassword) {
        this.staffpassword = myPassword;
    }

    public String getMyStaffPassword() {
        return this.staffpassword;
    }

    public void setMyStaffName(String myStaffName) { this.staffname = myStaffName; }

    public String getMyStaffName() { return this.staffname; }

}


