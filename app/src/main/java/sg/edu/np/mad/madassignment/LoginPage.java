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
    DBHandler dbHandler;
    private TextView loginemail, loginpassword;
    private Button submitbtn, cancelbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        loginemail = findViewById(R.id.loginemail);
        loginpassword = findViewById(R.id.loginpassword);
        submitbtn = findViewById(R.id.loginsubmit);
        cancelbtn = findViewById(R.id.logincancel);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginemail.getText().toString();
                String password = loginpassword.getText().toString();
                Log.v(TAG, FILENAME + ": Logging in with: " + email + ": " + password);
                if (userValid(email, password) == false){
                    Log.v(TAG, FILENAME + ": Invalid user!");
                    reset();
                    Toast.makeText(getApplicationContext(), "Invalid email or password! Please re-enter again.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // redirect to level select page
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(LoginPage.this, MainActivity.class);
                startActivity(welcomepage);
                reset();
            }
        });

    }

    public boolean userValid(String e, String p){
        return true;
    }
    public void reset(){
        loginemail.setText("Enter Your Email");
        loginpassword.setText("Enter Your Password");
    }

}
