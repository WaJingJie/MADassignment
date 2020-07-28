package sg.edu.np.mad.madassignment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class OverDueLoan extends AppCompatActivity{
    TextView bookname,borrowdate,duedate,isbn,returndate,overdueduration,overduefee;
    Spinner spinner;
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    DBHandler dbHandler;
    Button payloan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overdueloancv);

        bookname = findViewById(R.id.overduebookname);
        borrowdate = findViewById(R.id.borrowdate);
        duedate = findViewById(R.id.overduedate);
        isbn = findViewById(R.id.overdueisbn);
        returndate = findViewById(R.id.returndate);
        overdueduration = findViewById(R.id.duration);
        overduefee = findViewById(R.id.fee);

        logoutbutton = findViewById(R.id.studentlogoutbutton);
        homebutton = findViewById(R.id.studenthomebutton);
        profilebutton = findViewById(R.id.studentprofilebutton);
        viewbutton = findViewById(R.id.viewborrowicon);
        overduebutton = findViewById(R.id.overdueicon);

    }
}
