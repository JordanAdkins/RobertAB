package com.revature.controller.menus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.exception.MenuFailedException;
import com.revature.model.UserBuilder;
import com.revature.service.ScanForUserInput;

public class LoginMenu implements Menu {

  private static Logger log = Logger.getLogger(LoginMenu.class);
  
  private String obtainedPassword;

  public LoginMenu() {
   log.trace("Login Menu Created");
  }
  
  public static int getMenuId() {
    return 1;
  }
  
  private Boolean validateUsername(String username) {
    log.trace("Attempting to validate username");
    PreparedStatement stmt;
    try {
      stmt = Controller.connection.prepareStatement(
          "SELECT * from user_information WHERE user_userid = ?");
      stmt.setString(1, username);
      stmt.execute();
      log.trace("Excecute successfully, checking if existing");
      ResultSet rs = stmt.getResultSet();
      if(rs.next()) {
        obtainedPassword = rs.getString(3);
        return true;
      }
      return false;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return true;
  }

  @Override
  public void start() {
    log.trace("Login Menu Started");
    String username = "";
    String password = "";
    boolean usernameObtained = false;
    boolean passwordObtained = false;
    int passwordAttempts = 4;
    System.out.println("Welcome to the Login Menu. Please Enter your username ");
    while(!usernameObtained) {
      log.trace("Awaiting Input");
    String inputedString = ScanForUserInput.getUserInputStream();
    log.trace("input received");
    if(this.validateUsername(inputedString)) {
      log.trace("input validated");
      usernameObtained = true;
      username = inputedString;
      continue;
    }else {
      log.debug("input could not be validated, trying again");
      System.out.println("The username you have entered does not match any accounts, please try agian");
    }
    }
    System.out.println("Please enter your password");
    while(!passwordObtained) {
      log.trace("Awaiting Input");
     String inputedString = ScanForUserInput.getUserInputStream();
     log.trace("input received");
     if(obtainedPassword.equals(inputedString)) {
       log.trace("input validated");
       password = inputedString;
       passwordObtained = true;
       continue;
     }else {
       passwordAttempts--;
       if(passwordAttempts == 0) {
         log.debug("User is out of password Attempts, returning to menu");
         System.out.println("You have failed password validation too many times, returning to main menu");
         try {
          Controller.moveToMenu(StartMenu.getMenuId());
        } catch (MenuFailedException e) {
          log.fatal("Failed to locate menu, closing program to prevent data loss");
          System.exit(1);
        }
       }
       log.debug("The Wrong password has been entered, trying again");
       System.out.println("The password you have entered is incorrect, please try again (you have "
           + passwordAttempts + " tries remaining)");
     }
    }
    Controller.CurrentUser = UserBuilder.build(username, password);
    try {
      Controller.moveToMenu(UserDashboardMenu.getMenuId());
    } catch (MenuFailedException e) {
      log.fatal("failed to find menu, closing program to preserve data");
      System.exit(1);
    }
  }
}
