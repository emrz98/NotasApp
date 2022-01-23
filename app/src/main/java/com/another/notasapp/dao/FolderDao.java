package com.another.notasapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.another.notasapp.models.entity.FolderNotes;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface FolderDao {
    @Query("SELECT * FROM FolderNotes")
    List<FolderNotes> getAllFolders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFolder(FolderNotes folderNote);

    @Delete
    void deleteFolder(FolderNotes folderNote);


}
