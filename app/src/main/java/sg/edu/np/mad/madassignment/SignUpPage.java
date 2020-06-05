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
    private ArrayList<String> emailList, nameList, passwordList;
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

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = signupemail.getText().toString();
                String name = signupname.getText().toString();
                String password = signuppassword.getText().toString();
                String confirm = signupconfirm.getText().toString();
                int nofbooksborrowed = 0;
                int canborrow = 9;

                if(email.isEmpty() || password.isEmpty() || name.isEmpty() || confirm.isEmpty()){
                    Toast.makeText(SignUpPage.this, "Please complete all the details.", Toast.LENGTH_SHORT).show();

                }
                else{
                    dupvalidate(email, name, password, nofbooksborrowed, canborrow);
                }



            }
        });


        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, FILENAME + ": Return to welcome page");
                Intent welcomepage = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(welcomepage);
                reset();
                finish();
            }
        });
    }


    public void dupvalidate(String e, String n, String p, int b, int c){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        UserData data = dbHandler.findUser(e);

        if(data != null){
            Toast.makeText(SignUpPage.this, "User already exists! Please enter another email.",
                    Toast.LENGTH_LONG).show();
        }
        else{

            UserData userdata = new UserData(e, n, p, b, c);
            dbHandler.addUser(userdata);
            Log.v(TAG, FILENAME + ": New user successfully created !");
            Toast.makeText(SignUpPage.this, "Account created successfully!",
                    Toast.LENGTH_SHORT).show();
            LoginPage.userdata = data;
            //redirect to home page
            Log.v(TAG, FILENAME + ": Go to home page");
            Intent homepage = new Intent(SignUpPage.this, HomePage.class);
            startActivity(homepage);
            reset();
        }
    }

    public void reset(){
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
