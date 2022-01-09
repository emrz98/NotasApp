package com.another.notasapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.another.notasapp.R;
import com.another.notasapp.models.entity.FolderNotes;

import java.util.List;
import java.util.logging.Logger;

public class FolderNotesAdapter extends RecyclerView.Adapter<FolderNotesAdapter.ViewHolder>{

    private List<FolderNotes> folderNotesData;
    public FolderNotesAdapter(List<FolderNotes> dataSet) {
        this.folderNotesData = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folder_notes_item_list, parent, false);

        return new FolderNotesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("BindViewHolder", "Se enlaza la vista de " + this.folderNotesData.get(position).getName());
        holder.getTextView().setText(this.folderNotesData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.folderNotesData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.item_folder_list);
        }
        public TextView getTextView() {
            return textView;
        }
    }



}
