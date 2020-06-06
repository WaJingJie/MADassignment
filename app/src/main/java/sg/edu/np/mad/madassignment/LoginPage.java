package sg.edu.np.mad.madassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.TextView;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {
    private static final String FILENAME = "LoginPage.java";
    private static final String TAG = "NP Library";
    DBHandler dbHandler;
    private TextView loginemail, loginpassword;
    private Button submitbutton, cancelbutton;
    //This is a Userdata object that can be used among all the classes
    public static UserData userdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        dbHandler = new DBHandler(this,null,null,1);

        loginemail = findViewById(R.id.loginemail);
        loginpassword = findViewById(R.id.loginpassword);
        submitbutton = findViewById(R.id.loginsubmit);
        cancelbutton = findViewById(R.id.logincancel);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginemail.getText().toString();
                String password = loginpassword.getText().toString();
                Log.v(TAG, FILENAME + ": Logging in with: " + email + ": " + password);
                //This occurs when the email entered is not found in the database
                if (checkUser(email, password) == false){
                    Log.v(TAG, FILENAME + ": Invalid user!");
                    reset();
                    return;
                }

                Log.v(TAG, FILENAME + ": Valid User! Logging in");
                // redirect to home page
                Log.v(TAG, FILENAME + ": Redirecting to Home Page");
                Intent homepage = new Intent(LoginPage.this, HomePage.class);
                startActivity(homepage);
            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(LoginPage.this, MainActivity.class);
                startActivity(welcomepage);
                reset();
            }
        });

    }


    public boolean checkUser(String e, String p){
        UserData data = dbHandler.findUser(e);
        if(data == null){
            Toast.makeText(getApplicationContext(), "Invalid email! Please re-enter again.",
                    Toast.LENGTH_LONG).show();
            reset();
            return false;
        }
        else if(!data.getMyPassword().equals(p)) {
            Toast.makeText(getApplicationContext(), "Invalid password! Please re-enter again.",
                    Toast.LENGTH_LONG).show();
            resetPassword();
            return false;
        }
        else{
            userdata = data;
            return true;
        }
    }
    public int onReturn(View v)
    {
        return 0;
    }

    public void reset(){
        loginemail.setHint("Enter Your Email");
        loginpassword.setHint("Enter Your Password");
    }

    public void resetPassword(){
        loginpassword.setHint("Enter Your Password");
    }

    protected void onStop(){
        super.onStop();
        finish();
    }

}
