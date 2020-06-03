package sg.edu.np.mad.madassignment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    Button addbook;
    RecyclerView rv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        ArrayList<String> isbn = new ArrayList<>();
        ArrayList<String> bookname = new ArrayList<>();
        ArrayList<String> dateborrowed = new ArrayList<>();
        ArrayList<String> duedate = new ArrayList<>();
        rv = findViewById(R.id.homepageview);
        addbook = findViewById(R.id.homepageadd);
        LibraryAdapter adapter = new LibraryAdapter(isbn, bookname, dateborrowed, duedate);
        rv.setAdapter(adapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borrowpage = new Intent(HomePage.this, BorrowBook.class);
                startActivity(borrowpage);
            }
        });
    }

}
