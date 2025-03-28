package com.enesuzun.ibb_ecodation_javafx.dao;

import com.enesuzun.ibb_ecodation_javafx.database.SingletonDBConnection;
import com.enesuzun.ibb_ecodation_javafx.dto.UserDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


///
/// Burada interface'i başka bir interface'e implemente etmek için extends kullanılır
/// (Tahminimce aynı tür oldukları için bu keywords seçilmiştir)
///

public interface IDaoImplements <T> extends ILogin {
    //create
    Optional<T> create(T entity);
    //LİST
    Optional<List<T>> list();
    //findBy
    Optional<T> findByName(String name);
    Optional<T> findById(int id);

    //Update
    Optional<T> update(int id, T entity);
    //DELETE
    Optional<T> delete(int id);


    //gövdeli metot yazılacak(Java 8 ile geliyor

    default Connection iDaoImplementsDatabaseConnection(){
        return SingletonDBConnection.getInstance().getConnection();
    }

}
