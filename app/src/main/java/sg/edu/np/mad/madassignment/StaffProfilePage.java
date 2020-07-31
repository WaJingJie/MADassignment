package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
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

public class StaffProfilePage extends AppCompatActivity{
    ImageButton logoutbutton, homebutton, profilebutton, addbutton, deletebutton;
    Button editbutton, editpwd;
    TextView name, email, phoneno;
    private static final String FILENAME = "StaffProfilePage.java";
    private static final String TAG = "NP Library";
    DBHandler dbHandler;
    DatabaseReference ref;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffprofilepage);
        logoutbutton = findViewById(R.id.stafflogoutbutton);
        homebutton = findViewById(R.id.staffhomebutton);
        profilebutton = findViewById(R.id.staffprofilebutton);
        addbutton = findViewById(R.id.addbookicon);
        deletebutton = findViewById(R.id.deletebookicon);

        //ref = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());
        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        name = findViewById(R.id.staffname);
        email = findViewById(R.id.staffemail);
        phoneno = findViewById(R.id.phoneno);

        editbutton = findViewById(R.id.editstaffbtn);
        editpwd = findViewById(R.id.staffchangepwd);
        StaffData staffData = StaffLoginPage.staffdata;

        dbHandler = new DBHandler(this,null,null,1);
        firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        ref.child("staff").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String staffname = snapshot.child("name").getValue().toString();
                String staffemail = snapshot.child("email").getValue().toString();
                if(snapshot.child("phoneno").getValue().equals("")){
                    phoneno.setText("Add Phone Number");
                }
                else{
                    String staffpn = snapshot.child("phoneno").getValue().toString();
                    phoneno.setText(staffpn);
                }
                name.setText(staffname);
                email.setText(staffemail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //This sets the profile name and the profile email using data from the public static Staffdata object
        //name.setText(dbHandler.getstaffname(staffData.getMyStaffEmail()));
        //email.setText(staffData.getMyStaffEmail());
        //phoneno.setText(dbHandler.getphonebystaffemail(staffData.getMyStaffEmail()));

        //this is to redirect the staff to the edit profile page
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editpage = new Intent(StaffProfilePage.this, EditStaffProfile.class);
                startActivity(editpage);
            }
        });

        //this is to redirect the staff to the edit password page
        editpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editpwdpage = new Intent(StaffProfilePage.this, StaffEditPwd.class);
                startActivity(editpwdpage);
            }
        });

        //this is to allow the staff to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(StaffProfilePage.this, StaffLoginPage.class);
               startActivity(welcomepage);
           }
        });

        //this is to redirect the staff to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View v) {
                Intent profilepage = new Intent(StaffProfilePage.this, StaffHomePage.class);
              startActivity(profilepage);
           }
        });

        //this is to redirect the staff to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(StaffProfilePage.this, StaffProfilePage.class);
                startActivity(profilepage);
            }
        });

       //this is to redirect the staff to the add book page
       addbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent viewpage = new Intent(StaffProfilePage.this, AddBook.class);
               startActivity(viewpage);
           }
       });

        //this is to redirect the staff to the delete book page
       deletebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              Intent overduepage = new Intent(StaffProfilePage.this, DeleteBook.class);
              startActivity(overduepage);
         }
       });
    }
}
