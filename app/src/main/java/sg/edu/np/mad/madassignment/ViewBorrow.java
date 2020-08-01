package sg.edu.np.mad.madassignment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewBorrow extends AppCompatActivity{
    FloatingActionButton fab;
    private static final String TAG = "NP Library";
    TextView hbno;
    TextView cbno;
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;

    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    LibraryAdapter adapter;
    Integer borrowcount;
    Integer maxcount;
    String email;
    ArrayList<String> isbnList = new ArrayList<>();
    ArrayList<String> booknameList = new ArrayList<>();
    ArrayList<String> borrowdateList = new ArrayList<>();
    ArrayList<String> duedateList = new ArrayList<>();

    DBHandler dbHandler;

    DatabaseReference ref;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        fab = findViewById(R.id.homepageadd);

        hbno = findViewById(R.id.hbno);
        cbno = findViewById(R.id.cbno);

        logoutbutton = findViewById(R.id.studentlogoutbutton);
        homebutton = findViewById(R.id.studenthomebutton);
        profilebutton = findViewById(R.id.studentprofilebutton);
        viewbutton = findViewById(R.id.viewborrowicon);
        overduebutton = findViewById(R.id.overdueicon);

        UserData userData = LoginPage.userdata;

        //initialize db
        dbHandler = new DBHandler(this,null,null,1);

//        //this gets the data from borrow book page
//        Intent receivingEnd = getIntent();
//
//        //get arraylist from borrowbook page
//        isbnList = receivingEnd.getStringArrayListExtra("isbn");
//        booknameList = receivingEnd.getStringArrayListExtra("bookname");
//        borrowdateList = receivingEnd.getStringArrayListExtra("borrowdate");
//        duedateList = receivingEnd.getStringArrayListExtra("duedate");
//
//        //test log to see if arraykist is created properly
//        if(isbnList == null){
//            //sets the maximum borrow to 9 and borrow book count to 9 when the list is empty
//            hbno.setText("0");
//            cbno.setText("9");
//            Log.d("List", "empty");
//        }
//        //continue on with the rest of the code if list is not null. null list will crash the program.
//        else if(isbnList != null){
//            //display the no of books borrowed
//            int borrowcount = isbnList.size();
//            hbno.setText(Integer.toString(borrowcount));
//
//            //display the remaining borrow count(9 is max)
//            int remainingcount = 9-borrowcount;
//            cbno.setText(Integer.toString(remainingcount));
//
//            //recyclerview code goes here
//            RecyclerView rv = findViewById(R.id.homepageview);
//
//            LibraryAdapter adapter = new LibraryAdapter(isbnList, booknameList, borrowdateList, duedateList);
//            rv.setAdapter(adapter);
//
//            LinearLayoutManager layout = new LinearLayoutManager(this);
//            rv.setLayoutManager(layout);
//            rv.setItemAnimator(new DefaultItemAnimator());
//            adapter.notifyDataSetChanged();
//
//            Log.d("List", isbnList.toString());
//            Log.d("List", booknameList.toString());
//            Log.d("List", borrowdateList.toString());
//            Log.d("List", duedateList.toString());
//        }

        //initialize rv
        rv = findViewById(R.id.homepageview);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        borrowcount = 0;
        maxcount = 9;
        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //retrieves borrowbooks from db and display it in rv
        adapter = new LibraryAdapter(this, new ArrayList<BorrowData>());
        rv.setAdapter(adapter);

        ref.child("users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                email = (String)snapshot.child("email").getValue();
                Log.d(TAG, email);
                ref.child("borrowedbooks").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot child : snapshot.getChildren()) {
                            String e = (String) child.child("email").getValue();
                            if(e.equals(email)){
                                String isbn = (String) child.child("isbn").getValue();
                                String bookname = (String) child.child("bookname").getValue();
                                String borrowdate = (String) child.child("borrowdate").getValue();
                                String duedate = (String) child.child("duedate").getValue();
                                BorrowData borrowData = new BorrowData(email, isbn, bookname, borrowdate, duedate);
                                adapter.borrowdata.add(borrowData);
                                borrowcount += 1;
                                maxcount -= 1;

                            }

                            //b = (String) child.child("bookname").getValue();
                            //Log.d(TAG, b);
                        }
                        String borrowno = borrowcount.toString();
                        String maxno = maxcount.toString();
                        hbno.setText(borrowno);
                        cbno.setText(maxno);
                        adapter.notifyDataSetChanged();
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





        //display the no of books borrowed
        //hbno.setText(borrowcount);

        //display the remaining borrow count(9 is max)
        //int remainingcount = 9- adapter.getItemCount();
        //cbno.setText(maxcount);

        //creates an onclick listener to notify the user that they have reached the max amount of books they can borrow
        if(Integer.parseInt(cbno.getText().toString()) == adapter.getItemCount()){

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewBorrow.this, "Reached maximum number of borrowed books.", Toast.LENGTH_LONG).show();
                }
            });
        }

        else{
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toborrowpage = new Intent(ViewBorrow.this, BorrowBook.class);
                    //Bundle data = new Bundle();
//                    data.putStringArrayList("isbn", isbnList);
//                    data.putStringArrayList("bookname", booknameList);
//                    data.putStringArrayList("borrowdate", borrowdateList);
//                    data.putStringArrayList("duedate", duedateList);
//                    toborrowpage.putExtras(data);
                    startActivity(toborrowpage);
                }
            });
        }

        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(ViewBorrow.this, MainActivity.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the user to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(ViewBorrow.this, ProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the user to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(ViewBorrow.this, StudentHomePage.class);
                startActivity(homepage);
            }
        });

        //this is to redirect the user to the view borrowed books page
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(ViewBorrow.this, ViewBorrow.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the user to the overdue page
        overduebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overduepage = new Intent(HomePage.this, OverDueLoan.class);
                startActivity(overduepage);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}