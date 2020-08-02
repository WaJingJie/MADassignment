package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    TextView name, email, phone;
    Button changepwd, editprofile;
    private static final String FILENAME = "ProfilePage.java";
    private static final String TAG = "NP Library";
    DBHandler dbHandler;
    private FirebaseAuth firebaseAuth;
    DatabaseReference ref;
    private FirebaseUser firebaseUser;
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

        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        //This gets the current user
        firebaseUser = firebaseAuth.getCurrentUser();
        ref.child("users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n = snapshot.child("username").getValue().toString();
                String e = snapshot.child("email").getValue().toString();
                //this checks whether the user has added their phone number to their account
                if(snapshot.child("phoneno").getValue().equals("")){
                    phone.setText("Add Phone Number");
                }
                else{
                    String pn = snapshot.child("phoneno").getValue().toString();
                    phone.setText(pn);
                }
                name.setText(n);
                email.setText(e);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                Intent viewpage = new Intent(ProfilePage.this, ViewBorrow.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the user to the overdue page
        overduebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overduepage = new Intent(ProfilePage.this, OverDueLoan.class);
                startActivity(overduepage);
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
