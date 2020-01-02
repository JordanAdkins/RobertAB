package com.revature.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.controller.menus.StartMenu;
import com.revature.exception.MenuFailedException;
import com.revature.service.AccountCreation;

public class UserBuilder {
  private static Logger log = Logger.getLogger(AccountCreation.class);

  public static User build(String userLoginId, String userPassword) {
    log.debug("UserBuilder.build started");
    int userId = 0;
    String userName = "";
    int userQuestionId = 0;
    String userQuestionAnswer = "";
    int userSSID = 0;
    int userCheckingAccountNumber = 0;
    int userSavingAccountNumber = 0;
    boolean userHasSavingAccount = false;
    boolean userHasCheckingAccount = false;
    double userCheckingAccountBalance = 0;
    double userSavingAccountBalance = 0;
    String CheckingCheck = "Checking";
    String SavingCheck = "Saving";

    PreparedStatement stmt;
    try {
      stmt = Controller.connection
          .prepareStatement("SELECT * FROM user_information WHERE user_userid = ?");
      stmt.setString(1, userLoginId);
      log.debug("Attempting to retrieve user information from database, via SQL");
      stmt.execute();
      ResultSet rs = stmt.getResultSet();
      rs.next();
      userId = rs.getInt(1);
      userName = rs.getString(4);
      userQuestionId = rs.getInt(5);
      userQuestionAnswer = rs.getString(6);
      log.debug("Successfully obtained information from user_information table");
      stmt = Controller.connection.prepareStatement("SELECT * FROM ssid_table WHERE userid = ?");
      stmt.setInt(1, userId);
      log.debug("Attempting to retrieve SSID from table");
      stmt.execute();
      rs = stmt.getResultSet();
      rs.next();
      userSSID = rs.getInt(1);
      log.debug("Success");
      log.debug("Attempting to obtain account information");
      stmt =
          Controller.connection.prepareStatement("SELECT * FROM account_numbers WHERE user_id = ?");
      stmt.setInt(1, userId);
      stmt.execute();
      rs = stmt.getResultSet();
      log.debug("result set was found");
      while (rs.next()) {
        if (rs.getString(2).equalsIgnoreCase(CheckingCheck)) {
          log.debug("Checking Account Found, Inserting information");
          userHasCheckingAccount = true;
          userCheckingAccountNumber = rs.getInt(1);
          userCheckingAccountBalance = rs.getDouble(3);
        }
        if (rs.getString(2).equalsIgnoreCase(SavingCheck)) {
          log.debug("Saving Account Found, Inserting information");
          userHasSavingAccount = true;
          userSavingAccountNumber = rs.getInt(1);
          userSavingAccountBalance = rs.getDouble(3);
        }
      } 
      User BuiltUser = new User(userId, userLoginId, userPassword, userName, userQuestionId, userQuestionAnswer,
          userSSID, userCheckingAccountNumber, userSavingAccountNumber, userHasSavingAccount,
          userHasCheckingAccount, userCheckingAccountBalance, userSavingAccountBalance);
      return BuiltUser;
    } catch (SQLException e) {
      log.fatal("failed to obtain information from database, returning to start menu");
      try {
        Controller.moveToMenu(StartMenu.getMenuId());
      } catch (MenuFailedException e1) {
        log.fatal("Failed to locate Menu, closing program to protect data");
        System.exit(1);
      }
    }
    return null;
  }
}
