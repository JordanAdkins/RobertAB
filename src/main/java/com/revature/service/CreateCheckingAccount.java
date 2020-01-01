package com.revature.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;

public class CreateCheckingAccount {
  
  private static Logger log = Logger.getLogger(CreateCheckingAccount.class);

  public static int run(int userId) {
    log.trace("generating checking account number");
    int checkingNumber = (411456600 + userId);
    PreparedStatement stmt;
    try {
      log.debug("Attempting to insert account information into database using SQL");
      stmt = Controller.connection.prepareStatement(
          "INSERT INTO account_numbers(account_number, account_type, account_balance, user_id)" + 
          "VALUES (?, 'Checking', 0, ?);");
      stmt.setInt(1, checkingNumber);
      stmt.setInt(2, userId);
      stmt.execute();
      log.debug("Insert Successful");
      return checkingNumber;
    } catch (SQLException e) {
      log.fatal("Failed to insert information, closing program to prevent data loss");
      System.exit(1);
    }
    return -1;
  }
}
