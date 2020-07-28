package sg.edu.np.mad.madassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder{
    public TextView id,isbn, bookname, status;
    View view;
    public SearchViewHolder(View itemView){
        super(itemView);
        isbn = itemView.findViewById(R.id.isbn);
        bookname = itemView.findViewById(R.id.bookname);
        status = itemView.findViewById(R.id.status);
        view = itemView;
    }
}
public class Searchbookadapter extends RecyclerView.Adapter<SearchViewHolder>{
    private Context context;
    public List<Book> books;

    public Searchbookadapter(Context context, List<Book> books){
        this.context = context;
        this.books = books;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.studenthompagerv, parent,false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, int position) {
        String isbn = books.get(position).getIsbn();
        String bookname = books.get(position).getBookname();
        String status = books.get(position).getStatus();

        holder.isbn.setText(isbn);
        holder.bookname.setText(bookname);
        holder.status.setText(status);

    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
