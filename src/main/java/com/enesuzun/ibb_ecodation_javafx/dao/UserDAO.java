package com.enesuzun.ibb_ecodation_javafx.dao;

import com.enesuzun.ibb_ecodation_javafx.database.SingletonDBConnection;
import com.enesuzun.ibb_ecodation_javafx.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class UserDAO implements IDaoImplements<UserDTO>{

    //Fields
    private Connection connection;//database ile bağlantı sağlar

    //Parametresiz constructer
    public UserDAO() {
        this.connection= SingletonDBConnection.getInstance().getConnection();
    }


    /// //////////////////////////////////////////////////
    /// CRUD İŞLEMLERİ

    ///Create
    @Override
    public Optional<UserDTO> create(UserDTO userDTO) {
        String sql="INSERT INTO users (username,password,email) VALUES(?,?,?)";
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1,userDTO.getUserName());
            preparedStatement.setString(2,userDTO.getPassword());
            preparedStatement.setString(3,userDTO.getEmail());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<UserDTO> list() {
        return List.of();
    }


    @Override
    public Optional<UserDTO> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> update(int id, UserDTO entity) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> delete(int id) {
        return Optional.empty();
    }
}
