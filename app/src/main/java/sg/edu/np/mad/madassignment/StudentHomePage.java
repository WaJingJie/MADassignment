package sg.edu.np.mad.madassignment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ListViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class StudentHomePage extends AppCompatActivity {
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    Searchbookadapter adapter;

    MaterialSearchBar materialSearchBar;

    List<String> suggestion = new ArrayList<>();
    List<book> booklist = new ArrayList<>();

    DBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studenthomepage);

        //initialize database
        dbHandler = new DBHandler(this,null,null,1);

        materialSearchBar = findViewById(R.id.studentsearch);

        rv = findViewById(R.id.studentRV);
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
        adapter = new Searchbookadapter(this,dbHandler.getBook());
        rv.setAdapter(adapter);

    }

    private void loadSuggestList(){
        suggestion = dbHandler.getBookname();
        materialSearchBar.setLastSuggestions(suggestion);
    }

    private void startSearch(String text){
        adapter = new Searchbookadapter(this, dbHandler.getBookByName(text));
        rv.setAdapter(adapter);
    }
}
