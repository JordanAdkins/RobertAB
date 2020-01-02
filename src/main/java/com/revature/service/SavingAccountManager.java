package com.revature.service;

import java.sql.PreparedStatement;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;

public class SavingAccountManager {

  private static Logger log = Logger.getLogger(SavingAccountManager.class);

  public static boolean deposit() {
    String depositAmountString = "";
    log.trace("asking user how much money to deposit");
    System.out.println("How much money would you like to deposit?");
    while (true) {
      String inputedString = ScanForUserInput.getUserInputStream();
      char[] inputAsArray = inputedString.toCharArray();
      int decimalPlaceCounter = 0;
      boolean decimalPlaced = false;
      depositAmountString = "";
      for (char x : inputAsArray) {
        if (decimalPlaceCounter == 2) {
          continue;
        }
        // 46 is ASCII for '.'
        if (x == 46) {
          depositAmountString = (depositAmountString + x);
          decimalPlaced = true;
          continue;
        }
        // 47 and 58 are used as the ASCII for 0-9 lie between them
        if (x > 47 && x < 58) {
          depositAmountString = (depositAmountString + x);
          if (decimalPlaced) {
            decimalPlaceCounter++;
          }
        }
      }
      try {
        double amountToDeposit = Double.parseDouble(depositAmountString);
        if (amountToDeposit > 0) {
          if (amountToDeposit < 50000) {
            System.out.println("To confirm, you would like to deposit: $" + amountToDeposit
                + " into your Saving account #"
                + Controller.CurrentUser.getUserSavingAccountNumber());
            System.out.println("please enter YES or NO");
            inputedString = ScanForUserInput.getUserInputStream();
            if (inputedString.equalsIgnoreCase("yes") || inputedString.equalsIgnoreCase("y")) {
              Controller.CurrentUser.setUserSavingAccountBalance(
                  (Controller.CurrentUser.getUserSavingAccountBalance() + amountToDeposit));
              log.trace("user account balance updated locally");
              PreparedStatement stmt = Controller.connection.prepareStatement(
                  "UPDATE account_numbers SET account_balance = ? WHERE account_number = ?;");
              stmt.setDouble(1, Controller.CurrentUser.getUserSavingAccountBalance());
              stmt.setInt(2, Controller.CurrentUser.getUserSavingAccountNumber());
              log.debug("Attempting to update database with new balance");
              stmt.execute();
              System.out.println(amountToDeposit + " successfully deposited");
              return true;
            } else {
              log.trace("User Entered somthing besides yes");
              System.out.println("Please enter a new amount to deposit");
              continue;
            }
          } else {
            log.trace("User inputed too much money");
            System.out.println(
                "I am sorry, but the maximum deposit size we can accept at once is: $50,000");
            System.out.println("Please enter a new amount to deposit");
            continue;
          }
        } else {
          log.trace("user inputed invalid number");
          System.out.println("Please enter a valid amount to deposit");
          continue;
        }
      } catch (Exception e) {
        log.fatal("Failed to parse deposit, Attempting to recover");
        System.out
            .println("You have entered invalid deposit data, please enter a valid deposit amount");
        continue;
      }
    }
  }



  public static boolean withdraw() {
    String withdrawAmountString = "";
    log.trace("asking user how much money to withdraw");
    System.out.println("How much money would you like to withdraw?");
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
            System.out.println("To confirm, you would like to withdraw: $" + amountToWithdraw
                + " from your saving account #"
                + Controller.CurrentUser.getUserSavingAccountNumber());
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
                log.debug("Attempting to update database with new balance");
                stmt.execute();
                System.out.println(amountToWithdraw + " successfully withdrawn");
                return true;
              } else {
                log.error("User tried to withdraw more money then account currently has");
                System.out.println("You do not have enough funds to process this withdraw");
                System.out.println("Please enter a new amount to Withdraw");
                continue;
              }
            } else {
              log.trace("User Entered somthing besides yes");
              System.out.println("Please enter a new amount to withdraw");
              continue;
            }
          } else {
            log.trace("User inputed too much money");
            System.out.println(
                "I am sorry, but the maximum withdraw size we can accept at once is: $5,000");
            System.out.println("Please enter a new amount to withdraw");
            continue;
          }
        } else {
          log.trace("user inputed invalid number");
          System.out.println("Please enter a valid amount to withdraw");
          continue;
        }
      } catch (Exception e) {
        log.fatal("Failed to parse withdraw, Attempting to recover");
        System.out.println(
            "You have entered invalid withdraw data, please enter a valid withdraw amount");
        continue;
      }
    }
  }

  public static boolean transferToChecking() {
    String withdrawAmountString = "";
    log.trace("asking user how much money would you like to transfer");
    System.out.println("How much money would you like to transfer to your checking account?");
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
            System.out.println("To confirm, you would like to transfer: $" + amountToWithdraw
                + " FROM your Saving account #"
                + Controller.CurrentUser.getUserSavingAccountNumber());
            System.out.println(
                "TO your Checking account #" + Controller.CurrentUser.getUserCheckingAccountNumber());
            System.out.println("please enter YES or NO");
            inputedString = ScanForUserInput.getUserInputStream();
            if (inputedString.equalsIgnoreCase("yes") || inputedString.equalsIgnoreCase("y")) {
              if (Controller.CurrentUser.getUserSavingAccountBalance() >= amountToWithdraw) {
                Controller.CurrentUser.setUserSavingAccountBalance(
                    (Controller.CurrentUser.getUserSavingAccountBalance() - amountToWithdraw));
                Controller.CurrentUser.setUserCheckingAccountBalance(
                    (Controller.CurrentUser.getUserCheckingAccountBalance() + amountToWithdraw));
                log.trace("user account balance updated locally");
                PreparedStatement stmt = Controller.connection.prepareStatement(
                    "UPDATE account_numbers SET account_balance = ? WHERE account_number = ?;");
                stmt.setDouble(1, Controller.CurrentUser.getUserCheckingAccountBalance());
                stmt.setInt(2, Controller.CurrentUser.getUserCheckingAccountNumber());
                log.debug("Attempting to update database with new checking balance");
                stmt.execute();
                stmt = Controller.connection.prepareStatement(
                    "UPDATE account_numbers SET account_balance = ? WHERE account_number = ?;");
                stmt.setDouble(1, Controller.CurrentUser.getUserSavingAccountBalance());
                stmt.setInt(2, Controller.CurrentUser.getUserSavingAccountNumber());
                log.debug("Attempting to update database with new Saving balance");
                stmt.execute();
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

