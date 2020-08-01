package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.madassignment.utils.DateUtil;


public class OverDueLoan extends AppCompatActivity{
    TextView canBorrow, totalCost,bookname,borrowdate,duedate,isbn,returndate,overdueduration,overduefee;
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
    String useremail;
    Double bookfee;
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
        totalCost = findViewById(R.id.canborrow2);
        canBorrow = findViewById(R.id.canborrow);
        logoutbutton = findViewById(R.id.studentlogoutbutton);
        homebutton = findViewById(R.id.studenthomebutton);
        profilebutton = findViewById(R.id.studentprofilebutton);
        viewbutton = findViewById(R.id.viewborrowicon);
        overduebutton = findViewById(R.id.overdueicon);
        homepageview = findViewById(R.id.homepageview);
        homepageview.setLayoutManager(new LinearLayoutManager(this));

        dbHandler = new DBHandler(this,null,null,1);
        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //UserData userData = LoginPage.userdata;
        //Log.e("email :",""+userData.getMyEmail());
        //borrowDataList = dbHandler.getborrowbyEmail(userData.getMyEmail());
        //Log.e("borrow list  :",""+borrowDataList.toString());

        overdueloandadapter = new Overdueloandadapter(OverDueLoan.this, new ArrayList<OverdueLoanData>());
        homepageview.setAdapter(overdueloandadapter);

        ref.child("users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                useremail = (String)snapshot.child("email").getValue();
                //Log.d(TAG, useremail);
                ref.child("borrowedbooks").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot child : snapshot.getChildren()) {
                            String e = (String) child.child("email").getValue();
                            if(e.equals(useremail)){
                                final String isbn = (String) child.child("isbn").getValue();
                                final String bookname = (String) child.child("bookname").getValue();
                                String borrowdate = (String) child.child("borrowdate").getValue();
                                final String duedate = (String) child.child("duedate").getValue();
                                BorrowData borrowData = new BorrowData(useremail, isbn, bookname, borrowdate, duedate);
                                borrowDataList.add(borrowData);
                                if(!borrowDataList.isEmpty()){
                                    for(int i=0; i< borrowDataList.size(); i++){
                                        if(DateUtil.checkTimeElapseOrNot(borrowDataList.get(i).getDueDate(),currentDate)){
                                            borrowList.add(borrowDataList.get(i));
                                        }
                                    }
                                    if(!borrowList.isEmpty()){
                                        Log.e("borrow list size  :",""+borrowList.size());
                                        canBorrow.setText("You have "+ borrowList.size()+" overdue loans.");
                                        Integer totalDayElapsed = 0;
                                        for(int i=0; i< borrowList.size(); i++){
                                            String diff = DateUtil.getDiffBetTwoDate(borrowList.get(i).getDueDate(), currentDate);
                                            if(diff != null){
                                                totalDayElapsed = totalDayElapsed + Integer.parseInt(diff);
                                            }
                                        }
                                        Log.e("total day Elapsed  :",""+totalDayElapsed);
                                        if(totalDayElapsed != 0){
                                            bookfee = totalDayElapsed * 0.50;
                                            final String id = ref.child("overdueloans").push().getKey();
                                            final String days = totalDayElapsed.toString();
                                            final String fee = bookfee.toString();
                                            ref.child("overdueloans").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot child : snapshot.getChildren()) {
                                                        String overdueisbn = (String) child.child("isbn").getValue();
                                                        if(!overdueisbn.equals(isbn)){
                                                            writeNewLoan(id, useremail, isbn, bookname, duedate, days, fee);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            //totalCost.setText("Total Overdue Fee: $ "+ totalPrice +".00");
                                        }

                                    }



                                }
                                //adapter.borrowdata.add(borrowData);
                                //borrowcount += 1;
                                //maxcount -= 1;

                            }

                            //b = (String) child.child("bookname").getValue();
                            //Log.d(TAG, b);
                        }
                        ref.child("overdueloans").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Double totalFee = 0.00;
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    String em = (String) child.child("email").getValue();
                                    if(em.equals(useremail)){
                                        String isbn = (String) child.child("isbn").getValue();
                                        String bookname = (String) child.child("bookname").getValue();
                                        String duedate = (String) child.child("duedate").getValue();
                                        Integer days = Integer.parseInt((String)child.child("days").getValue()) ;
                                        Double fee = Double.parseDouble((String)child.child("fees").getValue());
                                        OverdueLoanData overdueLoanData = new OverdueLoanData(useremail, isbn, bookname, duedate, days, fee);
                                        overdueloandadapter.loanData.add(overdueLoanData);
                                        totalFee += fee;
                                    }
                                }
                                String stringfee = totalFee.toString();
                                totalCost.setText("Total Overdue Fee: $ "+ stringfee);
                                overdueloandadapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




                        //String borrowno = borrowcount.toString();
                        //String maxno = maxcount.toString();
                        //hbno.setText(borrowno);
                        //cbno.setText(maxno);
                        //adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        overdueloandadapter.notifyDataSetChanged();






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
