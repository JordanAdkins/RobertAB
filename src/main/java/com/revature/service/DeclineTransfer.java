package com.revature.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;

public class DeclineTransfer {
  
  private static Logger log = Logger.getLogger(DeclineTransfer.class);


  public static boolean run() {
    log.trace("Running Decline Transfer Service");
    System.out.println("You have chosen to decline this transfer");
    try {
      PreparedStatement stmt = Controller.connection.prepareStatement(
          "SELECT * FROM account_numbers WHERE account_number = ?");
      stmt.setInt(1, Controller.CurrentUser.getPendingTransferAccount());
      stmt.execute();
      log.debug("SQL success");
      ResultSet rs = stmt.getResultSet();
      rs.next();
      double currentBalance = rs.getDouble(3);
      currentBalance = (currentBalance + Controller.CurrentUser.getPendingTransferAmount());
      log.debug("current balance saved locally");
      stmt = Controller.connection.prepareStatement(
          "UPDATE account_numbers SET account_balance = ? WHERE account_number = ?");
      stmt.setDouble(1, currentBalance);
      stmt.setInt(2, Controller.CurrentUser.getPendingTransferAccount());
      stmt.execute();
      log.debug("transfered user account updated in database");
      Controller.CurrentUser.setHasPendingTransfer(false);
      Controller.CurrentUser.setPendingTransferAccount(0);
      Controller.CurrentUser.setPendingTransferAmount(0);
      stmt = Controller.connection.prepareStatement(
          "UPDATE user_information SET user_haspendingtransfer = FALSE, "
          + "user_pendingTransfersender = NULL, user_pendingtransferamount = NULL,"
          +  " user_pendingtransferaccount = NULL WHERE user_id = ?;");
      stmt.setInt(1, Controller.CurrentUser.getUserId());
      stmt.execute();
      log.debug("Cleared Pending Transfer from account");
      return true;
    } catch (SQLException e) {
      log.fatal("SQL statement failed to parse. Closing Program to save data");
      System.exit(1);
    }
    return false;
    
  }

}
