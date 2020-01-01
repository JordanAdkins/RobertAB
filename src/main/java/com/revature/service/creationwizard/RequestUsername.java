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
    System.out.println(
        "Please enter your desired Username (Please be aware that Usernames ARE case-sensitive");
    while (true) {
      log.trace("Requesting Username");
      String retrievedString = ScanForUserInput.getUserInputStream();
      log.trace("Input Received");
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
        System.out.println("succcess");
      } catch (SQLException e) {
      }
      log.debug("Username " + retrievedString + " is unique");
      username = retrievedString;
      return username;
    }
  }

}
