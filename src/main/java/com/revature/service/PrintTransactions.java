package com.revature.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.revature.controller.Controller;

public class PrintTransactions {

  public static void start(int accountNumber) {
    int transactionID = 0;
    String description = "";
    double balanceAdjustment = 0;
    String timestamp = "";

    try {
      PreparedStatement stmt = Controller.connection
          .prepareStatement("SELECT * FROM list_transactions WHERE account_number = ?;");
      stmt.setInt(1, accountNumber);
      stmt.execute();
      ResultSet rs = stmt.getResultSet();
      while (rs.next()) {
        transactionID = (rs.getInt(1) + 2871);
        description = rs.getString(3);
        balanceAdjustment = rs.getDouble(4);
        timestamp = rs.getTimestamp(5).toString();
        System.out.println("TransactionID: " + transactionID + "- Description: " + description
            + "- balance adjustment: $" + balanceAdjustment + "- Time: " + timestamp);
      }
      System.out.println("End of list, enter anything to return to dashboard");
      @SuppressWarnings("unused")
      String inputedString = ScanForUserInput.getUserInputStream();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}
