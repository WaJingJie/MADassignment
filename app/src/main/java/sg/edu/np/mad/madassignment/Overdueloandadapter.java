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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.edu.np.mad.madassignment.utils.DateUtil;

class OverdueloanViewHolder extends RecyclerView.ViewHolder{
    public TextView bookname,borrowdate,duedate,isbn,returndate,overdueduration,overduefee;
    CardView frame;
    View view;
    public OverdueloanViewHolder(View itemView){
        super(itemView);
        bookname = itemView.findViewById(R.id.overduebookname);
        duedate = itemView.findViewById(R.id.overduedate);
        isbn = itemView.findViewById(R.id.overdueisbn);
        overdueduration = itemView.findViewById(R.id.days);
        overduefee = itemView.findViewById(R.id.fee);
        frame = itemView.findViewById(R.id.frame);
    }
}

public class Overdueloandadapter extends RecyclerView.Adapter<OverdueloanViewHolder> {
    private Context context;
    public List<OverdueLoanData> loanData;

    public Overdueloandadapter(Context context, List<OverdueLoanData> loanData){
        this.context = context;
        this.loanData = loanData;
    }

    @Override
    public OverdueloanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.overdueloancv, parent,false);
        return new OverdueloanViewHolder(itemView);
    }
//returndate,overdueduration,overduefee;
    @Override
    public void onBindViewHolder(@NonNull OverdueloanViewHolder holder, int position) {
        String bookname = loanData.get(position).getMyLoanName();
        String isbn = loanData.get(position).getISBN();
        String duedate = loanData.get(position).getDueDate();
        //Integer days = loanData.get(position).getDays();
        //String fee = String.valueOf(loanData.get(position).getFee());

        holder.bookname.setText(bookname);
        holder.isbn.setText(isbn);
        holder.duedate.setText("DUE DATE: " + duedate);
        //holder.overdueduration.setText(days);
        //holder.overduefee.setText(fee);

        Log.e("date format :",""+duedate);
        //DateFormat sdf = new SimpleDateFormat("M/dd/yy");
        //String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(DateUtil.getCurrentDate());
        Calendar c = Calendar.getInstance();
        Date getDate = c.getTime();
        DateFormat sdf = new SimpleDateFormat("M/dd/yy");
        String currentDate = sdf.format(getDate);
        //duedate.setText(date);

        //String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        //DateFormat sdf = new SimpleDateFormat("M/dd/yy");
        //String currentDate = DateUtil.getCurrentTimeStamp();

        String diff = DateUtil.getDiffBetTwoDate(loanData.get(position).getDueDate(),currentDate);
        if(diff != null && Integer.parseInt(diff) != 0){
            Double totalPrice = Double.parseDouble(diff) * 0.50;
            holder.overdueduration.setText(diff+" Days Overdue");
            holder.overduefee.setText("$ "+totalPrice);
        }else {
            holder.overdueduration.setText("No Overdue");
            holder.overduefee.setText("$ 0.0");
        }

        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Overdueloandadapter.this.context,Overduepayment.class);
                intent.putExtra("list", (Serializable) loanData);
                Overdueloandadapter.this.context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return loanData.size();
    }
}
