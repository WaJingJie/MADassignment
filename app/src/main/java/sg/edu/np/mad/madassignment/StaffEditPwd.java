package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StaffEditPwd extends AppCompatActivity{
    TextView e;
    EditText pwd, cfmpwd;
    Button cfm, cancel;
    DBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffchangepwd);
        final StaffData staffData = StaffLoginPage.staffdata;
        e = findViewById(R.id.editstaffemail);
        pwd = findViewById(R.id.staffetpassword);
        cfmpwd = findViewById(R.id.staffetcfmpassword);
        cfm = findViewById(R.id.staffpwdconfirm);
        cancel = findViewById(R.id.staffcancelpwdbtn);

        //initialize database
        dbHandler = new DBHandler(this,null,null,1);
        //set user's email into textview email
        e.setText(staffData.getMyStaffEmail());

        //updates phone number when user clicks on the button
        cfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pwd.getText().toString().isEmpty()|| pwd.getText().toString() == cfmpwd.getText().toString()){
                    Toast.makeText(getApplicationContext(), "Please enter the field correctly! Hint password must be the same.", Toast.LENGTH_LONG).show();
                }
                else{
                    boolean updated = dbHandler.updatePwd(staffData.getMyStaffEmail(), pwd.getText().toString());
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
                Intent cancel = new Intent(StaffEditPwd.this, StaffProfilePage.class);
                startActivity(cancel);
            }
        });
    }
}
