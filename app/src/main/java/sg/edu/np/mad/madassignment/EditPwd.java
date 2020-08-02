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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPwd extends AppCompatActivity {

    TextView email;
    EditText password, cfmpassword;
    Button cfm, cancel;
    DBHandler dbHandler;
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    DatabaseReference ref;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepwd);

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

        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //initialize database
        dbHandler = new DBHandler(this,null,null,1);

        ref.child("users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //set staff's email into textview email
                String useremail = snapshot.child("email").getValue().toString();
                email.setText(useremail);

                cfm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pwd = password.getText().toString();
                        String confirmpwd = cfmpassword.getText().toString();
                        //this validates that the two password fields are the same
                        if(pwd.isEmpty() || !pwd.equals(confirmpwd)){
                            Toast.makeText(getApplicationContext(), "Please enter the field correctly! Hint password must be the same.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            //this updates the user's password
                            firebaseUser.updatePassword(pwd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //this informs the user that their password has been updated
                                        Toast.makeText(getApplicationContext(), "Password successfully updated!", Toast.LENGTH_LONG).show();
                                        finish();
                                        Intent confirm = new Intent(EditPwd.this, ProfilePage.class);
                                        startActivity(confirm);
                                    }
                                }
                            });

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

        //this is to redirect the user to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(EditPwd.this, StudentHomePage.class);
                startActivity(homepage);
            }
        });

        //this is to redirect the user to the view borrowed books page
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(EditPwd.this, ViewBorrow.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the user to the overdue page
        overduebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overduepage = new Intent(EditPwd.this, OverDueLoan.class);
                startActivity(overduepage);
            }
        });
    }
}
