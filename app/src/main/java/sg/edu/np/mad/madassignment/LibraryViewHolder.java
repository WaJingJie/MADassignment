package sg.edu.np.mad.madassignment;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class LibraryViewHolder extends RecyclerView.ViewHolder {

    TextView bookisbn;
    TextView bookname;
    TextView borrowdate;
    TextView duedate;
    CardView frame;
    View view;

    public LibraryViewHolder(View v) {
        super(v);
        bookisbn = v.findViewById(R.id.bkisbn);
        bookname = v.findViewById(R.id.bkname);
        borrowdate = v.findViewById(R.id.borrowdate);
        duedate = v.findViewById(R.id.duedate);
        frame = v.findViewById(R.id.frame);

        view = v;
    }

}
