package sg.edu.np.mad.madassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryViewHolder> {

    ArrayList<String> data;


    public LibraryAdapter(ArrayList<String> inputData)
    {
        data  = inputData;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,null);
        return new LibraryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        String information = data.get(position);
        holder.txt.setText(information);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
