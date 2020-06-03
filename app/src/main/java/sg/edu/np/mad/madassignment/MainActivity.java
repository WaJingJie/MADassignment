package sg.edu.np.mad.madassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button loginbtn, signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this is to link the java class to its layout
        setContentView(R.layout.activity_main);
        //this is to link the buttons to their layout components
        loginbtn = findViewById(R.id.welcomelogin);
        signupbtn = findViewById(R.id.welcomesignup);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginpage = new Intent(MainActivity.this, LoginPage.class);
                startActivity(loginpage);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signuppage = new Intent(MainActivity.this, SignUpPage.class);
                startActivity(signuppage);
            }
        });


    }
}
