package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class StudentHomePage extends AppCompatActivity {
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    Searchbookadapter adapter;
    ImageButton logoutbutton, homebutton, profilebutton, viewbutton, overduebutton;
    MaterialSearchBar materialSearchBar;
    List<String> booknameList = new ArrayList<>();
    List<String> suggestion = new ArrayList<>();
    DBHandler dbHandler;
    DatabaseReference ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studenthomepage);

        //initialize database
        dbHandler = new DBHandler(this,null,null,1);

        logoutbutton = findViewById(R.id.studentlogoutbutton);
        homebutton = findViewById(R.id.studenthomebutton);
        profilebutton = findViewById(R.id.studentprofilebutton);
        viewbutton = findViewById(R.id.viewborrowicon);
        overduebutton = findViewById(R.id.overdueicon);

        materialSearchBar = findViewById(R.id.studentsearch);

        rv = findViewById(R.id.studentRV);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        ref = FirebaseDatabase.getInstance().getReference();
        //search bar setup
        materialSearchBar.setHint("Search");
        materialSearchBar.setCardViewElevation(10);
        loadSuggestList(booknameList);

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for(String search:suggestion){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                    materialSearchBar.setLastSuggestions(suggest);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter = new Searchbookadapter(StudentHomePage.this, new ArrayList<Book>());
                rv.setAdapter(adapter);
                ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot child : snapshot.getChildren()) {
                            String isbn = (String) child.child("isbn").getValue();
                            String bookname = (String) child.child("bookname").getValue();
                            String status = (String) child.child("status").getValue();
                            Book book = new Book(isbn, bookname, status);
                            adapter.books.add(book);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled){
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                suggestionList(text.toString(), booknameList);
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        adapter = new Searchbookadapter(this,new ArrayList<Book>());
        rv.setAdapter(adapter);
        ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()) {

                    String isbn = (String) child.child("isbn").getValue();
                    String bookname = (String) child.child("bookname").getValue();
                    String status = (String) child.child("status").getValue();
                    Book book = new Book(isbn, bookname, status);
                    adapter.books.add(book);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //this is to allow the user to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(StudentHomePage.this, MainActivity.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the user to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(StudentHomePage.this, ProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the user to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(StudentHomePage.this, StudentHomePage.class);
                startActivity(homepage);
            }
        });

        //this is to redirect the user to the view borrowed books page
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(StudentHomePage.this, ViewBorrow.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the user to the overdue page
        overduebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overduepage = new Intent(StudentHomePage.this, OverDueLoan.class);
                startActivity(overduepage);
            }
        });

    }

    private void loadSuggestList(final List<String> nameList){
        ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                suggestion = nameList;
                materialSearchBar.setLastSuggestions(suggestion);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void startSearch(final String text){
        ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this creates a new book list
                List<Book> bookList = new ArrayList<>();
                for(DataSnapshot child : snapshot.getChildren()) {
                    String name = (String) child.child("bookname").getValue();
                    //this validates whether the text equals the name
                    if(name != null && name.toLowerCase().contains(text.toLowerCase())){
                        String isbn = (String) child.child("isbn").getValue();
                        String status = (String) child.child("status").getValue();
                        //this creates a new book object
                        Book book = new Book(name, isbn, status);
                        bookList.add(book);
                    }
                }
                adapter = new Searchbookadapter(StudentHomePage.this, bookList);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void suggestionList(final String text, final List<String> nameList){
        ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()) {
                    String name = (String) child.child("bookname").getValue();
                    //this validates whether the text equals the name
                    if(name != null && name.toLowerCase().contains(text.toLowerCase())){
                        nameList.add(name.toLowerCase());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
