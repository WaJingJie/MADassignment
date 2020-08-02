package sg.edu.np.mad.madassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "NP Library";
    Button loginbtn, signupbtn, returnselectbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = findViewById(R.id.welcomelogin);
        signupbtn = findViewById(R.id.welcomesignup);
        returnselectbtn = findViewById(R.id.returnstudent);

        //This method is to redirect the user to the login page
        Log.v(TAG, FILENAME + ": Redirecting to Login Page");
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginpage = new Intent(MainActivity.this, LoginPage.class);
                startActivity(loginpage);
            }
        });

        //This method is to redirect the user to the sign up page
        Log.v(TAG, FILENAME + ": Redirecting to Sign Up Page");
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signuppage = new Intent(MainActivity.this, SignUpPage.class);
                startActivity(signuppage);
            }
        });

        //This method is to redirect the user to the sign up page
        Log.v(TAG, FILENAME + ": Redirecting to Select Page");
        returnselectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnpage = new Intent(MainActivity.this, Select.class);
                startActivity(returnpage);
            }
        });

    }

}
