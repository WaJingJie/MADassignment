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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditStaffProfile extends AppCompatActivity{
    TextView email;
    EditText name, phoneno;
    Button confirm, cancel;
    DatabaseReference ref;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
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

        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



        //validation to disallow empty fields
        ref.child("staff").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //set staff's email into textview email
                String staffemail = snapshot.child("email").getValue().toString();
                email.setText(staffemail);
                //when the confirm button is pressed
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String n = name.getText().toString();
                        final String pn = phoneno.getText().toString();
                        if (n.isEmpty() && pn.isEmpty()) {
                            Toast.makeText(EditStaffProfile.this, "Please enter details", Toast.LENGTH_LONG).show();

                        }
                        //when only name is filled
                        else if (pn.isEmpty()) {
                            //this updates the staff's name
                            ref.child("staff").child(firebaseUser.getUid()).child("name").setValue(n);
                            //this informs the staff that their name has been updated
                            Toast.makeText(getApplicationContext(), "Name successfully updated!", Toast.LENGTH_LONG).show();
                            Intent confirm = new Intent(EditStaffProfile.this, StaffProfilePage.class);
                            startActivity(confirm);
                        }
                        //when only phone number is filled
                        else if (n.isEmpty()) {
                            //this updates the staff's phone number
                            ref.child("staff").child(firebaseUser.getUid()).child("phoneno").setValue(pn);
                            //this informs the staff that their phone no has been updated
                            Toast.makeText(getApplicationContext(), "Phone number successfully updated!", Toast.LENGTH_LONG).show();
                            Intent confirm = new Intent(EditStaffProfile.this, StaffProfilePage.class);
                            startActivity(confirm);
                        }
                        //when both name and phone number fields are filled
                        else {
                            //this updates the staff's name and phone number
                            ref.child("staff").child(firebaseUser.getUid()).child("name").setValue(n);
                            ref.child("staff").child(firebaseUser.getUid()).child("phoneno").setValue(pn);
                            //this informs the staff that their phone no and name has been updated
                            Toast.makeText(getApplicationContext(), "Profile successfully updated!", Toast.LENGTH_LONG).show();
                            Intent confirm = new Intent(EditStaffProfile.this, StaffProfilePage.class);
                            startActivity(confirm);
                        }

                    }



                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
