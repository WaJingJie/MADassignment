package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.madassignment.utils.DateUtil;


public class OverDueLoan extends AppCompatActivity{
    TextView canBorrow,totalCoast,bookname,borrowdate,duedate,isbn,returndate,overdueduration,overduefee;
    Spinner spinner;
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    DBHandler dbHandler;
    List<BorrowData> borrowDataList = new ArrayList<>();
    List<BorrowData> borrowList = new ArrayList<>();
    RecyclerView homepageview;
    String currentDate;
    Button payloan;
    DatabaseReference ref;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Overdueloandadapter overdueloandadapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overduebook);

//        bookname = findViewById(R.id.overduebookname);
//        borrowdate = findViewById(R.id.borrowdate);
//        duedate = findViewById(R.id.overduedate);
//        isbn = findViewById(R.id.overdueisbn);
//        returndate = findViewById(R.id.returndate);
//        overdueduration = findViewById(R.id.duration);
//        overduefee = findViewById(R.id.fee);
        currentDate = DateUtil.getCurrentTimeStamp();
        totalCoast = findViewById(R.id.canborrow2);
        canBorrow = findViewById(R.id.canborrow);
        logoutbutton = findViewById(R.id.studentlogoutbutton);
        homebutton = findViewById(R.id.studenthomebutton);
        profilebutton = findViewById(R.id.studentprofilebutton);
        viewbutton = findViewById(R.id.viewborrowicon);
        overduebutton = findViewById(R.id.overdueicon);
        homepageview = findViewById(R.id.homepageview);
        homepageview.setLayoutManager(new LinearLayoutManager(this));

        dbHandler = new DBHandler(this,null,null,1);

        UserData userData = LoginPage.userdata;
        Log.e("email :",""+userData.getMyEmail());
        borrowDataList = dbHandler.getborrowbyEmail(userData.getMyEmail());
        Log.e("borrow list  :",""+borrowDataList.toString());

        overdueloandadapter = new Overdueloandadapter(OverDueLoan.this,borrowList);
        homepageview.setAdapter(overdueloandadapter);


        overdueloandadapter.notifyDataSetChanged();

        if(!borrowDataList.isEmpty()){
            for(int i=0; i< borrowDataList.size(); i++){
                if(DateUtil.checkTimeElapseOrNot(borrowDataList.get(i).getDueDate(),currentDate)){
                    borrowList.add(borrowDataList.get(i));
                }
            }
            if(!borrowList.isEmpty()){
                Log.e("borrow list size  :",""+borrowList.size());
                canBorrow.setText("You have "+ borrowList.size()+" overdue loans.");
                int totalDayElapsed = 0;
                for(int i=0; i< borrowList.size(); i++){
                    String diff = DateUtil.getDiffBetTwoDate(borrowList.get(i).getDueDate(),currentDate);
                    if(diff != null){
                        totalDayElapsed = totalDayElapsed + Integer.parseInt(diff);
                    }
                }
                Log.e("total day Elapsed  :",""+totalDayElapsed);
                if(totalDayElapsed != 0){
                    int totalPrice = totalDayElapsed * 50;
                    totalCoast.setText("Total Overdue Fee: $ "+ totalPrice +".00");
                }

            }


        }




        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(OverDueLoan.this, MainActivity.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the user to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(OverDueLoan.this, ProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the user to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(OverDueLoan.this, StudentHomePage.class);
                startActivity(homepage);
            }
        });

        //this is to redirect the user to the view borrowed books page
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(OverDueLoan.this, ViewBorrow.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the user to the overdue page
        overduebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overduepage = new Intent(OverDueLoan.this, OverDueLoan.class);
                startActivity(overduepage);
            }
        });


    }

    private void writeNewLoan(String id, String email, String isbn, String name, String duedate, String days, String fee) {
        ref.child("overdueloans").child(id).child("email").setValue(email);
        ref.child("overdueloans").child(id).child("isbn").setValue(isbn);
        ref.child("overdueloans").child(id).child("bookname").setValue(name);
        ref.child("overdueloans").child(id).child("duedate").setValue(duedate);
        ref.child("overdueloans").child(id).child("days").setValue(days);
        ref.child("overdueloans").child(id).child("fees").setValue(fee);
    }

}
