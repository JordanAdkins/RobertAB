package com.revature.controller.menus;

import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.exception.MenuFailedException;
import com.revature.service.CheckingAccountManager;
import com.revature.service.SavingAccountManager;
import com.revature.service.ScanForUserInput;

public class DepositMoneyMenu implements Menu {

  private static Logger log = Logger.getLogger(DepositMoneyMenu.class);

  public DepositMoneyMenu() {
    log.trace("DepositMoneyMenu Created");
  }

  public static int getMenuId() {
    return 4;
  }

  @Override
  public void start() {
    log.trace("DepositMoneymenu Started");
    System.out.println("Welcome to the deposit menu");
    System.out.println("Which account would you like to deposit money into?");
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
            this.depositInChecking();
          }
          if (inputAsInt == 2 & Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            this.depositInSaving();
          }
          if (inputAsInt == 1 & Controller.CurrentUser.isUserHasCheckingAccount()
              & !Controller.CurrentUser.isUserHasSavingAccount()) {
            this.depositInChecking();
          }
          if (inputAsInt == 1 & !Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            this.depositInSaving();
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

  private void depositInChecking() {
    if (CheckingAccountManager.deposit()) {
      System.out.println("Thank you for your deposit, returning to the dashboard.");
      try {
        Controller.moveToMenu(UserDashboardMenu.getMenuId());
      } catch (MenuFailedException e) {
        log.fatal("failed to locate Menu, closing program to preserve data");
        System.exit(1);
      }
    }
    log.fatal("depositing failed, closing program to preserver data integrity");
    System.exit(1);
  }

  private void depositInSaving() {
    if (SavingAccountManager.deposit()) {
      System.out.println("Thank you for your deposit, returning to the dashboard.");
      try {
        Controller.moveToMenu(UserDashboardMenu.getMenuId());
      } catch (MenuFailedException e) {
        log.fatal("failed to locate Menu, closing program to preserve data");
        System.exit(1);
      }
    }
    log.fatal("depositing failed, closing program to preserver data integrity");
    System.exit(1);
  }

}
