package sg.edu.np.mad.madassignment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class Select extends AppCompatActivity {
    private static final String FILENAME = "Select.java";
    private static final String TAG = "NP Library";
    Button studentbtn, staffbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectmain);
        studentbtn = findViewById(R.id.selectstudent);
        staffbtn = findViewById(R.id.selectstaff);

        //This method is to redirect the staff to the student welcome page
        Log.v(TAG, FILENAME + ": Redirecting to Student Welcome Page");
        studentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentpage = new Intent(Select.this, MainActivity.class);
                startActivity(studentpage);
            }
        });

        //This method is to redirect the staff to the staff welcome page
        Log.v(TAG, FILENAME + ": Redirecting to Staff Welcome Page");
        staffbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent staffpage = new Intent(Select.this, StaffLoginPage.class);
                startActivity(staffpage);
            }
        });


    }
}
