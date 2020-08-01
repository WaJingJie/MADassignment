package sg.edu.np.mad.madassignment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

import javax.crypto.spec.DESKeySpec;

public class DeleteBook extends AppCompatActivity {
    private static final String TAG = "NPLibrary";
    String selectedisbn;
    EditText deleteid;
    EditText deletebookname;
    EditText deletebookstatus;
    DBHandler dbHandler;
    Spinner spinner;
    ArrayAdapter<String> spinneradapter;
    Integer bookid;
    private ImageButton logoutbutton, homebutton, profilebutton, addbook, deletebook;
    private Button deletebtn;
    private DatabaseReference ref;
    private ArrayList<String> bookidList = new ArrayList<>();
    private ArrayList<String> deleteisbnList = new ArrayList<>();
    private ArrayList<String> deletebooknameList = new ArrayList<>();
    private ArrayList<Integer> copiesList = new ArrayList<>();
    private ArrayList<String> statusList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletebook);
        deleteid = findViewById(R.id.deletebookid);
        deletebookname = findViewById(R.id.deletebookname);
        deletebookstatus = findViewById(R.id.deletebookstatus);

        logoutbutton = findViewById(R.id.stafflogoutbutton);
        homebutton = findViewById(R.id.staffhomebutton);
        profilebutton = findViewById(R.id.staffprofilebutton);
        addbook = findViewById(R.id.addbookicon);
        deletebook = findViewById(R.id.deletebookicon);
        deletebtn = findViewById(R.id.deletebutton);

        spinner = findViewById(R.id.deleteisbnlist);

        dbHandler = new DBHandler(this,null,null,1);
        ref = FirebaseDatabase.getInstance().getReference();

        //this gets the data from home page
        Intent recieveingEnd = getIntent();
        //get arraylist from homepage
        /*bookidList = recieveingEnd.getStringArrayListExtra("bookid");
        deleteisbnList = recieveingEnd.getStringArrayListExtra("isbn");
        deletebooknameList = recieveingEnd.getStringArrayListExtra("bookname");
        copiesList = recieveingEnd.getIntegerArrayListExtra("copies");
        statusList = recieveingEnd.getStringArrayListExtra("status");*/

        ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> booklist = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    String bookisbn = (String) child.child("isbn").getValue();
                    String bookstatus = (String) child.child("status").getValue();
                    if(bookstatus.equals("Available")){
                        booklist.add(bookisbn);
                    }
                    spinneradapter = new ArrayAdapter<>(DeleteBook.this, R.layout.spinnerlayout, R.id.tvspinner, booklist);
                    spinner.setAdapter(spinneradapter);

                    //b = (String) child.child("bookname").getValue();
                    //Log.d(TAG, b);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedisbn = parent.getItemAtPosition(position).toString();
                bookid = dbHandler.getBookID(selectedisbn);
                String stringid = bookid.toString();
                String bookname = dbHandler.getBookName(selectedisbn);
                String bookstatus = dbHandler.getBookStatus(selectedisbn);
                deleteid.setText(stringid);
                deletebookname.setText(bookname);
                deletebookstatus.setText(bookstatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //this is to delete the book from the database
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation to disallow empty fields for both isbn and book name field
                if(deleteid.getText().toString().isEmpty() || deletebookname.getText().toString().isEmpty() || deletebookstatus.getText().toString().isEmpty()) {
                    Toast.makeText(DeleteBook.this, "Please ensure all details are filled", Toast.LENGTH_LONG).show();
                }
                //continue if all fields are filled
                else{
                    /*for(int i = 0; i < bookidList.size(); i++){
                        if(copiesList.get(i) == 1){
                            deleteisbnList.remove(deleteisbn.getText().toString());
                            deletebooknameList.remove(deletebookname.getText().toString());
                        }
                        Integer copy = copiesList.get(copiesList.size() - 1);
                        copiesList.set(i, (copy - 1));
                        bookidList.remove(bookid);
                        statusList.remove(statusList.get(i));

                        Log.d("List", deleteisbnList.toString());
                        Log.d("List", deletebooknameList.toString());
                        Log.d("List", bookidList.toString());
                        Log.d("List", copiesList.toString());
                        Log.d("List", statusList.toString());
                        returnQuery();
                    }*/
                    ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot child : snapshot.getChildren()) {
                                String isbn = (String) child.child("isbn").getValue();
                                if(isbn.equals(selectedisbn)){
                                    ref.child("books").child(child.getKey()).setValue(null);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    Toast.makeText(getApplicationContext(), "Book successfully deleted",
                            Toast.LENGTH_LONG).show();
                    returnQuery();
                }
            }
        });

        //this is to allow the staff to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(DeleteBook.this, StaffLoginPage.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the staff to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(DeleteBook.this, StaffProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the staff to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(DeleteBook.this, StaffHomePage.class);
                startActivity(homepage);
            }
        });

        //this is to redirect the staff to the add book page
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(DeleteBook.this, AddBook.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the staff to the delete book page
        deletebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deletebook = new Intent(DeleteBook.this, DeleteBook.class);
                startActivity(deletebook);
            }
        });

    }

    private void writeDeleteBook(String id) {
        ref.child("books").child(id).child("isbn").setValue(null);
        ref.child("books").child(id).child("bookname").setValue(null);
        ref.child("books").child(id).child("status").setValue(null);
    }

    //this asks the staff whether they want to continue deleting books
    private void returnQuery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Return to Home Page?");
        Log.v(TAG, "Return option given to user!");
        builder.setMessage("Select 'Yes' to return to the home page and 'No' to continue deleting books.");
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
        Intent backtohome = new Intent(DeleteBook.this, StaffHomePage.class);
        //allows for multiple data to be intent to homepage
        Bundle data = new Bundle();

        data.putStringArrayList("isbn", deleteisbnList);
        data.putStringArrayList("bookname", deletebooknameList);
        data.putIntegerArrayList("copies", copiesList);
        data.putStringArrayList("status", statusList);
        data.putStringArrayList("bookid", bookidList);
        backtohome.putExtras(data);
        //begins actitvity of homepage
        startActivity(backtohome);
    }
}
