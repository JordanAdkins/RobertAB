package com.revature.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.revature.controller.Controller;

public class LogTransaction {

  public static boolean saveChecking(String description, double balanceChange) {
    PreparedStatement stmt;
    try {
      stmt = Controller.connection.prepareStatement(
  "INSERT INTO list_transactions(account_number,transaction_description,"
  + "balance_adjustment,transaction_time)" + 
      "VALUES (?,?,?,current_timestamp);");
    stmt.setInt(1, Controller.CurrentUser.getUserCheckingAccountNumber());
    stmt.setString(2, description);
    stmt.setDouble(3, balanceChange);
    stmt.execute();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return true;
  }
  public static boolean saveSaving(String description, double balanceChange)  {
    PreparedStatement stmt;
    try {
      stmt = Controller.connection.prepareStatement(
  "INSERT INTO list_transactions(account_number,transaction_description,"
  + "balance_adjustment,transaction_time)" + 
      "VALUES (?,?,?,current_timestamp);");
    stmt.setInt(1, Controller.CurrentUser.getUserSavingAccountNumber());
    stmt.setString(2, description);
    stmt.setDouble(3, balanceChange);
    stmt.execute();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return true;
  }
  public static boolean saveReturn(String description, int accountNumber, double balanceChange)  {
    PreparedStatement stmt;
    try {
      stmt = Controller.connection.prepareStatement(
  "INSERT INTO list_transactions(account_number,transaction_description,"
  + "balance_adjustment,transaction_time)" + 
      "VALUES (?,?,?,current_timestamp);");
    stmt.setInt(1, accountNumber);
    stmt.setString(2, description);
    stmt.setDouble(3, balanceChange);
    stmt.execute();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return true;
  }
}
