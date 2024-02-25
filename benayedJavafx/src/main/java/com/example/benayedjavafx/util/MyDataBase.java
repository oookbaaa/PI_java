package com.example.benayedjavafx.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
   private final String URL = "jdbc:mysql://localhost:3306/formation";
   private final String USER = "root";
   private final String PSW = "";
   private Connection connection ;
   private static MyDataBase instance;
   private MyDataBase(){
       try {
           connection = DriverManager.getConnection(URL, USER, PSW);
           System.out.println("connected");
       } catch (SQLException e){
           System.out.println(e.getMessage());
       }

   }
   public static MyDataBase getInstance(){
       if (instance == null)
           instance = new MyDataBase();
       return instance;
   }

   public Connection getConnection() {
       return connection;
   }
}
