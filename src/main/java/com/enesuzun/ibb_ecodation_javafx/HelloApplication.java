package com.enesuzun.ibb_ecodation_javafx;

import com.enesuzun.ibb_ecodation_javafx.database.SingletonDBConnection;
import com.enesuzun.ibb_ecodation_javafx.utils.SpecialColor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloApplication extends Application {
    //proje ilk acıldığında koşacak metot
    @Override
    public void start(Stage stage) throws IOException {
        //PROJE AYAGA KALKARKEN DATABASE (H2DB) ÇALIŞSIN
        initializeDatabase();



        //caused by : java.lang.IllegalStateException : Locationis not set
        //Yukarıdaki hata sayfayıbulamadığı için gelmektedir
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
         */
        /// ////////////////////////
        //Başlangıçta Login ekranı gelsin
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/login.fxml"));
        Parent parent=fxmlLoader.load();
        stage.setTitle("Kullanıcı Yönetimi login sayfası");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    /// //////////////////////////////////////////////////////////////////////////
    /// DATABASE
    // Proje ayağa kalkarken veritabanından örnek veriler eklesin
    // Database Başlangıçtaki değeri
    private void initializeDatabase() {
        try {
            Connection conn = SingletonDBConnection.getInstance().getConnection(); // STATIC BAĞLANTI ALINDI
            Statement stmt = conn.createStatement();

            String createTableSQL = """
                -- User login
                CREATE TABLE IF NOT EXISTS users (
                    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                    username VARCHAR(50) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE
                );
                
                -- Fişler Ekle
                    CREATE TABLE receipts (
                        id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, -- H2 için AUTO_INCREMENT yerine IDENTITY kullanıyoruz.
                        receipt_number VARCHAR(50) NOT NULL UNIQUE,
                        receipt_date DATE NOT NULL,
                        tax_number VARCHAR(20) NOT NULL,
                        company_name VARCHAR(100) NOT NULL,
                        customer_name VARCHAR(100) NOT NULL,
                        description TEXT,
                        created_by VARCHAR(100) NOT NULL,
                        account_code VARCHAR(50) NOT NULL,
                     
                        -- ENUM yerine CHECK constraint ile değerleri sınırlıyoruz
                        receipt_type VARCHAR(20) NOT NULL CHECK (receipt_type IN ('Ödeme', 'Tahsilat', 'Masraf', 'Gelir')),
                        amount DECIMAL(10,2) NOT NULL,
                        vat_rate DECIMAL(5,2) NOT NULL,
                        total_amount DECIMAL(10,2) NOT NULL,
                    
                        -- ENUM yerine CHECK constraint
                        payment_type VARCHAR(20) NOT NULL CHECK (payment_type IN ('Nakit', 'Kredi Kartı', 'Havale', 'Çek')),
                    
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    );   
                """;
            stmt.execute(createTableSQL);

            String insertDataSQL = """
                MERGE INTO users (id, username, password, email)
                KEY(id) VALUES (1, 'EnesUzun', 'root', 'enesuzun@gmail.com');
                """;
            stmt.execute(insertDataSQL);

            System.out.println(SpecialColor.CYAN+"H2DB Veritabanı başarıyla oluşturuldu ve veri eklendi."+SpecialColor.RESET);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /// PSVM
    public static void main(String[] args) {
        launch();
    }
}