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

public class StaffHomePage extends AppCompatActivity{
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    Searchbookadapter adapter;

    MaterialSearchBar materialSearchBar;

    List<String> suggestion = new ArrayList<>();
    ImageButton logoutbutton, homebutton, profilebutton, addbutton, deletebutton;
    DatabaseReference ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffhomepage);

        logoutbutton = findViewById(R.id.stafflogoutbutton);
        homebutton = findViewById(R.id.staffhomebutton);
        profilebutton = findViewById(R.id.staffprofilebutton);
        addbutton = findViewById(R.id.addbookicon);
        deletebutton = findViewById(R.id.deletebookicon);

        materialSearchBar = findViewById(R.id.staffsearch);
        ref = FirebaseDatabase.getInstance().getReference();
        rv = findViewById(R.id.staffRV);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        //search bar setup
        materialSearchBar.setHint("Search");
        materialSearchBar.setCardViewElevation(10);
        loadSuggestList();

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
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        adapter = new Searchbookadapter(this, new ArrayList<Book>());
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

        //this is to allow the staff to log out
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(StaffHomePage.this, StaffLoginPage.class);
                startActivity(welcomepage);
            }
        });

        //this is to redirect the staff to the profile page
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(StaffHomePage.this, StaffProfilePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the staff to the home page
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage = new Intent(StaffHomePage.this, StaffHomePage.class);
                startActivity(profilepage);
            }
        });

        //this is to redirect the staff to the add book page
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpage = new Intent(StaffHomePage.this, AddBook.class);
                startActivity(viewpage);
            }
        });

        //this is to redirect the staff to the delete book page
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overduepage = new Intent(StaffHomePage.this, DeleteBook.class);
                startActivity(overduepage);
            }
        });


    }

    private void loadSuggestList(){
        ref.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> booknameList = new ArrayList<>();
                for(DataSnapshot child : snapshot.getChildren()) {
                    String name = (String) child.child("bookname").getValue();
                    booknameList.add(name);
                }
                suggestion = booknameList;
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
                    if(name.equals(text)){
                        String isbn = (String) child.child("isbn").getValue();
                        String status = (String) child.child("status").getValue();
                        //this creates a new book object
                        Book book = new Book(name, isbn, status);
                        bookList.add(book);
                    }
                }
                adapter = new Searchbookadapter(StaffHomePage.this, bookList);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
