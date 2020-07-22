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
    Button editbutton;
    TextView name, email, phoneno;
    private static final String FILENAME = "StaffProfilePage.java";
    private static final String TAG = "NP Library";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logoutbutton = findViewById(R.id.studentlogoutbutton);
        homebutton = findViewById(R.id.studenthomebutton);
        profilebutton = findViewById(R.id.studentprofilebutton);
        addbutton = findViewById(R.id.addbookicon);
        deletebutton = findViewById(R.id.deletebookicon);
        StaffData staffData = StaffLoginPage.staffdata;
        //This sets the profile name and the profile email using data from the public static Staffdata object
        name.setText(staffData.getMyStaffName());
        email.setText(staffData.getMyStaffEmail());
        phoneno.setText(staffData.getMyStaffPhoneNo());

        //this is to allow the user to log out
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
