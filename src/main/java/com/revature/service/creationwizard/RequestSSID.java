package com.revature.service.creationwizard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.controller.menus.LoginMenu;
import com.revature.exception.MenuFailedException;
import com.revature.service.AccountCreation;
import com.revature.service.ScanForUserInput;

public class RequestSSID {

  private static Logger log = Logger.getLogger(AccountCreation.class);

  public static int run() {

    int valueToReturn = -1;
    String input;
    String SSIDattempt;
    while (true) {
      SSIDattempt = "";
      System.out.println("Please enter your Social Security or Tax Identification Number");
      log.trace("Requesting SSID");
      input = ScanForUserInput.getUserInputStream();
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
          valueToReturn = Integer.parseInt(SSIDattempt);
        } catch (Exception e) {
          log.fatal(
              "Fatal Error Occured when attempting to parseint from SSIDattempt, returning to start");
          Controller controller = new Controller();
        }

        if (isSSIDUnique(valueToReturn)) {
          log.debug("SSID entered is unique");
          return valueToReturn;
        } else {
          log.debug("SSID entered is NOT unique, moving to login menu");
          System.out.println("The SSID or TIN you have entered is already associated with a user. "
              + "Please login with your existing");
          System.out.println("creditials or select forgot creditials " + "from the login screen");
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
      PreparedStatement stmt = Controller.connection
          .prepareStatement("SELECT * FROM ssid_table WHERE user_ssid = ?");
      stmt.setInt(1, SSID);
      stmt.execute();
      log.debug("SQL statment excecuted with int: " + SSID);
      ResultSet rs = stmt.getResultSet();
      if (rs.next()) {
        return false;
      }
      return true;
    }catch(Exception e) {
      log.fatal("fatal error occured when accessing isSSIDUnique");
      System.exit(1);
    }
    return false;
  }
}
