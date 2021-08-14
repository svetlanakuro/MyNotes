package com.example.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private NoteSource noteSource;
    private OnItemClickListener listener;

    public ItemAdapter(NoteSource noteSource) {
        this.noteSource = noteSource;
    }

    public void setListener(@NonNull OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        holder.bind(noteSource.getNoteData(position));
    }

    @Override
    public int getItemCount() {
        return noteSource.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private final CheckBox executed;
        private TextView date;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            executed = itemView.findViewById(R.id.executed);
            date = itemView.findViewById(R.id.date);

            title.setOnClickListener(v -> listener.onItemClick(description, getLayoutPosition()));
        }

        public void bind(NoteData noteData) {
            title.setText(noteData.getTitle());
            description.setText(noteData.getDescription());
            executed.setChecked(noteData.isExecuted());
            date.setText(new SimpleDateFormat("dd-MM-yy").format(noteData.getDate()));
        }
    }

    interface OnItemClickListener {
        void onItemClick(@NonNull View view, int position);
    }
}
