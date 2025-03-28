package com.enesuzun.ibb_ecodation_javafx.dao;

import com.enesuzun.ibb_ecodation_javafx.database.SingletonDBConnection;
import com.enesuzun.ibb_ecodation_javafx.dto.UserDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface IDaoImplements <T> {
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

    /// /////////////////////////////////////////////////////////
    //generics metot (list,find)
    //resultSet'ten userDTO oluşturmayı tek bir yardımcı metot ile bu şekilde yapacağız
    T mapToObjectDTO(ResultSet resultSet);

    //dizi elemanları gelebilir( değişken , birden fazla olabilir )
    //ID VEYA NAME ile veri çekilince bu ortak metot kullanılacak
    //generics ile tek kayıt döndüren metot
    Optional<T> selectSingle(String sql,Object... params);

    /// ///////////////////////////////////////////////////////
    //gövdeli metot yazılacak(Java 8 ile geliyor

    default Connection iDaoImplementsDatabaseConnection(){
        return SingletonDBConnection.getInstance().getConnection();
    }

}
