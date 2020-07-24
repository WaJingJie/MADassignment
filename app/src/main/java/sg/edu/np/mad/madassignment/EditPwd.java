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

public class EditPwd extends AppCompatActivity {

    TextView email;
    EditText password, cfmpassword;
    Button cfm, cancel;
    DBHandler dbHandler;
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepwd);

        //get user's email
        final UserData userData = LoginPage.userdata;

        email = findViewById(R.id.changepwdemail);
        password = findViewById(R.id.etpassword);
        cfmpassword = findViewById(R.id.etcfmpassword);
        cfm = findViewById(R.id.editpwdconfirm);
        cancel = findViewById(R.id.btnchangepwd);

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
                if(password.getText().toString().isEmpty()|| password.getText().toString() == cfmpassword.getText().toString()){
                    Toast.makeText(getApplicationContext(), "Please enter the field correctly! Hint password must be the same.", Toast.LENGTH_LONG).show();
                }
                else{
                    boolean updated = dbHandler.updatePwd(userData.getMyEmail(), password.getText().toString());
                    if(updated == true){
                        Toast.makeText(getApplicationContext(), "Password successfully updated!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Password unsuccessfully updated", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //intent back to profile if user cancels
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(EditPwd.this, ProfilePage.class);
                startActivity(cancel);
            }
        });

        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(EditPwd.this, MainActivity.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the user to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(EditPwd.this, ProfilePage.class);
                startActivity(profilepage);
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(EditPwd.this, StudentHomePage.class);
            }
        });

        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(EditPwd.this, HomePage.class);
                startActivity(viewpage);
            }
        });

        overduebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent overduepage = new Intent(StudentHomePage.this, OverdueLoan.class);
                //startActivity(overduepage);
            }
        });
    }
}
