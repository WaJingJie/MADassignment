package sg.edu.np.mad.madassignment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryViewHolder> {
    private static final String FILENAME = "LibraryAdapter.java";
    private static final String TAG = "NP Library";
    public List<BorrowData> borrowdata;
    Context context;
    DatabaseReference ref;

    public LibraryAdapter(Context context, List<BorrowData> borrowdata){
        this.context = context;
        this.borrowdata = borrowdata;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loanbook, parent,false);
        return new LibraryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        Log.v(TAG, FILENAME +": Retrieving data from the list");
        ref = FirebaseDatabase.getInstance().getReference();
        //retrieves data from the list
        String isbn = borrowdata.get(position).getISBN();
        String bname = borrowdata.get(position).getMyBookName();
        String borrowd = borrowdata.get(position).getBorrowDate();
        String dued = borrowdata.get(position).getDueDate();
        Log.v(TAG, FILENAME +": Displaying data from the list");
        //display the info from the list to the ui
        holder.bookisbn.setText(isbn);
        holder.bookname.setText(bname);
        holder.borrowdate.setText("Borrow Date: " + borrowd);
        holder.duedate.setText("Due Date: " + dued);
    }

    //This gets the number of items in ISBN list
    @Override
    public int getItemCount() {
        Log.v(TAG, FILENAME +": Getting number of items from ISBN List");
        if (borrowdata != null){
            return borrowdata.size();
        }
        return 0;
    }
}
