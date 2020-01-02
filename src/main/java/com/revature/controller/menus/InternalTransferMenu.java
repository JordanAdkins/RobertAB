package com.revature.controller.menus;

import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.exception.MenuFailedException;
import com.revature.service.CheckingAccountManager;
import com.revature.service.SavingAccountManager;
import com.revature.service.ScanForUserInput;

public class InternalTransferMenu implements Menu {

  private static Logger log = Logger.getLogger(InternalTransferMenu.class);

  public InternalTransferMenu() {
    log.trace("InternalTransferMenu Created");
  }

  public static int getMenuId() {
    return 6;
  }

  @Override
  public void start() {
    log.trace("InternalTransferMenu Started");
    System.out.println("Welcome to the Internal Transfer menu");
    System.out.println("From which account would you like to withdraw money from?");
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
            this.transferFromChecking();
          }
          if (inputAsInt == 2 & Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            this.transferFromSaving();
          }
          if (inputAsInt == 1 & Controller.CurrentUser.isUserHasCheckingAccount()
              & !Controller.CurrentUser.isUserHasSavingAccount()) {
            this.transferFromChecking();
          }
          if (inputAsInt == 1 & !Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            this.transferFromSaving();
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

  public void transferFromChecking() {

    if (CheckingAccountManager.transferToSaving()) {
      System.out.println("Thank you for your transfer. Moving to the dashboard...");
      try {
        Controller.moveToMenu(UserDashboardMenu.getMenuId());
      } catch (MenuFailedException e) {
        log.error("Failed to move to new menu, closing software to prevent data loss");
        System.exit(1);
      }
    }
  }

  public void transferFromSaving() {

    if (SavingAccountManager.transferToChecking()) {
      System.out.println("Thank you for your transfer. Moving to the dashboard...");
      try {
        Controller.moveToMenu(UserDashboardMenu.getMenuId());
      } catch (MenuFailedException e) {
        log.error("Failed to move to new menu, closing software to prevent data loss");
        System.exit(1);
      }
    }
  }
}
