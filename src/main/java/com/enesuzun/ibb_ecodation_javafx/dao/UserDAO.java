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

/// DAO (Data Access Object)
/// Açılımı: Data Access Object
/// Tanım: Veritabanı işlemlerini gerçekleştiren bir tasarım desenidir. CRUD (Create, Read, Update, Delete) işlemlerini soyutlamak için kullanılır.
/// Kullanım Amacı: İş mantığı ve veri erişim işlemlerini birbirinden ayırarak kodun daha okunabilir ve yönetilebilir olmasını sağlar.

/*
* ResultSet, Java'nın JDBC (Java Database Connectivity) API'sinde
* kullanılan ve bir veritabanı sorgusunun sonuçlarını temsil eden bir arayüzdür.
* SELECT sorgularından dönen sonuçları işlerken kullanılır.
* */

/*
* PreparedStatement Java'nın JDBC (Java Database Connectivity) API'sinde kullanılan
* bir arayüzdür ve veritabanı işlemlerini gerçekleştirmeyi sağlar.
* Temel amacı SQL enjeksiyonu riskini azaltmak ve tekrar eden sorguların performansını iyileştirmektir.
* */

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
    ///Find by id
    @Override
    public Optional<UserDTO> findById(int id) {
        String sql="SELECT * FROM users WHERE id=?";
        return selectSingle(sql,id);
    }

    /// Find by name
    @Override
    public Optional<UserDTO> findByName(String name) {
        //String sql="SELECT * FROM users WHERE username=?";
        String sql="SELECT * FROM users WHERE email=?";
        return selectSingle(sql,name);
    }
    ///UPDATE
    @Override
    public Optional<UserDTO> update(int id, UserDTO userDTO) {
        Optional<UserDTO> optionalDelete=findById(id);
        if(optionalDelete.isPresent()) {//Veri varsa 1

            //update yapılacaksa önce bulunmalı sonra verilen degerlerle değiştirilemli
            String sql = "UPDATE users SET username=?,password=?,email=? WHERE id=?";//sadece aranan ıd güncellensin diye where id yazdık
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userDTO.getUserName());
                preparedStatement.setString(2, userDTO.getPassword());
                preparedStatement.setString(3, userDTO.getEmail());
                preparedStatement.setInt(4, id);//creatten farklı olarak burada id de güncelleniyor

                //create , delete, update işlemlerinde executeUpdate yapılması gereklidir
                int affectedRows/*etkilenen satır*/ = preparedStatement.executeUpdate();

                //Eğer Güncelleme başarılı ise
                if (affectedRows > 0) {
                    userDTO.setId(id);//güncellenen userDTO için id'yi ekle
                    return Optional.of(userDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
    /// DELETE
    @Override
    public Optional<UserDTO> delete(int id) {
        //silinecek olan once bulunur sonra silinir
        Optional<UserDTO> optionalDelete=findById(id);
        if(optionalDelete.isPresent()){//Veri varsa 1
            String sql="DELETE users WHERE id=?";//sadece SİLMEK istediğimiz id yapısıdır
            try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,id);

                int affectedRows =  preparedStatement.executeUpdate();

                //Eğer silme başarılı ise
                if(affectedRows>0){
                    return optionalDelete;//Bunu neden böyle yaptık bir bak
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        //eğer silinecek olan yoksa boş döndür
        return Optional.empty();
    }

    /// ////////////////////////////////////////////////////////////////////////////////
    //generics metot (list,find)
    //resultSet'ten userDTO oluşturmayı tek bir yardımcı metot ile bu şekilde yapacağız
    @Override
    UserDTO mapToObjectDTO(ResultSet resultSet) throws SQLException{
        return UserDTO.builder()
                .id(resultSet.getInt("id"))
                .userName(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .email(resultSet.getString("email"))
                .build();
    }

    //dizi elemanları gelebilir( değişken , birden fazla olabilir )
    //ID VEYA NAME ile veri çekilince bu ortak metot kullanılacak
    //generics ile tek kayıt döndüren metot
    @Override
    public Optional<UserDTO> selectSingle(String sql, Object... params) {
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {//kaç tane varsa o kadar güncelleyecek
                preparedStatement.setObject((i+1),params[i]);
            }
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return Optional.of(mapToObjectDTO(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    /// ////////////////////////////////////////////////////////////////////////////////
}
