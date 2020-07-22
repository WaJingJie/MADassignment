package sg.edu.np.mad.madassignment;
import android.content.Intent;
import android.widget.TextView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StaffLoginPage extends AppCompatActivity {
    private static final String FILENAME = "StaffLoginPage.java";
    private static final String TAG = "NP Library";
    DBHandler dbHandler;
    private TextView loginemail, loginpassword;
    private Button submitbutton, cancelbutton;
    //This is a StaffData object that can be used among all the classes
    public static StaffData staffdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginstaffpage);

        dbHandler = new DBHandler(this,null,null,1);

        loginemail = findViewById(R.id.staffloginemail);
        loginpassword = findViewById(R.id.staffloginpassword);
        submitbutton = findViewById(R.id.staffloginsubmit);
        cancelbutton = findViewById(R.id.stafflogincancel);
        //This method occurs when the submit button is clicked by the user
        Log.v(TAG, FILENAME + ": Submit Button Clicked");
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginemail.getText().toString();
                String password = loginpassword.getText().toString();
                Log.v(TAG, FILENAME + ": Logging in with: " + email + ": " + password);
                //This occurs when the email entered is not found in the database
                if (checkStaff(email, password) == false){
                    Log.v(TAG, FILENAME + ": Invalid user!");
                    reset();
                    return;
                }

                Log.v(TAG, FILENAME + ": Valid User! Logging in");
                // redirect to home page
                Log.v(TAG, FILENAME + ": Redirecting to Home Page");
                Intent homepage = new Intent(StaffLoginPage.this, StaffHomePage.class);
                startActivity(homepage);
            }
        });
        //This method occurs when the cancel button is clicked by the user
        Log.v(TAG, FILENAME + ": Cancel Button Clicked");
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(StaffLoginPage.this, Select.class);
                startActivity(welcomepage);
                reset();
            }
        });

    }

    //This method is for checking whether the user is an existing user and that the email and password used is valid
    public boolean checkStaff(String e, String p){
        //This searches the database for the entered email
        StaffData data = dbHandler.findStaff(e);
        //This happens when the user is not found in the database
        if(data == null){
            Log.v(TAG, FILENAME + ": Invalid email used!");
            Toast.makeText(getApplicationContext(), "Invalid email! Please re-enter again.",
                    Toast.LENGTH_LONG).show();
            reset();
            return false;
        }
        //This occurs where the password entered is not the correct password
        else if(!data.getMyStaffPassword().equals(p)) {
            Log.v(TAG, FILENAME + ": Invalid password used!");
            Toast.makeText(getApplicationContext(), "Invalid password! Please re-enter again.",
                    Toast.LENGTH_LONG).show();
            resetPassword();
            return false;
        }
        //This occurs when the user is found to exist in the database
        else{
            staffdata = data;
            return true;
        }
    }
    public int onReturn(View v)
    {
        return 0;
    }

    //This resets the login textboxes
    public void reset(){
        loginemail.setHint("Enter Your Email");
        loginpassword.setHint("Enter Your Password");
    }

    //This resets the password textbox
    public void resetPassword(){
        loginpassword.setHint("Enter Your Password");
    }

    protected void onStop(){
        super.onStop();
        finish();
    }

}

