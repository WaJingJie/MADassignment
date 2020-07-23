package sg.edu.np.mad.madassignment;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryViewHolder> {
    private static final String FILENAME = "LibraryAdapter.java";
    private static final String TAG = "NP Library";
//    ArrayList<String> isbnList;
//    ArrayList<String> booknameList;
//    ArrayList<String> borrowdateList;
//    ArrayList<String> duedateList;
    private List<BorrowData> borrowdata;
    private Context context;

//    public LibraryAdapter(ArrayList<String> isbnList, ArrayList<String> booknameList, ArrayList<String> borrowdateList, ArrayList<String> duedateList)
//    {
//        this.isbnList = isbnList;
//        this.booknameList = booknameList;
//        this.borrowdateList = borrowdateList;
//        this.duedateList = duedateList;
//    }

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
        //retrieves data from the list
        String isbn = borrowdata.get(position).getISBN();
        String bname = borrowdata.get(position).getMyBookName();
        String borrowd = borrowdata.get(position).getBorrowDate();
        String dued = borrowdata.get(position).getDueDate();
        Log.v(TAG, FILENAME +": Displaying data from the list");
        //display the info from the list to the ui
        holder.bookisbn.setText(isbn);
        holder.bookname.setText(bname);
        holder.borrowdate.setText(borrowd);
        holder.duedate.setText(dued);
    }

    ;
    //This gets the number of items in ISBN list
    @Override
    public int getItemCount() {
        Log.v(TAG, FILENAME +": Getting number of items from ISBN List");
        return borrowdata.size();

    }
}
