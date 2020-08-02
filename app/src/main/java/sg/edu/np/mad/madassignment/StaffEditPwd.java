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

public class StaffEditPwd extends AppCompatActivity{
    TextView e;
    EditText pwd, cfmpwd;
    Button cfm, cancel;
    DBHandler dbHandler;
    ImageButton logoutbutton, homebutton, profilebutton, addbutton, deletebutton;
    DatabaseReference ref;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffchangepwd);
        final StaffData staffData = StaffLoginPage.staffdata;
        e = findViewById(R.id.staffprofileemail);
        pwd = findViewById(R.id.staffetpassword);
        cfmpwd = findViewById(R.id.staffetcfmpassword);
        cfm = findViewById(R.id.staffpwdconfirm);
        cancel = findViewById(R.id.staffcancelpwdbtn);
        logoutbutton = findViewById(R.id.stafflogoutbutton);
        homebutton = findViewById(R.id.staffhomebutton);
        profilebutton = findViewById(R.id.staffprofilebutton);
        addbutton = findViewById(R.id.addbookicon);
        deletebutton = findViewById(R.id.deletebookicon);

        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        ref.child("staff").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //set staff's email into textview email
                String staffemail = snapshot.child("email").getValue().toString();
                e.setText(staffemail);
                //updates password when staff clicks on the button
                cfm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = pwd.getText().toString();
                        String confirmpwd = cfmpwd.getText().toString();
                        //this ensures the two password field matches
                        if(password.isEmpty()|| password == confirmpwd){
                            Toast.makeText(getApplicationContext(), "Please enter the field correctly! Hint password must be the same.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            firebaseUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Password successfully updated!", Toast.LENGTH_LONG).show();
                                        finish();
                                        Intent confirm = new Intent(StaffEditPwd.this, StaffProfilePage.class);
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
                Intent cancel = new Intent(StaffEditPwd.this, StaffProfilePage.class);
                startActivity(cancel);
            }
        });

        //this is to allow the staff to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(StaffEditPwd.this, StaffLoginPage.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the staff to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(StaffEditPwd.this, StaffProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the staff to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(StaffEditPwd.this, StaffHomePage.class);
                startActivity(homepage);
            }
        });

        //this is to redirect the staff to the add book page
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(StaffEditPwd.this, AddBook.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the staff to the delete book page
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deletepage = new Intent(StaffEditPwd.this, DeleteBook.class);
                startActivity(deletepage);
            }
        });
    }
}
