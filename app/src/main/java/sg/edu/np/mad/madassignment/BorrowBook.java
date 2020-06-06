package sg.edu.np.mad.madassignment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class BorrowBook extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText isbn;
    EditText bookname;
    EditText borrowdate;
    EditText duedate;

    ImageButton logoutbutton, homebutton, profilebutton;
    Button borrowbtn;

    ArrayList<String> isbnList = new ArrayList<>();
    ArrayList<String> booknameList = new ArrayList<>();
    ArrayList<String> borrowdateList = new ArrayList<>();
    ArrayList<String> duedateList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrowbook);

        isbn = findViewById(R.id.isbnfield);
        bookname = findViewById(R.id.bookname);

        borrowdate = findViewById(R.id.dateborrowed);
        borrowdate.setInputType(InputType.TYPE_NULL);

        duedate = findViewById(R.id.duedatefield);
        duedate.setInputType(InputType.TYPE_NULL);

        logoutbutton = findViewById(R.id.logoutbutton);
        homebutton = findViewById(R.id.homebutton);
        profilebutton = findViewById(R.id.profilebutton);
        borrowbtn = findViewById(R.id.borrowbutton);

        //gets the today date
        Calendar c = Calendar.getInstance();
        String todaydate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        borrowdate.setText(todaydate);

        //auto increment date for due date
        incrementdate();

        //this gets the data from home page
        Intent recieveingEnd = getIntent();
        //get arraylist from homepage
        isbnList = recieveingEnd.getStringArrayListExtra("isbn");
        booknameList = recieveingEnd.getStringArrayListExtra("bookname");
        borrowdateList = recieveingEnd.getStringArrayListExtra("borrowdate");
        duedateList = recieveingEnd.getStringArrayListExtra("duedate");

        //statement to detect if the list is null
        if(isbnList == null){
            //recreates the list to not make it null
            isbnList= new ArrayList<String>();
            booknameList= new ArrayList<String>();
            borrowdateList= new ArrayList<String>();
            duedateList= new ArrayList<String>();
        }

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

        borrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adding input fields to list when btn is pressed
                isbnList.add(isbn.getText().toString());
                booknameList.add(bookname.getText().toString());
                borrowdateList.add(borrowdate.getText().toString());
                duedateList.add(duedate.getText().toString());

                /*Log.d("List", isbnList.toString());
                Log.d("List", booknameList.toString());
                Log.d("List", borrowdateList.toString());
                Log.d("List", duedateList.toString());*/
                Toast.makeText(getApplicationContext(), "Book successfully borrowed!",
                        Toast.LENGTH_LONG).show();
                //intent to go back to homepage
                Intent backtohome = new Intent(BorrowBook.this, HomePage.class);
                //allows for multiple data to be intent to homepage
                Bundle data = new Bundle();

                data.putStringArrayList("isbn", isbnList);
                data.putStringArrayList("bookname", booknameList);
                data.putStringArrayList("borrowdate", borrowdateList);
                data.putStringArrayList("duedate", duedateList);
                backtohome.putExtras(data);
                //begins actitvity of homepage
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

        //this is to redirect the user to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(BorrowBook.this, HomePage.class);
                startActivity(homepage);
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

        borrowdate = findViewById(R.id.dateborrowed);
        borrowdate.setText(currentDateString);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

