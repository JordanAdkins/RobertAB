package com.revature.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;

public class CreateSavingAccount {

  private static Logger log = Logger.getLogger(CreateSavingAccount.class);

  public static int run(int userId) {
    log.trace("generating saving account number");
    int savingNumber = (211456600 + userId);
    PreparedStatement stmt;
    try {
      log.debug("Attempting to insert account information into database using SQL");
      stmt = Controller.connection.prepareStatement(
          "INSERT INTO account_numbers(account_number, account_type, account_balance, user_id)" + 
          "VALUES (?, 'Saving', 0, ?);");
      stmt.setInt(1, savingNumber);
      stmt.setInt(2, userId);
      stmt.execute();
      log.debug("Insert Successful");
      return savingNumber;
    } catch (SQLException e) {
      log.fatal("Failed to insert information, closing program to prevent data loss");
      System.exit(1);
    }
    return -1;
  }
}
