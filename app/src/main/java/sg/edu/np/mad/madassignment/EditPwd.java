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

public class EditPwd extends AppCompatActivity {

    TextView email;
    EditText password, cfmpassword;
    Button cfm, cancel;
    DBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepwd);

        //get user's email
        final UserData userData = LoginPage.userdata;

        email = findViewById(R.id.editprofileemail);
        password = findViewById(R.id.etpassword);
        cfmpassword = findViewById(R.id.etcfmpassword);

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
    }
}
