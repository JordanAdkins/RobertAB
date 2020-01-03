package com.revature.controller.menus;

import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.service.PrintTransactions;
import com.revature.service.ScanForUserInput;

public class TransactionHistoryMenu implements Menu {

  private static Logger log = Logger.getLogger(TransactionHistoryMenu.class);

  public static int getMenuId() {
    return 9;
  }

  @Override
  public void start() {
    log.trace("TransactionHistory menu Started");
    System.out.println("Welcome to your transaction history!");
    System.out.println("Please select from the options below:");
    if (Controller.CurrentUser.isUserHasCheckingAccount()
        & Controller.CurrentUser.isUserHasSavingAccount()) {
      System.out.println("1:) View Checking Account History");
      System.out.println("2:) View Saving Account History");
    }
    if (Controller.CurrentUser.isUserHasCheckingAccount()
        & !Controller.CurrentUser.isUserHasSavingAccount()) {
      System.out.println("1:) View Checking Account History");
    }
    if (!Controller.CurrentUser.isUserHasCheckingAccount()
        & Controller.CurrentUser.isUserHasSavingAccount()) {
      System.out.println("1:) View Saving Account History");
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
            System.out.println("Transactions from Checking:");
            PrintTransactions.start(Controller.CurrentUser.getUserCheckingAccountNumber());
            System.out.println("returning to your Dashboard");
            Controller.moveToMenu(UserDashboardMenu.getMenuId());
          }

          if (inputAsInt == 2 & Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            System.out.println("Transactions from Saving:");
            PrintTransactions.start(Controller.CurrentUser.getUserSavingAccountNumber());
            System.out.println("returning to your Dashboard");
            Controller.moveToMenu(UserDashboardMenu.getMenuId());
          }

          if (inputAsInt == 1 & Controller.CurrentUser.isUserHasCheckingAccount()
              & !Controller.CurrentUser.isUserHasSavingAccount()) {
            System.out.println("Transactions from Checking:");
            PrintTransactions.start(Controller.CurrentUser.getUserCheckingAccountNumber());
            System.out.println("returning to your Dashboard");
            Controller.moveToMenu(UserDashboardMenu.getMenuId());
          }
          if (inputAsInt == 1 & !Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            System.out.println("Transactions from Saving:");
            PrintTransactions.start(Controller.CurrentUser.getUserSavingAccountNumber());
            System.out.println("returning to your Dashboard");
            Controller.moveToMenu(UserDashboardMenu.getMenuId());
          }
          log.error("Invalid Selection was made, trying again");
          System.out.println("Please make a valid Selection");
        } catch (Exception e) {
          log.fatal("Failed to parse int");
          System.exit(1);
        }
      }
    }
  }
}
