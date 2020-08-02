package sg.edu.np.mad.madassignment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BorrowBook extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String FILENAME = "BorrowBook.java";
    private static final String TAG = "NP Library";

    TextView bookname;
    EditText borrowdate;
    EditText duedate;

    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    Button borrowbtn;

    Spinner spinner;
    ArrayAdapter<String> spinneradapter;
    DBHandler dbHandler;
    private DatabaseReference ref;
    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    String selectedisbn;
    String textname;
    String borrowdatetext;
    String duedatetext;
    String email;
    String b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrowbook);

        bookname = findViewById(R.id.bookname);

        borrowdate = findViewById(R.id.borrowdate);
        borrowdate.setInputType(InputType.TYPE_NULL);

        duedate = findViewById(R.id.duedate);
        duedate.setInputType(InputType.TYPE_NULL);

        logoutbutton = findViewById(R.id.studentlogoutbutton);
        homebutton = findViewById(R.id.studenthomebutton);
        profilebutton = findViewById(R.id.studentprofilebutton);
        viewbutton = findViewById(R.id.viewborrowicon);
        overduebutton = findViewById(R.id.overdueicon);
        borrowbtn = findViewById(R.id.btnborrow);

        spinner = findViewById(R.id.isbnlist);

        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this creates a new list for the spinner
                ArrayList<String> booklist = new ArrayList<>();
                //this gets the isbn for the list
                for (DataSnapshot child : snapshot.getChildren()) {
                    String bookisbn = (String) child.child("isbn").getValue();
                    String bookstatus = (String) child.child("status").getValue();
                    //this validates that the book status is available
                    if(bookstatus.equals("Available")){
                        //this adds the isbn to the list
                        booklist.add(bookisbn);
                    }
                    spinneradapter = new ArrayAdapter<>(BorrowBook.this, R.layout.spinnerlayout, R.id.tvspinner, booklist);
                    spinner.setAdapter(spinneradapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //this method is used when the isbn is selected in the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //this gets the isbn that is selected
                selectedisbn = parent.getItemAtPosition(position).toString();
                ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot child : snapshot.getChildren()) {
                            String bookisbn = (String) child.child("isbn").getValue();
                            String bookstatus = (String) child.child("status").getValue();
                            //this checks that the book isbn matches the selected isbn and checks the book status is available
                            if(bookisbn.equals(selectedisbn) && bookstatus.equals("Available")){
                                //this gets the bookname
                                b = (String) child.child("bookname").getValue();
                                Log.d(TAG, b);
                                //this sets the name field to the book name
                                bookname.setText(b);
                                //gets the today date
                                Calendar c = Calendar.getInstance();
                                String todaydate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
                                //this sets the borrow date field to the borrow date
                                borrowdate.setText(todaydate);
                                //auto increment date for due date
                                //String d = borrowdate.getText().toString();
                                incrementdate();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //shows date picker when the text box is click
        borrowdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //auto fills up the due date by 2 weeks
        borrowdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                incrementdate();
            }
        });

        ref.child("users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this gets the email of the current user
                email = snapshot.child("email").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //this allows the user to borrow a book
        borrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textname = bookname.getText().toString();
                borrowdatetext = borrowdate.getText().toString();
                duedatetext = duedate.getText().toString();
                //this creates an unique id for the new borrow book object
                String id = ref.child("books").push().getKey();
                //this creates the borrow book object
                writeNewBorrowBook(id, selectedisbn, textname, borrowdatetext, duedatetext, email);
                ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot child : snapshot.getChildren()) {
                            //this gets the isbn of each book object in the for loop
                            String bookisbn = (String) child.child("isbn").getValue();
                            //this checks whether the isbn matches the selected isbn in the spinner
                            if(bookisbn.equals(selectedisbn)){
                                //this sets the status of the book object to unavailable
                                ref.child("books").child(child.getKey()).child("status").setValue("Unavailable");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(getApplicationContext(), "Book successfully borrowed!", Toast.LENGTH_LONG).show();

                //intent to go back to homepage
                Intent backtohome = new Intent(BorrowBook.this, ViewBorrow.class);
                startActivity(backtohome);
            }
        });

        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(BorrowBook.this, MainActivity.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the user to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(BorrowBook.this, ProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the user to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(BorrowBook.this, StudentHomePage.class);
                startActivity(homepage);
            }
        });

        //this is to redirect the user to the view borrowed books page
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(BorrowBook.this, ViewBorrow.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the user to the overdue page
        overduebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overduepage = new Intent(BorrowBook.this, OverDueLoan.class);
                startActivity(overduepage);
            }
        });

    }

    //this method creates a new borrowed book object to add to the firebase database
    private void writeNewBorrowBook(String id, String isbn, String name, String borrowdate, String duedate, String email) {
        ref.child("borrowedbooks").child(id).child("email").setValue(email);
        ref.child("borrowedbooks").child(id).child("isbn").setValue(isbn);
        ref.child("borrowedbooks").child(id).child("bookname").setValue(name);
        ref.child("borrowedbooks").child(id).child("borrowdate").setValue(borrowdate);
        ref.child("borrowedbooks").child(id).child("duedate").setValue(duedate);
    }

    //method used to increase due date by 14 days
    private void incrementdate(){
        String d = borrowdate.getText().toString();
        String[] dsplit = d.split("/");

        int day = Integer.parseInt(dsplit[0]);
        int month = Integer.parseInt(dsplit[1]);
        int year = Integer.parseInt(dsplit[2]);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year,day-1,month+14);

        Date getDate = calendar.getTime();
        //this creates the format
        DateFormat sdf = new SimpleDateFormat("M/dd/yy");
        //this formats the date
        String date = sdf.format(getDate);
        duedate.setText(date);
    }

    //this gets the current date and sets the borrow date field to the current date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());

        borrowdate = findViewById(R.id.borrowdate);
        borrowdate.setText(currentDateString);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

