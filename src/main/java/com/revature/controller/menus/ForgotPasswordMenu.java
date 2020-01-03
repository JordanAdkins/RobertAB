package com.revature.controller.menus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.exception.MenuFailedException;
import com.revature.service.ScanForUserInput;

public class ForgotPasswordMenu {

  private static Logger log = Logger.getLogger(ForgotPasswordMenu.class);
  
  public static int uniqueID = 0;


  public static void start() {

    String SSIDattempt;
    int SSID = 0;
    System.out.println("Welcome to the forgot password menu");
    while (true) {
      SSIDattempt = "";
      System.out.println("Please enter your Social Security or Tax Identification Number");
      log.trace("Requesting SSID");
      String input = ScanForUserInput.getUserInputStream();
      log.trace("Input Received");
      char[] inputasarray = input.toCharArray();
      for (char x : inputasarray) {
        log.trace("Userinput includes: " + x);
        // These numbers are used to represent the ASCII values of 0-9
        if (x > 47 && x < 58) {
          SSIDattempt = SSIDattempt + x;
        }
      }
      if (SSIDattempt.length() == 9) {
        try {
          SSID = Integer.parseInt(SSIDattempt);
        } catch (Exception e) {
          log.fatal(
              "Fatal Error Occured when attempting to parseint from SSIDattempt, returning to start");
          @SuppressWarnings("unused")
          Controller controller = new Controller();
        }

        if (isSSIDUnique(SSID)) {
          log.debug("SSID entered is unique");
          System.out.println("This SSID is not registered with a user, returning to main menu");
          try {
            Controller.moveToMenu(StartMenu.getMenuId());
          } catch (MenuFailedException e) {
            e.printStackTrace(); 
          }
        } else {
          log.debug("SSID entered is NOT unique, getting info");
          printInfo();
          try {
            Controller.moveToMenu(LoginMenu.getMenuId());
            System.exit(1);
          } catch (MenuFailedException e) {
            log.fatal("Failed to locate Login Menu, closing program with error code 1");
            System.exit(1);
          }
        }

        log.info("Invalid input received, trying again");
        System.out
            .println("The number you have entered is not a valid SSID or TIN. Please try again");
      }
    }
  }

  private static boolean isSSIDUnique(int SSID) {
    try {
      PreparedStatement stmt =
          Controller.connection.prepareStatement("SELECT * FROM ssid_table WHERE user_ssid = ?");
      stmt.setInt(1, SSID);
      stmt.execute();
      log.debug("SQL statment excecuted with int: " + SSID);
      ResultSet rs = stmt.getResultSet();
      if (rs.next()) {
        uniqueID = rs.getInt(2);
        return false;
      }
      return true;
    } catch (Exception e) {
      log.fatal("fatal error occured when accessing isSSIDUnique");
      System.exit(1);
    }
    return false;
  }
  
  public static void printInfo() {
    PreparedStatement stmt;
    try {
      stmt = Controller.connection.prepareStatement("SELECT user_userid, user_password FROM user_information"
          + " WHERE user_id = ?");
      stmt.setInt(1, uniqueID);
      stmt.execute();
      ResultSet rs = stmt.getResultSet();
      rs.next();
      System.out.println("Your username is:" + rs.getString(1));
      System.out.println("Your password is:" + rs.getString(2));
      System.out.println("Press any button to return to the main menu");
      @SuppressWarnings("unused")
      String input = ScanForUserInput.getUserInputStream();
      Controller.moveToMenu(StartMenu.getMenuId());
      
    } catch (SQLException | MenuFailedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

}
