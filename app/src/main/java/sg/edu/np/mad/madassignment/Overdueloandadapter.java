package sg.edu.np.mad.madassignment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import sg.edu.np.mad.madassignment.utils.DateUtil;

class OverdueloanViewHolder extends RecyclerView.ViewHolder{
    public TextView bookname,borrowdate,duedate,isbn,returndate,overdueduration,overduefee;
    CardView frame;
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
        frame = itemView.findViewById(R.id.frame);
    }
}
public class Overdueloandadapter extends RecyclerView.Adapter<OverdueloanViewHolder> {
    private Context context;
    private List<BorrowData> borrowData;

    public Overdueloandadapter(Context context, List<BorrowData> borrowData){
        this.context = context;
        this.borrowData = borrowData;
    }

    @Override
    public OverdueloanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        holder.bookname.setText(bookname);
        holder.isbn.setText(isbn);
        holder.borrowdate.setText(borrowdate);
        holder.duedate.setText(duedate);
        holder.returndate.setText(duedate);
        Log.e("date format :",""+duedate);
        String currentDate = DateUtil.getCurrentTimeStamp();
        String diff = DateUtil.getDiffBetTwoDate(borrowData.get(position).getDueDate(),currentDate);
        if(diff != null && Integer.parseInt(diff) != 0){
            int totalPrice = Integer.parseInt(diff) * 50;
            holder.overdueduration.setText(diff+" Days Overdue");
            holder.overduefee.setText("$ "+totalPrice);
        }else {
            holder.overdueduration.setText("No Overdue");
            holder.overduefee.setText("$ 0.0");
        }
        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Overduepayment.class);
                intent.putExtra("list", (Serializable) borrowData);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return borrowData.size();
    }
}
