package sg.edu.np.mad.madassignment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity{
    FloatingActionButton fab;

    TextView hbno;
    TextView cbno;
    ImageButton logoutbutton, homebutton, profilebutton;
    ArrayList<String> isbnList = new ArrayList<>();
    ArrayList<String> booknameList = new ArrayList<>();
    ArrayList<String> borrowdateList = new ArrayList<>();
    ArrayList<String> duedateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        fab = findViewById(R.id.homepageadd);

        hbno = findViewById(R.id.hbno);
        cbno = findViewById(R.id.cbno);

        logoutbutton = findViewById(R.id.logoutbutton);
        homebutton = findViewById(R.id.homebutton);
        profilebutton = findViewById(R.id.profilebutton);
        //this gets the data from borrow book page
        Intent receivingEnd = getIntent();

        //get arraylist from borrowbook page
        isbnList = receivingEnd.getStringArrayListExtra("isbn");
        booknameList = receivingEnd.getStringArrayListExtra("bookname");
        borrowdateList = receivingEnd.getStringArrayListExtra("borrowdate");
        duedateList = receivingEnd.getStringArrayListExtra("duedate");

        //test log to see if arraykist is created properly
        if(isbnList == null){
            //sets the maximum borrow to 9 and borrow book count to 9 when the list is empty
            hbno.setText("0");
            cbno.setText("9");
            Log.d("List", "empty");
        }
        //continue on with the rest of the code if list is not null. null list will crash the program.
        else if(isbnList != null){
            //display the no of books borrowed
            int borrowcount = isbnList.size();
            hbno.setText(Integer.toString(borrowcount));

            //display the remaining borrow count(9 is max)
            int remainingcount = 9-borrowcount;
            cbno.setText(Integer.toString(remainingcount));

            //recyclerview code goes here
            RecyclerView rv = findViewById(R.id.homepageview);

            LibraryAdapter adapter = new LibraryAdapter(isbnList, booknameList, borrowdateList, duedateList);
            rv.setAdapter(adapter);

            LinearLayoutManager layout = new LinearLayoutManager(this);
            rv.setLayoutManager(layout);
            rv.setItemAnimator(new DefaultItemAnimator());
            adapter.notifyDataSetChanged();

            Log.d("List", isbnList.toString());
            Log.d("List", booknameList.toString());
            Log.d("List", borrowdateList.toString());
            Log.d("List", duedateList.toString());
        }
        //creates an onclick listener to redirect to borrow book layout when it the borrow limit is not equals to zero
        if(Integer.parseInt(cbno.getText().toString()) != 0){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toborrowpage = new Intent(HomePage.this, BorrowBook.class);
                    Bundle data = new Bundle();

                    data.putStringArrayList("isbn", isbnList);
                    data.putStringArrayList("bookname", booknameList);
                    data.putStringArrayList("borrowdate", borrowdateList);
                    data.putStringArrayList("duedate", duedateList);
                    toborrowpage.putExtras(data);
                    startActivity(toborrowpage);
                }
            });
        }
        //creates an onclick listener to notify the user that they have reached the max amount of books they can borrow
        else if(Integer.parseInt(cbno.getText().toString()) == 0){

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HomePage.this, "Reached maximum number of borrowed books.", Toast.LENGTH_LONG).show();
                }
            });
        }


        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(HomePage.this, MainActivity.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the user to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(HomePage.this, ProfilePage.class);
                startActivity(profilepage);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
