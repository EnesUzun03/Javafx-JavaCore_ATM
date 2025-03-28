package com.enesuzun.ibb_ecodation_javafx.controller;


import com.enesuzun.ibb_ecodation_javafx.dao.IDaoImplements;
import com.enesuzun.ibb_ecodation_javafx.dto.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserController implements IDaoImplements<UserDTO> {

    //injection


    /// /////////////////////////////////////////////////////////////
    /// CRUD İŞLEMLERİ

    ///creat
    @Override
    public Optional<UserDTO> create(UserDTO entity) {
        return Optional.empty();
    }
    ///LİST
    @Override
    public Optional<List<UserDTO>> list() {
        return Optional.empty();
    }
    ///FİND BY NAME
    @Override
    public Optional<UserDTO> findByName(String name) {
        return Optional.empty();
    }
    ///FIND BY ID
    @Override
    public Optional<UserDTO> findById(int id) {
        return Optional.empty();
    }
    ///UPDATE
    @Override
    public Optional<UserDTO> update(int id, UserDTO entity) {
        return Optional.empty();
    }
    ///DELETE
    @Override
    public Optional<UserDTO> delete(int id) {
        return Optional.empty();
    }




    @Override
    public Optional loginUser(String userName, String password) {
        return Optional.empty();
    }
}
