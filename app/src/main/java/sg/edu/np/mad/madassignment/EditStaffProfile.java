package sg.edu.np.mad.madassignment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

class EditStaffProfile extends AppCompatActivity{
    TextView email, name, phoneno, password;
    Button confirm, cancel;
    StaffData staffData = StaffLoginPage.staffdata;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editstaffprofile);
        email = findViewById(R.id.editstaffemail);
        name = findViewById(R.id.editstaffname);
        phoneno = findViewById(R.id.editstaffphone);
        password = findViewById(R.id.editstaffpassword);
        confirm = findViewById(R.id.editstaffconfirm);
        cancel = findViewById(R.id.staffcancelbtn);
        //validation to disallow empty fields
        if(email.getText().toString().isEmpty() && name.getText().toString().isEmpty()
                && phoneno.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
            Toast.makeText(EditStaffProfile.this, "Please enter details", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(EditStaffProfile.this, "Staff Profile Updated", Toast.LENGTH_LONG).show();
            
        }
    }
}
