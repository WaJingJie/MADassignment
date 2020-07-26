package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StaffProfilePage extends AppCompatActivity{
    ImageButton logoutbutton, homebutton, profilebutton, addbutton, deletebutton;
    Button editbutton, editpwd;
    TextView name, email, phoneno;
    private static final String FILENAME = "StaffProfilePage.java";
    private static final String TAG = "NP Library";
    DBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffprofilepage);
        logoutbutton = findViewById(R.id.stafflogoutbutton);
        homebutton = findViewById(R.id.staffhomebutton);
        profilebutton = findViewById(R.id.staffprofilebutton);
        addbutton = findViewById(R.id.addbookicon);
        deletebutton = findViewById(R.id.deletebookicon);

        name = findViewById(R.id.staffname);
        email = findViewById(R.id.staffemail);
        phoneno = findViewById(R.id.phoneno);

        editbutton = findViewById(R.id.editstaffbtn);
        editpwd = findViewById(R.id.staffchangepwd);
        StaffData staffData = StaffLoginPage.staffdata;

        dbHandler = new DBHandler(this,null,null,1);

        //This sets the profile name and the profile email using data from the public static Staffdata object
        name.setText(staffData.getMyStaffName());
        email.setText(staffData.getMyStaffEmail());
        phoneno.setText(dbHandler.getphonebystaffemail(staffData.getMyStaffEmail()));

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editpage = new Intent(StaffProfilePage.this, EditStaffProfile.class);
                startActivity(editpage);
            }
        });

        editpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editpwdpage = new Intent(StaffProfilePage.this, StaffEditPwd.class);
                startActivity(editpwdpage);
            }
        });

//        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(StaffProfilePage.this, StaffLoginPage.class);
               startActivity(welcomepage);
           }
        });

        //this is to redirect the user to the profile page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View v) {
                Intent profilepage = new Intent(StaffProfilePage.this, StaffHomePage.class);
              startActivity(profilepage);
           }
       });

       addbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent viewpage = new Intent(StaffProfilePage.this, AddBook.class);
               startActivity(viewpage);
           }
       });

       deletebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              Intent overduepage = new Intent(StaffProfilePage.this, DeleteBook.class);
              startActivity(overduepage);
         }
       });
    }
}
