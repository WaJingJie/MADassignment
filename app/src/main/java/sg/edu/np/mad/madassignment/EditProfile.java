package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {

    TextView email;
    EditText phoneno, name;
    Button cfm, cancel;
    DBHandler dbHandler;
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;

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

        //initialize database
        dbHandler = new DBHandler(this,null,null,1);

        //set user's email into textview email
        email.setText(userData.getMyEmail());

        //updates phone number when user clicks on the button
        cfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this validates whether the details are empty
                if(phoneno.getText().toString().isEmpty() && name.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter details.", Toast.LENGTH_LONG).show();
                }
                //this updates only the name field
                else if(phoneno.getText().toString().isEmpty()){
                    boolean nupdated = dbHandler.updateName(userData.getMyEmail(), name.getText().toString());
                    if(nupdated == true){
                        Toast.makeText(getApplicationContext(), "Name successfully updated!", Toast.LENGTH_LONG).show();
                        Intent confirm = new Intent(EditProfile.this, ProfilePage.class);
                        startActivity(confirm);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Name unsuccessfully updated", Toast.LENGTH_LONG).show();
                    }
                }
                //this updates only the phone no field
                else if(name.getText().toString().isEmpty()){
                    boolean pnupdated = dbHandler.updatePhonenum(userData.getMyEmail(), phoneno.getText().toString());
                    if(pnupdated == true){
                        Toast.makeText(getApplicationContext(), "Phone number successfully updated!", Toast.LENGTH_LONG).show();
                        Intent confirm = new Intent(EditProfile.this, ProfilePage.class);
                        startActivity(confirm);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Phone number unsuccessfully updated", Toast.LENGTH_LONG).show();
                    }
                }
                //this updates both the name and phone number of the user
                else{
                    boolean nupdated = dbHandler.updateName(userData.getMyEmail(), name.getText().toString());
                    boolean pnupdated = dbHandler.updatePhonenum(userData.getMyEmail(), phoneno.getText().toString());
                    if(nupdated == true && pnupdated == true){
                        Toast.makeText(getApplicationContext(), "Profile successfully updated!", Toast.LENGTH_LONG).show();
                        Intent confirm = new Intent(EditProfile.this, ProfilePage.class);
                        startActivity(confirm);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Profile unsuccessfully updated", Toast.LENGTH_LONG).show();
                    }
                }
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
                Intent viewpage = new Intent(EditProfile.this, HomePage.class);
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
}


