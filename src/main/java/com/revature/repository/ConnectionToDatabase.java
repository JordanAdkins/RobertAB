package com.revature.repository;

import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;

public class ConnectionToDatabase {

  public ConnectionToDatabase() {}

  private static Logger log = Logger.getLogger(ConnectionToDatabase.class);


  public static void establishConnection() {
    try {
      Controller.connection = DriverManager.getConnection(System.getenv("connString"),
          System.getenv("username"), System.getenv("password"));
      log.info("Connection Successful");
    } catch (SQLException e) {
      log.fatal("Failed to connect to Database", e);
      System.exit(1);
    }
  }
}
