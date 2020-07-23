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

public class EditProfile extends AppCompatActivity {

    TextView email;
    EditText phoneno;
    Button cfm, cancel;
    DBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        //get user's email
        final UserData userData = LoginPage.userdata;

        email = findViewById(R.id.editprofileemail);
        phoneno = findViewById(R.id.editphoneno);

        cfm = findViewById(R.id.editprofileconfirm);
        cancel = findViewById(R.id.btnchangepwd);

        //initialize database
        dbHandler = new DBHandler(this,null,null,1);

        //set user's email into textview email
        email.setText(userData.getMyEmail());

        //updates phone number when user clicks on the button
        cfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneno.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter phone number", Toast.LENGTH_LONG).show();
                }
                else{
                    boolean updated = dbHandler.updatePhonenum(userData.getMyEmail(), phoneno.getText().toString());
                    if(updated == true){
                        Toast.makeText(getApplicationContext(), "Phone number successfully updated!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Phone number unsuccessfully updated", Toast.LENGTH_LONG).show();
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
    }
}


