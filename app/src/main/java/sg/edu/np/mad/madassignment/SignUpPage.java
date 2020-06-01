package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpPage extends AppCompatActivity {
    private static final String FILENAME = "LoginPage.java";
    private static final String TAG = "NP Library";
    /BHandler dbHandler;
    private TextView signupemail, signupname, signuppassword, signupconfirm;
    private Button submitbutton, cancelbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        signupemail = findViewById(R.id.signupemail);
        signupname = findViewById(R.id.signupname);
        signuppassword = findViewById(R.id.signuppassword);
        signupconfirm = findViewById(R.id.signupconfirm);
        submitbutton = findViewById(R.id.signupsubmit);
        cancelbutton = findViewById(R.id.signupcancel);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = signupemail.getText().toString();
                String name = signupname.getText().toString();
                String password = signuppassword.getText().toString();

                if (userExists(email) == false){

                }

                // redirect to level select page
            }
        });


        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(welcomepage);
                reset();
            }
        });
    }

    public boolean userExists(String e){
        Log.v(TAG, FILENAME + ": User already exists!");
        reset();
        Toast.makeText(getApplicationContext(), "User already exists! Please enter another email.",
                Toast.LENGTH_LONG).show();
        return true;
    }

    public void reset(){
        signupemail.setText("Enter Your Email");
        signupname.setText("Enter Your Name");
        signuppassword.setText("Enter Your Password");
        signupconfirm.setText("Confirm Your Password");
    }

    protected void onStop(){
        super.onStop();
        finish();
    }
}
