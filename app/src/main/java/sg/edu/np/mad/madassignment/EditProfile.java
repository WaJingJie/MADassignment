package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class EditProfile extends AppCompatActivity {

    TextView email;
    EditText phoneno, name;
    Button cfm, cancel;
    DBHandler dbHandler;
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    private DatabaseReference ref;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    public EditProfile(){}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        //get user's email
        final UserData userData = LoginPage.userdata;

        email = findViewById(R.id.editprofileemail);
        phoneno = findViewById(R.id.editphoneno);
        name = findViewById(R.id.editname);
        cfm = findViewById(R.id.editprofileconfirm);
        cancel = findViewById(R.id.btncanceledit);

        logoutbutton = findViewById(R.id.studentlogoutbutton);
        homebutton = findViewById(R.id.studenthomebutton);
        profilebutton = findViewById(R.id.studentprofilebutton);
        viewbutton = findViewById(R.id.viewborrowicon);
        overduebutton = findViewById(R.id.overdueicon);

        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //initialize database
        dbHandler = new DBHandler(this,null,null,1);

        //set user's email into textview email
        email.setText(userData.getMyEmail());

        ref.child("users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //updates phone number when user clicks on the button
                String useremail = snapshot.child("email").getValue().toString();
                email.setText(useremail);
                cfm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String n = name.getText().toString();
                        final String pn = phoneno.getText().toString();
                        //this validates whether the details are empty
                        if(pn.isEmpty() && n.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Please enter details.", Toast.LENGTH_LONG).show();
                        }
                        //this updates only the name field
                        else if(pn.isEmpty()){
                            ref.child("users").child(firebaseUser.getUid()).child("name").setValue(n);
                            Toast.makeText(getApplicationContext(), "Name successfully updated!", Toast.LENGTH_LONG).show();
                            Intent confirm = new Intent(EditProfile.this, ProfilePage.class);
                            startActivity(confirm);
                        }
                        //this updates only the phone no field
                        else if(name.getText().toString().isEmpty()){
                            ref.child("users").child(firebaseUser.getUid()).child("phoneno").setValue(pn);
                            Toast.makeText(getApplicationContext(), "Phone number successfully updated!", Toast.LENGTH_LONG).show();
                            Intent confirm = new Intent(EditProfile.this, ProfilePage.class);
                            startActivity(confirm);
                        }
                        //this updates both the name and phone number of the user
                        else{
                            ref.child("users").child(firebaseUser.getUid()).child("name").setValue(n);
                            ref.child("users").child(firebaseUser.getUid()).child("phoneno").setValue(pn);
                            Toast.makeText(getApplicationContext(), "Profile successfully updated!", Toast.LENGTH_LONG).show();
                            Intent confirm = new Intent(EditProfile.this, ProfilePage.class);
                            startActivity(confirm);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //intent back to profile if user cancels
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(EditProfile.this, ProfilePage.class);
                startActivity(cancel);
            }
        });

        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent welcomepage = new Intent(EditProfile.this, MainActivity.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the user to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(EditProfile.this, ProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the user to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(EditProfile.this, StudentHomePage.class);
            }
        });

        //this is to redirect the user to the view borrowed books page
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(EditProfile.this, ViewBorrow.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the user to the overdue page
        overduebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent overduepage = new Intent(EditProfile.this, OverdueLoan.class);
                //startActivity(overduepage);
            }
        });
    }

    public EditProfile(String id, String email, String name, String password, String phoneno){

    }


}


