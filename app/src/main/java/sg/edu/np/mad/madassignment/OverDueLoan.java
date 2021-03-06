package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
    TextView canBorrow, totalCost;
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    List<BorrowData> borrowDataList = new ArrayList<>();
    List<BorrowData> overdueList = new ArrayList<>();
    RecyclerView homepageview;
    String currentDate;
    DatabaseReference ref;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Overdueloandadapter overdueloandadapter;
    String useremail;
    String overduename;
    String overdueisbn;
    String overduedate;
    Integer overduedays;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overduebook);

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

        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        overdueloandadapter = new Overdueloandadapter(OverDueLoan.this, new ArrayList<OverdueLoanData>());
        homepageview.setAdapter(overdueloandadapter);

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

    @Override
    protected void onStart() {
        super.onStart();
        overdueloandadapter.loanData.clear();
        ref.child("users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this gets the user email
                useremail = (String)snapshot.child("email").getValue();
                ref.child("borrowedbooks").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            //this gets the email from the borrowedbook object
                            String e = (String) child.child("email").getValue();
                            //this validates whether the email matches the user email
                            if(e.equals(useremail)){
                                //this gets the isbn, name, borrow date and due date
                                String bookisbn = (String) child.child("isbn").getValue();
                                String bookname = (String) child.child("bookname").getValue();
                                String bookborrow = (String) child.child("borrowdate").getValue();
                                String bookdate = (String) child.child("duedate").getValue();
                                //this creates the new borrow data object
                                BorrowData borrowData = new BorrowData(useremail, bookisbn, bookname, bookborrow, bookdate);
                                //this adds the object to the list
                                borrowDataList.add(borrowData);
                                //this checks that the borrowdata list is not empty
                            }

                        }

                        final List<Book> newOverdueBooks = new ArrayList<>();
                        if(!borrowDataList.isEmpty()){
                            final List<String> addedISBNs = new ArrayList<>();
                            for(int i=0; i< borrowDataList.size(); i++) {
                                if (DateUtil.checkTimeElapseOrNot(borrowDataList.get(i).getDueDate(), currentDate)) {
                                    overdueList.add(borrowDataList.get(i));

                                    Log.e("borrow list size  :",""+overdueList.size());
                                    canBorrow.setText("You have "+ overdueList.size()+" overdue loans.");
                                    Integer days = 0;
                                    Integer s = overdueList.size() -1;

                                    String diff = DateUtil.getDiffBetTwoDate(overdueList.get(s).getDueDate(), currentDate);
                                    if(diff != null){
                                        //this gets the days overdue
                                        days = Integer.parseInt(diff);
                                        overduedays = days;
                                        //this calculates the book fee
                                        Log.e("total day Elapsed  :",""+  overduedays);
                                        overdueisbn = overdueList.get(s).getISBN();
                                        overduename = overdueList.get(s).getMyBookName();
                                        overduedate = overdueList.get(s).getDueDate();
                                        ref.child("overdueloans").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                //Log.e("total day Elapsed  :",""+  overduedays + fees);
                                                boolean flag = false;
                                                for (DataSnapshot child : snapshot.getChildren()) {
                                                    //Log.e("total day Elapsed  :",""+  overduedays + fees);
                                                    String isbn = (String) child.child("isbn").getValue();
                                                    if(overdueisbn.equals(isbn)){
                                                        flag = true;
                                                        break;
                                                    }
                                                    //Log.e("total day Elapsed  :",""+  overduedays + fees);
                                                }
                                                if(!flag && !addedISBNs.contains(overdueisbn)){
                                                    Double fees = overduedays * 0.50;
                                                    //this makes an unique id for the new overdueloan object in firebase
                                                    String id = ref.child("overdueloans").push().getKey();
                                                    //this creates the new overdue object in firebase
                                                    Log.e("total day Elapsed  :",""+  overduedays + fees + overduedate);
                                                    writeNewLoan(id, useremail, overdueisbn, overduename, overduedate, overduedays, fees);
                                                    addedISBNs.add(overdueisbn);

                                                    update();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                }
                            }
                        }

                        update();
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
    }

    private void update() {
        ref.child("overdueloans").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                overdueloandadapter.loanData.clear();
                Double totalFee = 0.00;
                for (DataSnapshot child : snapshot.getChildren()) {
                    String em = (String) child.child("email").getValue();
                    if(em.equals(useremail)){
                        String isbn = (String) child.child("isbn").getValue();
                        String bookname = (String) child.child("bookname").getValue();
                        String duedate = (String) child.child("duedate").getValue();
                        String diff = DateUtil.getDiffBetTwoDate(duedate, currentDate);
                        Integer days = 0;
                        if(diff != null){
                            //this gets the days overdue
                            days += Integer.parseInt(diff);
                        }
                        Double fee = days * 0.50;
                        ref.child("overdueloans").child(child.getKey()).child("days").setValue(days);
                        ref.child("overdueloans").child(child.getKey()).child("fees").setValue(fee);
                        //this creates a new overdue loan object
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
    }

    //this method creates the new overdue loan object
    private void writeNewLoan(String id, String email, String isbn, String name, String duedate, Integer days, Double fee) {
        ref.child("overdueloans").child(id).child("email").setValue(email);
        ref.child("overdueloans").child(id).child("isbn").setValue(isbn);
        ref.child("overdueloans").child(id).child("bookname").setValue(name);
        ref.child("overdueloans").child(id).child("duedate").setValue(duedate);
        ref.child("overdueloans").child(id).child("days").setValue(days);
        ref.child("overdueloans").child(id).child("fees").setValue(fee);
    }

}
