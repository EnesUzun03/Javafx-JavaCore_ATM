package com.enesuzun.ibb_ecodation_javafx.dao;

import com.enesuzun.ibb_ecodation_javafx.database.SingletonDBConnection;
import com.enesuzun.ibb_ecodation_javafx.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            preparedStatement.setString( 3,userDTO.getEmail());
            //create , delete, update işlemlerinde executeUpdate yapılması gereklidir
            int affectedRows =  preparedStatement.executeUpdate();

            //Eğer ekleme başarılı ise
            if(affectedRows>0){
                try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){//ResultSet databaseden dönüt almak içindir
                    if(generatedKeys.next()){
                        userDTO.setId(generatedKeys.getInt(1));//otomatik ıd set ediyoruz
                        return Optional.of(userDTO);
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Eğer ekleme basarızsızsa boş veri döndür
        return Optional.empty();
    }


    /// LİST
    @Override
    public Optional<List<UserDTO>> list() {
        List<UserDTO> userDTOList =new ArrayList<>();
        String sql="SELECT * FROM users";
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {

            //ResultSet databaseden dönen verileri almak için kullanılan yapılardır
            //executeQuery
            ResultSet resultSet=preparedStatement.executeQuery(sql);
            //veritabanından gelen verileri almak

            while (resultSet.next()){
                userDTOList.add(UserDTO.builder()
                                .id(resultSet.getInt("id"))
                                .userName(resultSet.getString("username"))
                                .password(resultSet.getString("password"))
                                .email(resultSet.getString("email"))
                        .build()
                );
            }
            return userDTOList.isEmpty()? Optional.empty() :Optional.of(userDTOList);

        }catch (Exception e) {
            e.printStackTrace();
        }
        //Eğer ekleme basarızsızsa boş veri döndür
        return Optional.empty();
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
