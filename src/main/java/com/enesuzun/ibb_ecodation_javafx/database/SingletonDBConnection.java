package com.enesuzun.ibb_ecodation_javafx.database;


import com.enesuzun.ibb_ecodation_javafx.utils.SpecialColor;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

//bu yapı ile database'e bağlantı kurmak isteniyor
public class SingletonDBConnection {
    //Fields
    //database'e bağlanmak istiyorsak 3 tane yapı vardır bunlar üzerinden devam ederiz
    //singelton design pattern yapısında private çok kullanılır

    //database information data
    private static final String URL="jdbc:h2:./h2db/user_management"+ "AUTO_SERVER=TRUE";
    private static final String USERNAME="sa";
    private static final String PASSWORD="";

    //Parametresiz constructer (NOT : SİNGELTON YAPISI İÇİN PRİVATE OLMASI GEREKLİDİR)
    //EĞER BİR CONSTRUCTERDA PRİVATE KULLANIYORSAK O CLASS'IN DIŞARIDAN NESNE YARATMASINI ENGELLEMEK İSTİYORUZSUR SOLİD 1
    private SingletonDBConnection(){

    }

    //Database Connection
    private static Connection connection;

    //Method
    public static Connection getConnection() throws SQLException {
        if(connection==null || connection.isClosed()){
            try{
                connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
                System.out.println(SpecialColor.GREEN +"VERİTABANI BAĞLANTISI BASARILI"+SpecialColor.RESET);
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
                System.out.println(SpecialColor.RED +"VERİTABANI BAĞLANTISI BASARISIZ"+SpecialColor.RESET);
                throw new RuntimeException("VERİTABANI BAĞLANTISI BASARISIZ");
            }
        }
        return connection;
    }



}//END CLASS
