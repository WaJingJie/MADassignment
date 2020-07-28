package sg.edu.np.mad.madassignment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BorrowBook extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText bookname;
    EditText borrowdate;
    EditText duedate;

    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    Button borrowbtn;

    Spinner spinner;

    DBHandler dbHandler;

    ArrayList<String> isbnList = new ArrayList<>();
    ArrayList<String> booknameList = new ArrayList<>();
    ArrayList<String> borrowdateList = new ArrayList<>();
    ArrayList<String> duedateList = new ArrayList<>();
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

        final UserData userData = LoginPage.userdata;

        //initialize database
        dbHandler = new DBHandler(this,null,null,1);

        //gets the today date
        Calendar c = Calendar.getInstance();
        String todaydate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        borrowdate.setText(todaydate);

        //auto increment date for due date
        incrementdate();

        /*//this gets the data from home page
        Intent recieveingEnd = getIntent();
        //get arraylist from homepage
        isbnList = recieveingEnd.getStringArrayListExtra("isbn");
        booknameList = recieveingEnd.getStringArrayListExtra("bookname");
        borrowdateList = recieveingEnd.getStringArrayListExtra("borrowdate");
        duedateList = recieveingEnd.getStringArrayListExtra("duedate");*/

//        //statement to detect if the list is null
//        if(isbnList == null){
//            //recreates the list to not make it null
//            isbnList= new ArrayList<String>();
//            booknameList= new ArrayList<String>();
//            borrowdateList= new ArrayList<String>();
//            duedateList= new ArrayList<String>();
//        }

        //database version
        ArrayList<String> isbnlist = dbHandler.getIsbn();
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this,R.layout.spinnerlayout, R.id.tvspinner, isbnlist);
        spinner.setAdapter(spinneradapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedisbn = parent.getItemAtPosition(position).toString();
                String book = dbHandler.getBookByISBN(selectedisbn);
                bookname.setText(book);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //end of database version

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

        //this allows the user to borrow book
        borrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //validation to disallow empty fields for both isbn and book name field
//                if(isbn.getText().toString().isEmpty() || bookname.getText().toString().isEmpty()) {
//                    Toast.makeText(BorrowBook.this, "Please enter ISBN and book name details", Toast.LENGTH_SHORT).show();
//                }
//                //continue if all fields are filled
//                else {
//                    //adding input fields to list when btn is pressed
//                    isbnList.add(isbn.getText().toString());
//                    booknameList.add(bookname.getText().toString());
//                    borrowdateList.add(borrowdate.getText().toString());
//                    duedateList.add(duedate.getText().toString());
//
//                    /*Log.d("List", isbnList.toString());
//                    Log.d("List", booknameList.toString());
//                    Log.d("List", borrowdateList.toString());
//                    Log.d("List", duedateList.toString());*/
//                    Toast.makeText(getApplicationContext(), "Book successfully borrowed!",
//                            Toast.LENGTH_LONG).show();
//
//                    //allows for multiple data to be intent to homepage
//                    Bundle data = new Bundle();
//
//                    data.putStringArrayList("isbn", isbnList);
//                    data.putStringArrayList("bookname", booknameList);
//                    data.putStringArrayList("borrowdate", borrowdateList);
//                    data.putStringArrayList("duedate", duedateList);
//                    backtohome.putExtras(data);
//                    //begins actitvity of homepage
//                    startActivity(backtohome);
//                }
                addtodb(userData.getMyEmail(), spinner.getSelectedItem().toString(), bookname.getText().toString(), borrowdate.getText().toString(), duedate.getText().toString());
                //updates book status to unavailable
                dbHandler.updatebookStatus(spinner.getSelectedItem().toString());

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
                /*Intent overduepage = new Intent(HomePage.this, OverduePage.class);
                startActivity(overduepage);*/
            }
        });

    }

    private void addtodb(String email, String isbn, String bookname, String borrowdate, String duedate){
        dbHandler.addBorrowedBook(email, isbn, bookname, borrowdate, duedate);

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
        DateFormat sdf = new SimpleDateFormat("M/dd/yy");

        String date = sdf.format(getDate);
        duedate.setText(date);
    }

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

