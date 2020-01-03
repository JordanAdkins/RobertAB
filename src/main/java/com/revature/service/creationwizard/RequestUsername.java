package com.revature.service.creationwizard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.service.ScanForUserInput;

public class RequestUsername {

  private static Logger log = Logger.getLogger(RequestUsername.class);

  public static String run() {
    String username = "";
    String usernamePattern = ".{8,20}";
    System.out.println(
        "Please enter your desired Username (Please note that Usernames ARE case-sensitive)");
    System.out.println("Username must be between 8-20 characters");
    while (true) {
      log.trace("Requesting Username");
      String retrievedString = ScanForUserInput.getUserInputStream();
      log.trace("Input Received");
      if (!retrievedString.matches(usernamePattern)) {
        System.out.println(
            "The username you have entered does not fit our requirments, please try again");
        continue;
      }
      try {
        PreparedStatement stmt = Controller.connection
            .prepareStatement("SELECT * FROM user_information WHERE user_userid = ?");
        stmt.setString(1, retrievedString);
        stmt.execute();
        log.debug("SQL statment excecuted with String: " + retrievedString);
        ResultSet rs = stmt.getResultSet();
        if (rs.next()) {
          System.out.println("Username " + retrievedString
              + " is already taken, please enter a different username");
          continue;
        }
      } catch (SQLException e) {
      }
      log.debug("Username " + retrievedString + " is unique");
      username = retrievedString;
      return username;
    }
  }

}
