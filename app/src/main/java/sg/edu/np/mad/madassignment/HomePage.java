package sg.edu.np.mad.madassignment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    FloatingActionButton fab;

    TextView hbno;
    TextView cbno;

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

        //this gets the data from borrow book page
        Intent recieveingEnd = getIntent();

        //get arraylist from borrowbook page
        isbnList = recieveingEnd.getStringArrayListExtra("isbn");
        booknameList = recieveingEnd.getStringArrayListExtra("bookname");
        borrowdateList = recieveingEnd.getStringArrayListExtra("borrowdate");
        duedateList = recieveingEnd.getStringArrayListExtra("duedate");

        //test log to see if arraykist is created properly
        if(isbnList == null){
            hbno.setText("0");
            cbno.setText("9");
            Log.d("List", "empty");
        }
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

    @Override
    protected void onResume() {
        super.onResume();

    }

}
