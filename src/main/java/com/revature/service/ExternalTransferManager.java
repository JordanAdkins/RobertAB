package com.revature.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;



public class ExternalTransferManager {

  private static Logger log = Logger.getLogger(ExternalTransferManager.class);

  public static boolean transferFromChecking() {
    String withdrawAmountString = "";
    String usernameOfReceipt = "";
    int idOfReceipt = 0;
    boolean searchingForUser = true;

    log.trace("asking user how much money would you like to transfer");
    System.out.println("How much money would you like to transfer from your checking account?");
    while (true) {
      String inputedString = ScanForUserInput.getUserInputStream();
      char[] inputAsArray = inputedString.toCharArray();
      int decimalPlaceCounter = 0;
      boolean decimalPlaced = false;
      withdrawAmountString = "";
      for (char x : inputAsArray) {
        if (decimalPlaceCounter == 2) {
          continue;
        }
        // 46 is ASCII for '.'
        if (x == 46) {
          withdrawAmountString = (withdrawAmountString + x);
          decimalPlaced = true;
          continue;
        }
        // 47 and 58 are used as the ASCII for 0-9 lie between them
        if (x > 47 & x < 58) {
          withdrawAmountString = (withdrawAmountString + x);
          if (decimalPlaced) {
            decimalPlaceCounter++;
          }
        }
      }
      try {
        double amountToWithdraw = Double.parseDouble(withdrawAmountString);
        if (amountToWithdraw > 0) {
          if (amountToWithdraw < 5000) {
            while (searchingForUser) {
              log.trace("Asking which user to send to");
              System.out.println("Please enter the Username of the person you wish to transfer to");
              log.trace("input received");
              inputedString = ScanForUserInput.getUserInputStream();
              PreparedStatement stmt = Controller.connection.prepareStatement(
                  "SELECT user_id, user_haspendingtransfer FROM user_information WHERE user_userid = ?;");
              stmt.setString(1, inputedString);
              stmt.execute();
              ResultSet rs = stmt.getResultSet();
              if (rs.next()) {
                if (rs.getBoolean(2)) {
                  System.out.println(
                      "Sorry, this user already has a pending transfer, please try again later");
                  continue;
                }
                searchingForUser = false;
                idOfReceipt = rs.getInt(1);
                usernameOfReceipt = inputedString;
              } else {
                log.debug("username not found in database");
                System.out.println("I am sorry, that username is not registered.");
                continue;
              }
            }
            System.out.println("To confirm, you would like to transfer: $" + amountToWithdraw
                + " FROM your checking account #"
                + Controller.CurrentUser.getUserCheckingAccountNumber());
            System.out.println("TO the accout belonging to " + usernameOfReceipt);
            System.out.println("please enter YES or NO");
            inputedString = ScanForUserInput.getUserInputStream();
            if (inputedString.equalsIgnoreCase("yes") || inputedString.equalsIgnoreCase("y")) {
              if (Controller.CurrentUser.getUserCheckingAccountBalance() >= amountToWithdraw) {
                Controller.CurrentUser.setUserCheckingAccountBalance(
                    (Controller.CurrentUser.getUserCheckingAccountBalance() - amountToWithdraw));
                log.trace("user account balance updated locally");
                PreparedStatement stmt = Controller.connection.prepareStatement(
                    "UPDATE account_numbers SET account_balance = ? WHERE account_number = ?;");
                stmt.setDouble(1, Controller.CurrentUser.getUserCheckingAccountBalance());
                stmt.setInt(2, Controller.CurrentUser.getUserCheckingAccountNumber());
                log.debug("Attempting to update database with new checking balance");
                stmt.execute();
                log.debug("success");
                log.debug("Attempting to update database with transfer information");
                stmt = Controller.connection
                    .prepareStatement("UPDATE user_information SET user_haspendingtransfer = true, "
                        + "user_pendingtransfersender = ?, "
                        + "user_pendingtransferamount = ?, user_pendingtransferaccount = ?"
                        + " WHERE user_id = ?;");
                stmt.setString(1, Controller.CurrentUser.getUserName());
                stmt.setDouble(2, amountToWithdraw);
                stmt.setInt(3, Controller.CurrentUser.getUserCheckingAccountNumber());
                stmt.setInt(4, idOfReceipt);
                stmt.execute();
                log.debug("Success");
                System.out.println(amountToWithdraw + " successfully transfered");
                return true;
              } else {
                log.error("User tried to withdraw more money then account currently has");
                System.out.println("You do not have enough funds to process this transfer");
                System.out.println("Please enter a new amount to transfer");
                continue;
              }
            } else {
              log.trace("User Entered somthing besides yes");
              System.out.println("Please enter a new amount to transfer");
              continue;
            }
          } else {
            log.trace("User inputed too much money");
            System.out.println(
                "I am sorry, but the maximum transfer amount we can process at once is: $5,000");
            System.out.println("Please enter a new amount to transfer");
            continue;
          }
        } else {
          log.trace("user inputed invalid number");
          System.out.println("Please enter a valid amount to transfer");
          continue;
        }
      } catch (Exception e) {
        log.fatal("Failed to parse transfer, Attempting to recover");
        System.out.println(
            "You have entered invalid transfer data, please enter a valid transfer amount");
        continue;
      }
    }
  }

  public static boolean transferFromSaving() {
    String withdrawAmountString = "";
    String usernameOfReceipt = "";
    int idOfReceipt = 0;
    boolean searchingForUser = true;

    log.trace("asking user how much money would you like to transfer");
    System.out.println("How much money would you like to transfer from your saving account?");
    while (true) {
      String inputedString = ScanForUserInput.getUserInputStream();
      char[] inputAsArray = inputedString.toCharArray();
      int decimalPlaceCounter = 0;
      boolean decimalPlaced = false;
      withdrawAmountString = "";
      for (char x : inputAsArray) {
        if (decimalPlaceCounter == 2) {
          continue;
        }
        // 46 is ASCII for '.'
        if (x == 46) {
          withdrawAmountString = (withdrawAmountString + x);
          decimalPlaced = true;
          continue;
        }
        // 47 and 58 are used as the ASCII for 0-9 lie between them
        if (x > 47 & x < 58) {
          withdrawAmountString = (withdrawAmountString + x);
          if (decimalPlaced) {
            decimalPlaceCounter++;
          }
        }
      }
      try {
        double amountToWithdraw = Double.parseDouble(withdrawAmountString);
        if (amountToWithdraw > 0) {
          if (amountToWithdraw < 5000) {
            while (searchingForUser) {
              log.trace("Asking which user to send to");
              System.out.println("Please enter the Username of the person you wish to transfer to");
              log.trace("input received");
              inputedString = ScanForUserInput.getUserInputStream();
              PreparedStatement stmt = Controller.connection.prepareStatement(
                  "SELECT user_id, user_haspendingtransfer FROM user_information WHERE user_userid = ?;");
              stmt.setString(1, inputedString);
              stmt.execute();
              ResultSet rs = stmt.getResultSet();
              if (rs.next()) {
                if (rs.getBoolean(2)) {
                  System.out.println(
                      "Sorry, this user already has a pending transfer, please try again later");
                  continue;
                }
                searchingForUser = false;
                idOfReceipt = rs.getInt(1);
                usernameOfReceipt = inputedString;
              } else {
                log.debug("username not found in database");
                System.out.println("I am sorry, that username is not registered.");
                continue;
              }
            }
            System.out.println("To confirm, you would like to transfer: $" + amountToWithdraw
                + " FROM your saving account #"
                + Controller.CurrentUser.getUserSavingAccountNumber());
            System.out.println("TO the accout belonging to " + usernameOfReceipt);
            System.out.println("please enter YES or NO");
            inputedString = ScanForUserInput.getUserInputStream();
            if (inputedString.equalsIgnoreCase("yes") || inputedString.equalsIgnoreCase("y")) {
              if (Controller.CurrentUser.getUserSavingAccountBalance() >= amountToWithdraw) {
                Controller.CurrentUser.setUserSavingAccountBalance(
                    (Controller.CurrentUser.getUserSavingAccountBalance() - amountToWithdraw));
                log.trace("user account balance updated locally");
                PreparedStatement stmt = Controller.connection.prepareStatement(
                    "UPDATE account_numbers SET account_balance = ? WHERE account_number = ?;");
                stmt.setDouble(1, Controller.CurrentUser.getUserSavingAccountBalance());
                stmt.setInt(2, Controller.CurrentUser.getUserSavingAccountNumber());
                log.debug("Attempting to update database with new saving balance");
                stmt.execute();
                log.debug("success");
                log.debug("Attempting to update database with transfer information");
                stmt = Controller.connection
                    .prepareStatement("UPDATE user_information SET user_haspendingtransfer = true, "
                        + "user_pendingtransfersender = ?, "
                        + "user_pendingtransferamount = ?, user_pendingtransferaccount = ?"
                        + " WHERE user_id = ?;");
                stmt.setString(1, Controller.CurrentUser.getUserName());
                stmt.setDouble(2, amountToWithdraw);
                stmt.setInt(3, Controller.CurrentUser.getUserSavingAccountNumber());
                stmt.setInt(4, idOfReceipt);
                stmt.execute();
                log.debug("Success");
                System.out.println(amountToWithdraw + " successfully transfered");
                return true;
              } else {
                log.error("User tried to withdraw more money then account currently has");
                System.out.println("You do not have enough funds to process this transfer");
                System.out.println("Please enter a new amount to transfer");
                continue;
              }
            } else {
              log.trace("User Entered somthing besides yes");
              System.out.println("Please enter a new amount to transfer");
              continue;
            }
          } else {
            log.trace("User inputed too much money");
            System.out.println(
                "I am sorry, but the maximum transfer amount we can process at once is: $5,000");
            System.out.println("Please enter a new amount to transfer");
            continue;
          }
        } else {
          log.trace("user inputed invalid number");
          System.out.println("Please enter a valid amount to transfer");
          continue;
        }
      } catch (Exception e) {
        log.fatal("Failed to parse transfer, Attempting to recover");
        System.out.println(
            "You have entered invalid transfer data, please enter a valid transfer amount");
        continue;
      }
    }
  }
}
