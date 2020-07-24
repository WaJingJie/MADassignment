package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

class EditStaffProfile extends AppCompatActivity{
    TextView email, name, phoneno, password;
    Button confirm, cancel;
    DBHandler dbHandler;
    StaffData staffData = StaffLoginPage.staffdata;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editstaffprofile);
        email = findViewById(R.id.editstaffemail);
        name = findViewById(R.id.editstaffname);
        phoneno = findViewById(R.id.editstaffphone);
        confirm = findViewById(R.id.editstaffconfirm);
        cancel = findViewById(R.id.staffcancelbtn);

        //initialize database
        dbHandler = new DBHandler(this,null,null,1);

        //set user's email into textview email
        email.setText(staffData.getMyStaffEmail());

        final String n = name.getText().toString();
        final String pn = phoneno.getText().toString();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation to disallow empty fields
                if(n.isEmpty() && pn.isEmpty()) {
                    Toast.makeText(EditStaffProfile.this, "Please enter details", Toast.LENGTH_LONG).show();
                }
                else if(pn.isEmpty()){
                    boolean updated = dbHandler.staffUpdateName(staffData.getMyStaffEmail(), n);
                    if(updated == true){
                        Toast.makeText(getApplicationContext(), "Name successfully updated!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Name unsuccessfully updated", Toast.LENGTH_LONG).show();
                    }
                }
                else if(n.isEmpty()){
                    boolean updated = dbHandler.staffUpdatePhonenum(staffData.getMyStaffEmail(), pn);
                    if(updated == true){
                        Toast.makeText(getApplicationContext(), "Phone number successfully updated!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Phone number unsuccessfully updated", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    boolean pnupdate = dbHandler.staffUpdatePhonenum(staffData.getMyStaffEmail(), pn);
                    boolean nupdate = dbHandler.staffUpdateName(staffData.getMyStaffEmail(), n);
                    if(pnupdate == true && nupdate == true){
                        Toast.makeText(getApplicationContext(), "Profile successfully updated!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Profile unsuccessfully updated", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(EditStaffProfile.this, StaffProfilePage.class);
                startActivity(cancel);
            }
        });
    }

}
