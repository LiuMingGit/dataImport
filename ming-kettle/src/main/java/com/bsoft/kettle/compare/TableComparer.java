package com.bsoft.kettle.compare;

import com.bsoft.kettle.database.DataBase;
import com.bsoft.kettle.repository.KettleRepository;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;

import java.util.List;

public class TableComparer {
    public List<TableCompareRes> compareTable(DataBase originDataBase,DataBase targetDataBase,String tableName){
        KettleDatabaseRepository repository = null;
        try {
            repository = KettleRepository.getInstance().getRepository();
            RepositoryDirectoryInterface directory = findDirectory(repository,"compareTable");



        } catch (KettleException e) {
            e.printStackTrace();
        }

        return null;
    }

    private RepositoryDirectoryInterface findDirectory(KettleDatabaseRepository repository,String directoryS) throws KettleException {
        RepositoryDirectoryInterface directory = repository.findDirectory("/"+directoryS);
        if(directory == null){
            directory = repository.createRepositoryDirectory(repository.findDirectory("/"),directoryS);
            repository.saveRepositoryDirectory(directory);
        }
        return directory;
    }
}
