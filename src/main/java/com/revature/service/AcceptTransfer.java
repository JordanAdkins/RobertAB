package com.revature.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.controller.menus.UserDashboardMenu;
import com.revature.exception.MenuFailedException;

public class AcceptTransfer {

  private static Logger log = Logger.getLogger(AcceptTransfer.class);

  public static void run() {
    log.trace("Running Accept Transfer Service");
    System.out.println("You have chosen to Accept this transfer");
    System.out.println("Which Account would you like to depsit this amount into?");
    if (Controller.CurrentUser.isUserHasCheckingAccount()
        & Controller.CurrentUser.isUserHasSavingAccount()) {
      System.out.println(
          "1:) Checking account # " + Controller.CurrentUser.getUserCheckingAccountNumber());
      System.out
          .println("2:) Saving account # " + Controller.CurrentUser.getUserSavingAccountNumber());
    }
    if (Controller.CurrentUser.isUserHasCheckingAccount()
        & !Controller.CurrentUser.isUserHasSavingAccount()) {
      System.out.println(
          "1:) Checking account # " + Controller.CurrentUser.getUserCheckingAccountNumber());
    }
    if (!Controller.CurrentUser.isUserHasCheckingAccount()
        & Controller.CurrentUser.isUserHasSavingAccount()) {
      System.out
          .println("1:) Saving account # " + Controller.CurrentUser.getUserSavingAccountNumber());
    }
    boolean lookingForInput = true;
    while (lookingForInput) {
      log.trace("Awaiting Input");
      String inputedString = ScanForUserInput.getUserInputStream();
      log.trace("Input received");
      if (inputedString.length() == 1) {
        try {
          int inputAsInt = Integer.parseInt(inputedString);
          if (inputAsInt == 1 & Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            depositInChecking();
          }
          if (inputAsInt == 2 & Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            depositInSaving();
          }
          if (inputAsInt == 1 & Controller.CurrentUser.isUserHasCheckingAccount()
              & !Controller.CurrentUser.isUserHasSavingAccount()) {
            depositInChecking();
          }
          if (inputAsInt == 1 & !Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            depositInSaving();
          }
          log.error("Invalid input received, trying again");
          System.out.println("Please make a valid selection");
        } catch (Exception e) {
          log.fatal("failed to parse int");
          System.exit(1);
        }
      }
    }
  }

  private static void depositInSaving() {
    Controller.CurrentUser
        .setUserSavingAccountBalance((Controller.CurrentUser.getUserSavingAccountBalance()
            + Controller.CurrentUser.getPendingTransferAmount()));
    log.trace("transer finished internally");
    PreparedStatement stmt;
    try {
      stmt = Controller.connection.prepareStatement(
          "UPDATE account_numbers SET account_balance = ? WHERE account_number = ?");
      stmt.setDouble(1, Controller.CurrentUser.getUserSavingAccountBalance());
      stmt.setInt(2, Controller.CurrentUser.getUserSavingAccountNumber());
      stmt.execute();
      log.debug("SQL statment success");
      LogTransaction.saveSaving("External Transfer In", Controller.CurrentUser.getPendingTransferAmount());
      Controller.CurrentUser.setHasPendingTransfer(false);
      Controller.CurrentUser.setPendingTransferAccount(0);
      Controller.CurrentUser.setPendingTransferAmount(0);
      stmt = Controller.connection
          .prepareStatement("UPDATE user_information SET user_haspendingtransfer = FALSE, "
              + "user_pendingTransfersender = NULL, user_pendingtransferamount = NULL,"
              + " user_pendingtransferaccount = NULL WHERE user_id = ?;");
      stmt.setInt(1, Controller.CurrentUser.getUserId());
      stmt.execute();
      log.debug("Cleared Pending Transfer from account");
      System.out.println("Transfer Successful, returning to your Dashboard");
      Controller.moveToMenu(UserDashboardMenu.getMenuId());
    } catch (SQLException | MenuFailedException e) {
      log.fatal("failed to process transfer, closing program to prevent loss");
      System.exit(1);
    }
  }

  private static void depositInChecking() {
    Controller.CurrentUser
    .setUserCheckingAccountBalance((Controller.CurrentUser.getUserCheckingAccountBalance()
        + Controller.CurrentUser.getPendingTransferAmount()));
log.trace("transer finished internally");
PreparedStatement stmt;
try {
  stmt = Controller.connection.prepareStatement(
      "UPDATE account_numbers SET account_balance = ? WHERE account_number = ?");
  stmt.setDouble(1, Controller.CurrentUser.getUserCheckingAccountBalance());
  stmt.setInt(2, Controller.CurrentUser.getUserCheckingAccountNumber());
  stmt.execute();
  log.debug("SQL statment success");
  LogTransaction.saveChecking("External Transfer In", Controller.CurrentUser.getPendingTransferAmount());
  Controller.CurrentUser.setHasPendingTransfer(false);
  Controller.CurrentUser.setPendingTransferAccount(0);
  Controller.CurrentUser.setPendingTransferAmount(0);
  stmt = Controller.connection
      .prepareStatement("UPDATE user_information SET user_haspendingtransfer = FALSE, "
          + "user_pendingTransfersender = NULL, user_pendingtransferamount = NULL,"
          + " user_pendingtransferaccount = NULL WHERE user_id = ?;");
  stmt.setInt(1, Controller.CurrentUser.getUserId());
  stmt.execute();
  log.debug("Cleared Pending Transfer from account");
  System.out.println("Transfer Successful, returning to your Dashboard");
  Controller.moveToMenu(UserDashboardMenu.getMenuId());
} catch (SQLException | MenuFailedException e) {
  log.fatal("failed to process transfer, closing program to prevent loss");
  System.exit(1);
}
  }
}
