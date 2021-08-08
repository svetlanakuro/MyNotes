package com.example.mynotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private String[] dataSource;
//    public static final String TAG = "ItemAdapter";

    public ItemAdapter(String[] dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        holder.getTextView().setText(dataSource[position]);
//        Log.d(TAG, "onBindViewHolder" + position);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
