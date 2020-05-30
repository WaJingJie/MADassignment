package sg.edu.np.mad.madassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.EditText;
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


        dbHandler = new DBHandler(this);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _username = username.getText().toString();
                String _password = password.getText().toString();

                Log.d(TAG, FILENAME + format(": New user creation with %s: %s",_username,_password));

                if (dbhandler.findUser(_username) != null){
                    Log.d(TAG, "User already exist during new user creation.");
                    resetText();
                    return;
                }

                ArrayList<Integer> initial_level_list = new ArrayList<>();
                ArrayList<Integer> initial_score_list = new ArrayList<>();
                initial_list(initial_score_list, initial_level_list);

                UserData data = new UserData(_username, _password, initial_level_list, initial_score_list);
                dbhandler.addUser(data);
                Log.v(TAG, FILENAME + ": New user created successfully!");
                resetText();

                // redirect to login page
                Log.v(TAG, FILENAME + ": Redirect to login page");
                Intent activityName = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(activityName);
                Toast.makeText(Main2Activity.this, "The account is created successfully.", Toast.LENGTH_SHORT).show();


            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, FILENAME + ": Redirect to login page");
                Intent redirect_to_login = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(redirect_to_login);
                finish();
            }
        });
    }
}
