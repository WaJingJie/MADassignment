package sg.edu.np.mad.madassignment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddBook extends AppCompatActivity{

    private static final String TAG = "NPLibrary";
    EditText addisbn;
    EditText addbookname;
    EditText bookid;
    DBHandler dbHandler;

    private ImageButton logoutbutton, homebutton, profilebutton, addbook, deletebook;
    private Button addbtn;
    private DatabaseReference ref;
    private ArrayList<String> bookidList = new ArrayList<>();
    private ArrayList<String> addisbnList = new ArrayList<>();
    private ArrayList<String> addbooknameList = new ArrayList<>();
    private ArrayList<Integer> copiesList = new ArrayList<>();
    private ArrayList<String> statusList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbook);
        addisbn = findViewById(R.id.addisbnfield);
        addbookname = findViewById(R.id.addbookname);
        logoutbutton = findViewById(R.id.stafflogoutbutton);
        homebutton = findViewById(R.id.staffhomebutton);
        profilebutton = findViewById(R.id.staffprofilebutton);
        addbook = findViewById(R.id.addbookicon);
        deletebook = findViewById(R.id.deletebookicon);
        addbtn = findViewById(R.id.addbutton);

        //this gets the data from home page
        Intent recieveingEnd = getIntent();
        //get arraylist from homepage
        bookidList = recieveingEnd.getStringArrayListExtra("bookid");
        addisbnList = recieveingEnd.getStringArrayListExtra("isbn");
        addbooknameList = recieveingEnd.getStringArrayListExtra("bookname");
        copiesList = recieveingEnd.getIntegerArrayListExtra("copies");
        statusList = recieveingEnd.getStringArrayListExtra("status");

        dbHandler = new DBHandler(this,null,null,1);
        final String status = "Available";

        ref = FirebaseDatabase.getInstance().getReference();

        //this is to add the book to the database
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation to disallow empty fields for both isbn and book name field
                final String isbn = addisbn.getText().toString();
                final String bookname = addbookname.getText().toString();
                if(isbn.isEmpty() || bookname.isEmpty()) {
                    Toast.makeText(AddBook.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }
                //continue if all fields are filled
                else{
                    dbHandler.addBook(isbn, bookname, status);
                    //String myUserId = getUid();
                    //this makes the id for the new book object in firebase
                    String id = ref.child("books").push().getKey();
                    //this makes the new object using the entered data
                    writeNewBook(id, isbn, bookname);
                    Toast.makeText(getApplicationContext(), "Book successfully added!",
                            Toast.LENGTH_LONG).show();
                    //this prompts the staff whether they want to continue adding books or return to the homepage
                    returnQuery();
                }
            }
        });

        //this is to allow the staff to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent welcomepage = new Intent(AddBook.this, StaffLoginPage.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the staff to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(AddBook.this, StaffProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the staff to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(AddBook.this, StaffHomePage.class);
                startActivity(homepage);
            }
        });

        //this is to redirect the staff to the add book page
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addbook = new Intent(AddBook.this, AddBook.class);
                startActivity(addbook);
            }
        });

        //this is to redirect the staff to the delete book page
        deletebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deletebook = new Intent(AddBook.this, DeleteBook.class);
                startActivity(deletebook);
            }
        });

    }

    //this method creates the new book object
    private void writeNewBook(String id, String isbn, String name) {
        ref.child("books").child(id).child("isbn").setValue(isbn);
        ref.child("books").child(id).child("bookname").setValue(name);
        ref.child("books").child(id).child("status").setValue("Available");
    }


    //this asks the staff whether they want to continue adding books
    private void returnQuery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Return to Home Page?");
        Log.v(TAG, "Return option given to user!");
        builder.setMessage("Select 'Yes' to return to the home page and 'No' to continue adding books.");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User accepts!");
                returnHomePage();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User decline!");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //this returns the staff to the home page
    private void returnHomePage(){
        //intent to go back to homepage
        Intent backtohome = new Intent(AddBook.this, StaffHomePage.class);
        //allows for multiple data to be intent to homepage
        Bundle data = new Bundle();

        data.putStringArrayList("isbn", addisbnList);
        data.putStringArrayList("bookname", addbooknameList);
        data.putIntegerArrayList("copies", copiesList);
        data.putStringArrayList("status", statusList);
        data.putStringArrayList("bookid", bookidList);
        backtohome.putExtras(data);
        //begins actitvity of homepage
        startActivity(backtohome);
    }




}
