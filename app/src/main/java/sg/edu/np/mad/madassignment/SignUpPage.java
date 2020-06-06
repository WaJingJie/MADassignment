package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SignUpPage extends AppCompatActivity {
    private static final String FILENAME = "LoginPage.java";
    private static final String TAG = "NP Library";
    DBHandler dbHandler;
    private EditText signupemail, signupname, signuppassword, signupconfirm;
    private Button submitbutton, cancelbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);

        dbHandler = new DBHandler(this,null,null,1);

        signupemail = findViewById(R.id.signupemail);
        signupname = findViewById(R.id.signupname);
        signuppassword = findViewById(R.id.signuppassword);
        signupconfirm = findViewById(R.id.signupconfirm);
        submitbutton = findViewById(R.id.signupsubmit);
        cancelbutton = findViewById(R.id.signupcancel);

        //This method occurs when the submit button is clicked by the user
        Log.v(TAG, FILENAME + ": Submit Button Clicked");
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = signupemail.getText().toString();
                String name = signupname.getText().toString();
                String password = signuppassword.getText().toString();
                String confirm = signupconfirm.getText().toString();
                int nofbooksborrowed = 0;
                int canborrow = 9;


                Log.v(TAG, FILENAME + ": Redirecting to Login Page");
                //This method is to check if any of the sign-up boxes are empty
                if(email.isEmpty() || password.isEmpty() || name.isEmpty() || confirm.isEmpty()){
                    Toast.makeText(SignUpPage.this, "Please complete all the details.", Toast.LENGTH_SHORT).show();

                }
                else{
                    //This method is to check whether the email has been already used by an existing user
                    dupvalidate(email, name, password, nofbooksborrowed, canborrow);
                }



            }
        });

        //This method occurs when the cancel button is clicked by the user
        Log.v(TAG, FILENAME + ": Cancel Button Clicked");
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, FILENAME + ": Return to welcome page");
                //This intent redirects the user back to the welcome page
                Intent welcomepage = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(welcomepage);
                reset();
                finish();
            }
        });
    }

    //This method is to check whether the email has been already used by an existing user

    public void dupvalidate(String e, String n, String p, int b, int c){

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        //This searches the database for the entered email
        Log.v(TAG, FILENAME + ": Searching database for email!");
        UserData data = dbHandler.findUser(e);
        //This occurs when the email has been found in the database
        if(data != null){
            Toast.makeText(SignUpPage.this, "User already exists! Please enter another email.",
                    Toast.LENGTH_LONG).show();
        }
        //This occurs when the email has not been found in the database
        else{

            UserData userdata = new UserData(e, n, p, b, c);
            //This adds the new user information into the database
            dbHandler.addUser(userdata);
            Log.v(TAG, FILENAME + ": New user successfully created!");
            Toast.makeText(SignUpPage.this, "Account created successfully!",
                    Toast.LENGTH_SHORT).show();
            //This saves the data into the public static Userdata object
            LoginPage.userdata = userdata;
            //This redirects the user to the home page
            Log.v(TAG, FILENAME + ": Go to home page");
            Intent homepage = new Intent(SignUpPage.this, HomePage.class);
            startActivity(homepage);
            reset();
        }
    }

    // This method resets the text in the textboxes in the Sign Up page
    public void reset(){
        Log.v(TAG, FILENAME + ": Resetting Sign Up page");
        signupemail.setHint("Enter Your Email");
        signupname.setHint("Enter Your Name");
        signuppassword.setHint("Enter Your Password");
        signupconfirm.setHint("Confirm Your Password");
    }

    protected void onStop(){
        super.onStop();
        finish();
    }
}
