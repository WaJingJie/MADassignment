package sg.edu.np.mad.madassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class OverdueloanViewHolder extends RecyclerView.ViewHolder{
    public TextView bookname,borrowdate,duedate,isbn,returndate,overdueduration,overduefee;
    View view;
    public OverdueloanViewHolder(View itemView){
        super(itemView);
        bookname = itemView.findViewById(R.id.overduebookname);
        borrowdate = itemView.findViewById(R.id.borrowdate);
        duedate = itemView.findViewById(R.id.overduedate);
        isbn = itemView.findViewById(R.id.overdueisbn);
        returndate = itemView.findViewById(R.id.returndate);
        overdueduration = itemView.findViewById(R.id.duration);
        overduefee = itemView.findViewById(R.id.fee);
    }
}
public class Overdueloandadapter extends RecyclerView.Adapter<OverdueloanViewHolder> {
    private Context context;
    private List<BorrowData> borrowData;

    public Overdueloandadapter(Context context, List<String> data){
        this.context = context;
        this.borrowData = borrowData;
    }
    @Override
    public OverdueloanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.overdueloancv, parent,false);
        return new OverdueloanViewHolder(itemView);
    }
//returndate,overdueduration,overduefee;
    @Override
    public void onBindViewHolder(@NonNull OverdueloanViewHolder holder, int position) {
        String bookname = borrowData.get(position).getMyBookName();
        String isbn = borrowData.get(position).getISBN();
        String borrowdate = borrowData.get(position).getBorrowDate();
        String duedate = borrowData.get(position).getDueDate();
    }

    @Override
    public int getItemCount() {
        return borrowData.size();
    }
}
