package sg.edu.np.mad.madassignment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LibraryViewHolder extends RecyclerView.ViewHolder {
    TextView txt;

    public LibraryViewHolder(View v) {
        super(v);
        txt = v.findViewById(android.R.id.text1);
    }

}
