package com.revature.repository;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.revature.controller.Controller;

public class ConnectionToDatabase {

  public ConnectionToDatabase() {}



  public static boolean establishConnection() {
    try {
      Controller.connection = DriverManager.getConnection(System.getenv("connString"),
          System.getenv("username"), System.getenv("password"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (Controller.connection != null) {
      return true;
    } else {
      return false;
    }
  }
}
