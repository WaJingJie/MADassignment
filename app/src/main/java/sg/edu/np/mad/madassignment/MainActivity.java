package sg.edu.np.mad.madassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "NP Library";
    Button loginbtn, signupbtn;
    DBHandler dbHandler;
    String[] bookisbn = new String[]{"978-1-4028-9463-6","978-1-4028-9461-6","978-1-4028-9462-6"};
    String[] bookname = new String[]{"Introduction to programming","Introduction to android","Introduction to IOS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = findViewById(R.id.welcomelogin);
        signupbtn = findViewById(R.id.welcomesignup);
        dbHandler = new DBHandler(this,null,null,1);

        //create initial catalog
        for (int i=0; i< bookname.length;i++){
            dbHandler.addbook(bookisbn[i],bookname[i],"Available");
        }

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


    }

}
