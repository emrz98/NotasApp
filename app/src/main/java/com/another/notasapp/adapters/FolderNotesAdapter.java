package com.another.notasapp.adapters;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.another.notasapp.AppDatabase;
import com.another.notasapp.MainActivity;
import com.another.notasapp.R;
import com.another.notasapp.models.entity.FolderNotes;
import com.another.notasapp.models.repository.FolderRepositoryImpl;

import java.util.List;
import java.util.logging.Logger;

public class FolderNotesAdapter extends RecyclerView.Adapter<FolderNotesAdapter.ViewHolder>{

    private List<FolderNotes> folderNotesData;
    private OnItemClickListener onItemClickListener;

    public FolderNotesAdapter(List<FolderNotes> dataSet, OnItemClickListener onItemClickListener) {
        this.folderNotesData = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
        boolean onItemLongClicked(int position);
        void deleteItem(int position);
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        private final TextView textView;
        private int adapterPos;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.item_folder_list);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getLayoutPosition();
                    onItemClickListener.onItemClicked(pos);

                }
            });

    /*          view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getLayoutPosition();
                    onItemClickListener.onItemLongClicked(pos);
                    return true;
                }
            });*/
            view.setOnCreateContextMenuListener(this);
        }
        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem edit = contextMenu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem delete = contextMenu.add(Menu.NONE, 2, 2, "Delete");
            edit.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
            Log.i("Get id view in the holder context menu", String.valueOf(this.getAdapterPosition()));
            adapterPos = this.getAdapterPosition();
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.i("Item context selected", String.valueOf(menuItem.getOrder()));
                if(menuItem.getOrder() == 2){
                    onItemClickListener.deleteItem(adapterPos);
                }
                return false;
            }
        };
    }
}
