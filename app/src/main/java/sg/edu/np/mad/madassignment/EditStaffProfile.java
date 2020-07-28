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

public class EditStaffProfile extends AppCompatActivity{
    TextView email;
    EditText name, phoneno;
    Button confirm, cancel;
    DBHandler dbHandler;
    StaffData staffData = StaffLoginPage.staffdata;
    ImageButton logoutbutton, homebutton, profilebutton, addbutton, deletebutton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editstaffprofile);
        email = findViewById(R.id.editstaffemail);
        name = findViewById(R.id.editstaffname);
        phoneno = findViewById(R.id.editstaffphone);
        confirm = findViewById(R.id.editstaffconfirm);
        cancel = findViewById(R.id.staffcancelbtn);

        logoutbutton = findViewById(R.id.stafflogoutbutton);
        homebutton = findViewById(R.id.staffhomebutton);
        profilebutton = findViewById(R.id.staffprofilebutton);
        addbutton = findViewById(R.id.addbookicon);
        deletebutton = findViewById(R.id.deletebookicon);

        //initialize database
        dbHandler = new DBHandler(this,null,null,1);

        //set staff's email into textview email
        email.setText(staffData.getMyStaffEmail());

        //when the confirm button is pressed
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String pn = phoneno.getText().toString();
                //validation to disallow empty fields
                if(n.isEmpty() && pn.isEmpty()) {
                    Toast.makeText(EditStaffProfile.this, "Please enter details", Toast.LENGTH_LONG).show();
                }
                //when only name is filled
                else if(pn.isEmpty()){
                    boolean updated = dbHandler.staffUpdateName(staffData.getMyStaffEmail(), n);
                    if(updated){
                        Toast.makeText(getApplicationContext(), "Name successfully updated!", Toast.LENGTH_LONG).show();
                        Intent confirm = new Intent(EditStaffProfile.this, StaffProfilePage.class);
                        startActivity(confirm);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Name unsuccessfully updated", Toast.LENGTH_LONG).show();
                    }
                }
                //when only phone number is filled
                else if(n.isEmpty()){
                    boolean updated = dbHandler.staffUpdatePhonenum(staffData.getMyStaffEmail(), pn);
                    if(updated){
                        Toast.makeText(getApplicationContext(), "Phone number successfully updated!", Toast.LENGTH_LONG).show();
                        Intent confirm = new Intent(EditStaffProfile.this, StaffProfilePage.class);
                        startActivity(confirm);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Phone number unsuccessfully updated", Toast.LENGTH_LONG).show();
                    }
                }
                //when both name and phone number fields are filled
                else{
                    boolean pnupdate = dbHandler.staffUpdatePhonenum(staffData.getMyStaffEmail(), pn);
                    boolean nupdate = dbHandler.staffUpdateName(staffData.getMyStaffEmail(), n);
                    if(pnupdate && nupdate){
                        Toast.makeText(getApplicationContext(), "Profile successfully updated!", Toast.LENGTH_LONG).show();
                        Intent confirm = new Intent(EditStaffProfile.this, StaffProfilePage.class);
                        startActivity(confirm);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Profile unsuccessfully updated", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //when the cancel button is pressed
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(EditStaffProfile.this, StaffProfilePage.class);
                startActivity(cancel);
            }
        });

        //this is to allow the staff to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(EditStaffProfile.this, StaffLoginPage.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the staff to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(EditStaffProfile.this, StaffProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the staff to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(EditStaffProfile.this, StaffHomePage.class);
                startActivity(homepage);
            }
        });

        //this is to redirect the staff to the add book page
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(EditStaffProfile.this, AddBook.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the staff to the delete book page
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overduepage = new Intent(EditStaffProfile.this, DeleteBook.class);
                startActivity(overduepage);
            }
        });
    }

}
