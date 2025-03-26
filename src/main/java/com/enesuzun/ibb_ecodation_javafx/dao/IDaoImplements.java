package com.enesuzun.ibb_ecodation_javafx.dao;

import com.enesuzun.ibb_ecodation_javafx.database.SingletonDBConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface IDaoImplements <T> {
    //create
    Optional<T> create(T entity);
    //LİST
    List<T> list();
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
