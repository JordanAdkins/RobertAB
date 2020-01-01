package com.revature.service.creationwizard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.service.AccountCreation;

public class GenerateUserID {

  private static Logger log = Logger.getLogger(AccountCreation.class);

  public static int run(String username, String password, String name, int questionId,
      String questionAnswer, int SSID) {

    int userID = 0;
    try {
      PreparedStatement stmt = Controller.connection.prepareStatement(
          "INSERT INTO user_information(user_userid, user_password, user_name, user_questionid, user_answer)"
              + "VALUES" + "(?,?,?,?,?);");
      stmt.setString(1, username);
      stmt.setString(2, password);
      stmt.setString(3, name);
      stmt.setInt(4, questionId);
      stmt.setString(5, questionAnswer);
      stmt.execute();
      log.debug("SQL statment executed with user information");
      stmt = Controller.connection.prepareStatement(
          "SELECT user_id FROM user_information WHERE user_userid = ?;");
      stmt.setString(1, username);
      stmt.execute();
      log.debug("SQL statment executed, trying to obtain userID");
      ResultSet rs = stmt.getResultSet();
      rs.next();
      userID = rs.getInt(1);
    } catch (Exception e) {
      log.fatal("failed to insert user information into database, closing program for data safety");
      System.exit(1);
    }
    return userID;
  }
}
