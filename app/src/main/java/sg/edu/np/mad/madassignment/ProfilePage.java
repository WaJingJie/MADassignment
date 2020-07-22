package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    TextView name, email;
    private static final String FILENAME = "ProfilePage.java";
    private static final String TAG = "NP Library";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepage);
        logoutbutton = findViewById(R.id.studentlogoutbutton);
        homebutton = findViewById(R.id.studenthomebutton);
        profilebutton = findViewById(R.id.studentprofilebutton);
        viewbutton = findViewById(R.id.viewborrowicon);
        overduebutton = findViewById(R.id.overdueicon);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        UserData userData = LoginPage.userdata;
        //This sets the profile name and the profile email using data from the public static Userdata object
        name.setText(userData.getMyName());
        email.setText(userData.getMyEmail());
        //This method is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(ProfilePage.this, MainActivity.class);
                startActivity(welcomepage);
                Log.v(TAG, FILENAME + ": Logging out");
            }
        });

        //this is to redirect the user to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(ProfilePage.this, HomePage.class);
                startActivity(homepage);
                Log.v(TAG, FILENAME + ": Redirecting to Home Page");
            }
        });
    }
}
