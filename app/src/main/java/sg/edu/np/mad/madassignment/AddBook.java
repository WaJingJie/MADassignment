package sg.edu.np.mad.madassignment;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.app.AlertDialog;
import java.util.ArrayList;

public class AddBook extends AppCompatActivity{

    private static final String TAG = "NPLibrary";
    EditText addisbn;
    EditText addbookname;
    EditText bookid;


    private ImageButton logoutbutton, homebutton, profilebutton, addbook, deletebook;
    private Button addbtn;

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
        bookid = findViewById(R.id.bookid);

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

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation to disallow empty fields for both isbn and book name field
                if(addisbn.getText().toString().isEmpty() || addbookname.getText().toString().isEmpty() || bookid.getText().toString().isEmpty()) {
                    Toast.makeText(AddBook.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }
                //continue if all fields are filled
                else{
                    String isbn = addisbn.getText().toString();
                    for(int i = 0; i < addisbnList.size(); i++){
                        if(isbn == addisbnList.get(i)){
                            bookidList.add(bookidList.get(bookidList.size() - 1) + 1);
                            Integer copy = copiesList.get(copiesList.size() - 1);
                            copiesList.set(i, (copy + 1));
                            statusList.add("Available");

                            Log.d("List", addisbnList.toString());
                            Log.d("List", addbooknameList.toString());
                            Log.d("List", bookidList.toString());
                            Log.d("List", copiesList.toString());
                            Log.d("List", statusList.toString());

                            Toast.makeText(getApplicationContext(), "Book successfully added!",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            //adding input fields to list when btn is pressed
                            addisbnList.add(addisbn.getText().toString());
                            addbooknameList.add(addbookname.getText().toString());
                            bookidList.add(bookid.getText().toString());
                            copiesList.add(1);
                            statusList.add("Available");

                            Log.d("List", addisbnList.toString());
                            Log.d("List", addbooknameList.toString());
                            Log.d("List", bookidList.toString());
                            Log.d("List", copiesList.toString());
                            Log.d("List", statusList.toString());

                            Toast.makeText(getApplicationContext(), "Book successfully added!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    Toast.makeText(getApplicationContext(), "Book successfully borrowed!",
                            Toast.LENGTH_LONG).show();
                    returnQuery();
                }
            }
        });



        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(AddBook.this, MainActivity.class);
                startActivity(welcomepage);
            }
        });
    }

    private void returnQuery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Return to Home Page?");
        Log.v(TAG, "Return option given to user!");
        builder.setMessage("Would you like to return to the home page or continue adding books?");
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
