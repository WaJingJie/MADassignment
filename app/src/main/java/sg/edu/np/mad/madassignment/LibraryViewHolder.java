package sg.edu.np.mad.madassignment;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class LibraryViewHolder extends RecyclerView.ViewHolder {
    TextView isbnview;
    TextView nameview;
    TextView dateborrowedview;
    TextView duedateview;

    TextView bookisbn;
    TextView bookname;
    TextView borrowdate;
    TextView duedate;
    ConstraintLayout frame;

    View view;

    public LibraryViewHolder(View v) {
        super(v);

        isbnview = v.findViewById(R.id.isbnview);
        nameview = v.findViewById(R.id.nameview);
        dateborrowedview = v.findViewById(R.id.dateborrowedview);
        duedateview = v.findViewById(R.id.duedateview);

        bookisbn = v.findViewById(R.id.bkisbn);
        bookname = v.findViewById(R.id.bkname);
        borrowdate = v.findViewById(R.id.borrowdate);
        duedate = v.findViewById(R.id.duedate);
        frame = v.findViewById(R.id.frame);

        view = v;
    }

}
