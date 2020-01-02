package com.revature.controller.menus;

import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.exception.MenuFailedException;
import com.revature.service.CheckingAccountManager;
import com.revature.service.SavingAccountManager;
import com.revature.service.ScanForUserInput;

public class WithdrawMoneyMenu implements Menu {
  
  private static Logger log = Logger.getLogger(DepositMoneyMenu.class);

  public WithdrawMoneyMenu() {
    log.trace("WithdrawMoneyMenu Created");
  }
  
  public static int getMenuId() {
    return 5;
  }

  @Override
  public void start() {
    log.trace("WithdrawMoneyMenu Started");
    System.out.println("Welcome to the withdraw menu");
    System.out.println("Which account would you like to withdraw money from?");
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
            this.withdrawFromChecking();
          }
          if (inputAsInt == 2 & Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            this.withdrawFromSaving();
          }
          if (inputAsInt == 1 & Controller.CurrentUser.isUserHasCheckingAccount()
              & !Controller.CurrentUser.isUserHasSavingAccount()) {
            this.withdrawFromChecking();
          }
          if (inputAsInt == 1 & !Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            this.withdrawFromSaving();
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
  
  private void withdrawFromChecking() {
    if (CheckingAccountManager.withdraw()) {
      System.out.println("Thank you for your withdraw, returning to the dashboard.");
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
  
  private void withdrawFromSaving() {
    if (SavingAccountManager.withdraw()) {
      System.out.println("Thank you for your withdraw, returning to the dashboard.");
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
