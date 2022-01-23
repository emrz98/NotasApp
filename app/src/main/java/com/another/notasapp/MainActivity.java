package com.another.notasapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.FragmentManager;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.another.notasapp.adapters.FolderNotesAdapter;
import com.another.notasapp.fragments.AddFolderDialog;
import com.another.notasapp.models.entity.FolderNotes;
import com.another.notasapp.models.repository.FolderRepository;
import com.another.notasapp.models.repository.FolderRepositoryImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements AddFolderDialog.OnSaveNewFolder, FolderNotesAdapter.OnItemClickListener{

    private RecyclerView recyclerView;

    private AppDatabase db;
    private FloatingActionButton addButton;
    private RecyclerView.Adapter adapterFolderNotes;
    List<FolderNotes> dummyData;
    private FolderRepository folderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addButton);

        db = AppDatabase.getInstance(this.getApplicationContext());
        folderRepository = new FolderRepositoryImpl(db.folderDao());

        Button nextActivity = findViewById(R.id.nextActivity);
        // Se crea una lista de objetos dummy para hacer pruebas
        dummyData = null;
        try {
            dummyData = getFolders();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Se configura el recycler view
        adapterFolderNotes = new FolderNotesAdapter(dummyData, this);
        recyclerView = findViewById(R.id.foldersRecycler);
        registerForContextMenu(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterFolderNotes);

        // Se notifica el cambio en el adapter
        adapterFolderNotes.notifyDataSetChanged();

        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextAct = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(nextAct);

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AddFolderDialog addFolderDialog = new AddFolderDialog();
                    addFolderDialog.show(getSupportFragmentManager(), "tag");
            }
        });
    }


    public List<FolderNotes> getFolders() throws ExecutionException, InterruptedException {

        Callable< List<FolderNotes>> callable = new Callable<List<FolderNotes>>() {
            @Override
            public  List<FolderNotes> call() throws Exception {
                List<FolderNotes> folders = folderRepository.getAllFolders();
                return folders;
            }
        };
        Future<List<FolderNotes>> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }

    // Guardar nuevo folder ya actualizar los datos en el recycler
    @Override
    public void onFolderSaved(FolderNotes folderNotes) throws ExecutionException, InterruptedException {
        // Guarda el nuevo folder
        folderRepository.insertFolder(folderNotes);

        // Trae todos los folders y toma el ultimo que se agrego
        FolderNotes newFolder = folderRepository.getAllFolders().get(folderRepository.getAllFolders().size() - 1);

        // Inserta el ultimo folder que se agrego a la base de datos en el recyclerview
        dummyData.add(newFolder);

        //printAllDataset(dummyData);

        // Notifica al adapter sobre el cambio en el dataset
        adapterFolderNotes.notifyDataSetChanged();

    }

    void printAllDataset(List<FolderNotes> folderNotes){
        System.out.println(folderNotes.size());
        for (int i = 0; i < folderNotes.size(); i++) {
            System.out.println(folderNotes.get(i).getName());
        }

    }

    // Usado en la generacion del dialog para agregar carpetas
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof AddFolderDialog){
            AddFolderDialog addFolderDialog = (AddFolderDialog) fragment;
            addFolderDialog.setOnSaveNewFolder(this);
        }
    }



    // On click de items del recycler atraves de una interfaz
    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "Short Click" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClicked(int position) {
        Toast.makeText(this, "Long Click" + position, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void deleteItem(int position) {
        folderRepository.deleteFolder(dummyData.get(position));
        dummyData.remove(position);
        adapterFolderNotes.notifyDataSetChanged();
    }

}