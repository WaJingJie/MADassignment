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

public class DeleteBook extends AppCompatActivity {
    private static final String TAG = "NPLibrary";
    EditText deleteisbn;
    EditText deletebookname;
    EditText deletebookid;

    private ImageButton logoutbutton, homebutton, profilebutton, addbook, deletebook;
    private Button deletebtn;

    private ArrayList<String> bookidList = new ArrayList<>();
    private ArrayList<String> deleteisbnList = new ArrayList<>();
    private ArrayList<String> deletebooknameList = new ArrayList<>();
    private ArrayList<Integer> copiesList = new ArrayList<>();
    private ArrayList<String> statusList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletebook);
        deleteisbn = findViewById(R.id.deleteisbnfield);
        deletebookname = findViewById(R.id.deletebookname);
        deletebookid = findViewById(R.id.deletebookid);

        logoutbutton = findViewById(R.id.stafflogoutbutton);
        homebutton = findViewById(R.id.staffhomebutton);
        profilebutton = findViewById(R.id.staffprofilebutton);
        addbook = findViewById(R.id.addbookicon);
        deletebook = findViewById(R.id.deletebookicon);
        deletebtn = findViewById(R.id.deletebutton);

        //this gets the data from home page
        Intent recieveingEnd = getIntent();
        //get arraylist from homepage
        bookidList = recieveingEnd.getStringArrayListExtra("bookid");
        deleteisbnList = recieveingEnd.getStringArrayListExtra("isbn");
        deletebooknameList = recieveingEnd.getStringArrayListExtra("bookname");
        copiesList = recieveingEnd.getIntegerArrayListExtra("copies");
        statusList = recieveingEnd.getStringArrayListExtra("status");

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation to disallow empty fields for both isbn and book name field
                if(deleteisbn.getText().toString().isEmpty() || deletebookname.getText().toString().isEmpty() || deletebookid.getText().toString().isEmpty()) {
                    Toast.makeText(DeleteBook.this, "Please ensure all details are filled", Toast.LENGTH_LONG).show();
                }
                //continue if all fields are filled
                else{
                    Integer bookid = Integer.parseInt(deletebookid.getText().toString());;
                    for(int i = 0; i < bookidList.size(); i++){

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

                        Toast.makeText(getApplicationContext(), "Book successfully deleted",
                                Toast.LENGTH_LONG).show();
                        returnQuery();
                    }

                }
            }
        });

        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(DeleteBook.this, MainActivity.class);
                startActivity(welcomepage);
            }
        });

    }

    private void returnQuery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Return to Home Page?");
        Log.v(TAG, "Return option given to user!");
        builder.setMessage("Would you like to return to the home page or continue deleting books?");
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
