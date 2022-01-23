package com.another.notasapp.models.repository;

import com.another.notasapp.models.entity.FolderNotes;

import java.util.List;

public interface FolderRepository {
    List<FolderNotes> getAllFolders();
    void insertFolder(FolderNotes folderNotes);
    void deleteFolder(FolderNotes folderNotes);
}
