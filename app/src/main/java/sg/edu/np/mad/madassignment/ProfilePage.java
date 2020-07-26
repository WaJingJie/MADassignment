package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    TextView name, email, phone;
    Button changepwd, editprofile;
    private static final String FILENAME = "ProfilePage.java";
    private static final String TAG = "NP Library";
    DBHandler dbHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepage);
        logoutbutton = findViewById(R.id.studentlogoutbutton);
        homebutton = findViewById(R.id.studenthomebutton);
        profilebutton = findViewById(R.id.studentprofilebutton);
        viewbutton = findViewById(R.id.viewborrowicon);
        overduebutton = findViewById(R.id.overdueicon);
        changepwd = findViewById(R.id.btnchangepwd);
        editprofile = findViewById(R.id.btnaddphoneno);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phoneno);
        UserData userData = LoginPage.userdata;

        dbHandler = new DBHandler(this,null,null,1);
        //This sets the profile name and the profile email using data from the public static Userdata object
        name.setText(dbHandler.getnamebyemail(userData.getMyEmail()));
        email.setText(userData.getMyEmail());
        phone.setText(dbHandler.getphonebyemail(userData.getMyEmail()));
        //This method is to allow the user to log out

        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(ProfilePage.this, MainActivity.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the user to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(ProfilePage.this, ProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the user to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(ProfilePage.this, StudentHomePage.class);
                startActivity(homepage);
            }
        });

        //this is to redirect the user to the view borrowed books page
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(ProfilePage.this, HomePage.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the user to the overdue page
        overduebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent overduepage = new Intent(ProfilePage.this, OverdueLoan.class);
                startActivity(overduepage);*/
            }
        });

        //intent to edit profile page
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editprofile = new Intent(ProfilePage.this, EditProfile.class);
                startActivity(editprofile);
                Log.v(TAG, FILENAME + ": Redirecting to Edit Profile Page");
            }
        });

        //intent to edit password page
        changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editpwd = new Intent(ProfilePage.this, EditPwd.class);
                startActivity(editpwd);
                Log.v(TAG, FILENAME + ": Redirecting to Edit Profile Page");
            }
        });
    }
}
