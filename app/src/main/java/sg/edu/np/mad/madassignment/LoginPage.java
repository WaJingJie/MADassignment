package sg.edu.np.mad.madassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.TextView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {
    private static final String FILENAME = "LoginPage.java";
    private static final String TAG = "NP Library";
    //DBHandler dbHandler;
    private TextView loginemail, loginpassword;
    private Button submitbutton, cancelbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

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
                if (validateUser(email, password) == true){

                    return;
                }

                // redirect to level select page
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

    public boolean validateUser(String e, String p){
        Log.v(TAG, FILENAME + ": Invalid user!");
        reset();
        Toast.makeText(getApplicationContext(), "Invalid email or password! Please re-enter again.",
                Toast.LENGTH_LONG).show();
        return false;
    }
    public void reset(){
        loginemail.setText("Enter Your Email");
        loginpassword.setText("Enter Your Password");
    }

    protected void onStop(){
        super.onStop();
        finish();
    }

}
