package com.another.notasapp.models.repository;

import com.another.notasapp.dao.FolderDao;
import com.another.notasapp.models.entity.FolderNotes;

import java.util.List;

public class FolderRepositoryImpl implements FolderRepository{
    FolderDao folderDao;

    public FolderRepositoryImpl(FolderDao folderDao){
        this.folderDao = folderDao;
    }
    @Override
    public List<FolderNotes> getAllFolders() {
        return folderDao.getAllFolders();
    }

    @Override
    public void insertFolder(FolderNotes folderNotes) {
        folderDao.insertFolder(folderNotes);
    }

    @Override
    public void deleteFolder(FolderNotes folderNotes) {
        folderDao.deleteFolder(folderNotes);
    }
}
