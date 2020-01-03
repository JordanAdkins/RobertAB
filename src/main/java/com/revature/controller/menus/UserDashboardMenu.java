package com.revature.controller.menus;

import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.service.CreateCheckingAccount;
import com.revature.service.CreateSavingAccount;
import com.revature.service.ScanForUserInput;
import com.revature.service.TOSAuthentication;

public class UserDashboardMenu implements Menu {

  private static Logger log = Logger.getLogger(UserDashboardMenu.class);

  public UserDashboardMenu() {
    log.trace("UserDashBoardMenu created");
  }

  public static int getMenuId() {
    return 3;
  }

  @Override
  public void start() {
    log.trace("UserDashBoardMenu started");
    System.out.println("Dashboard Loading");
    try {
      for (int i = 0; i < 8; ++i) {
        Thread.sleep(500);
        System.out.print(".");
      }
    } catch (InterruptedException e1) {
      log.debug("failed to sleep");
    }
    log.trace("'Clearing' the screen");
    for (int i = 0; i < 30; ++i)
      System.out.println();
    System.out.println("Welcome to your Dashboard, " + Controller.CurrentUser.getUserName() + ",");
    if (!Controller.CurrentUser.isUserHasCheckingAccount()
        & !Controller.CurrentUser.isUserHasSavingAccount()) {
      userHasNoAccounts();
    }
    if (Controller.CurrentUser.isUserHasCheckingAccount()) {
      System.out.println("Your Checking Account ending in "
          + (Controller.CurrentUser.getUserCheckingAccountNumber() - 411450000)
          + " Has a balance of: " + Controller.CurrentUser.getUserCheckingAccountBalance());
    }
    if (Controller.CurrentUser.isUserHasSavingAccount()) {
      System.out.println("Your Saving Account ending in "
          + (Controller.CurrentUser.getUserSavingAccountNumber() - 211450000)
          + " Has a balance of: " + Controller.CurrentUser.getUserSavingAccountBalance());
    }
    if (Controller.CurrentUser.isHasPendingTransfer()) {
      System.out.println("You have a transfer Pending from "
          + Controller.CurrentUser.getPendingTransferSenderName());
    }
    System.out.println("What would you like to do?");
    System.out.println("0:) Logout");
    System.out.println("1:) Deposit Money");
    System.out.println("2:) Withdraw Money");
    System.out.println("3:) See my Transaction List");
    System.out.println("4:) Transfer Money to another user");
    if (Controller.CurrentUser.isUserHasCheckingAccount()
        & Controller.CurrentUser.isUserHasSavingAccount()) {
      System.out.println("5:) Transfer Money between my accounts");
    }
    if (Controller.CurrentUser.isUserHasCheckingAccount()
        & !Controller.CurrentUser.isUserHasSavingAccount()) {
      System.out.println("5:) Create a Saving Account");
    }
    if (!Controller.CurrentUser.isUserHasCheckingAccount()
        & Controller.CurrentUser.isUserHasSavingAccount()) {
      System.out.println("5:) Create a Checking Account");
    }
    if (Controller.CurrentUser.isHasPendingTransfer()) {
      System.out.println("6:) Manage Pending Transfer");
    }
    boolean lookingForInput = true;
    while (lookingForInput) {
      log.trace("Awaiting Input");
      String inputedString = ScanForUserInput.getUserInputStream();
      log.trace("Input received");
      if (inputedString.length() == 1) {
        try {
          int inputAsInt = Integer.parseInt(inputedString);
          if (inputAsInt == 1) {
            Controller.moveToMenu(DepositMoneyMenu.getMenuId());
          }
          if (inputAsInt == 2) {
            Controller.moveToMenu(WithdrawMoneyMenu.getMenuId());
          }
          if (inputAsInt == 3) {
          }
          if (inputAsInt == 4) {
            Controller.moveToMenu(ExternalAccountsTransferMenu.getMenuId());
          }
          if (inputAsInt == 5 & Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            Controller.moveToMenu(InternalTransferMenu.getMenuId());
          }
          if (inputAsInt == 5 & Controller.CurrentUser.isUserHasCheckingAccount()
              & !Controller.CurrentUser.isUserHasSavingAccount()) {
            log.debug("User has selected 5: Create saving");
            Controller.CurrentUser.setUserSavingAccountNumber(
                CreateSavingAccount.run(Controller.CurrentUser.getUserId()));
            Controller.CurrentUser.setUserHasSavingAccount(true);
            System.out.println("Saving Account successfully created. Returning to Dashboard");
            this.start();
          }
          if (inputAsInt == 5 & !Controller.CurrentUser.isUserHasCheckingAccount()
              & Controller.CurrentUser.isUserHasSavingAccount()) {
            log.debug("User has selected 5: Create Checking");
            Controller.CurrentUser.setUserCheckingAccountNumber(
                CreateCheckingAccount.run(Controller.CurrentUser.getUserId()));
            Controller.CurrentUser.setUserHasCheckingAccount(true);
            System.out.println("Checking Account successfully created. Returning to Dashboard");
            this.start();
          }
          if (inputAsInt == 6 & Controller.CurrentUser.isHasPendingTransfer()) {
            
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

  private boolean userHasNoAccounts() {
    log.trace("user has no checking or saving account, asking user for next step");
    System.out.println("You do not currently have a checking or saving account");
    System.out.println("Which would you like to create now?");
    System.out.println("1:) Checking Account");
    System.out.println("2:) Saving Account");
    System.out.println("3:) Both");
    boolean lookingForInput = true;
    while (lookingForInput) {
      log.trace("Awaiting Input");
      String inputedString = ScanForUserInput.getUserInputStream();
      log.trace("Input received");
      if (inputedString.length() == 1) {
        try {
          int inputAsInt = Integer.parseInt(inputedString);
          if (inputAsInt == 1) {
            if (TOSAuthentication.getTOSAgreement()) {
              Controller.CurrentUser.setUserCheckingAccountNumber(
                  CreateCheckingAccount.run(Controller.CurrentUser.getUserId()));
              Controller.CurrentUser.setUserHasCheckingAccount(true);
              System.out.println("Checking Account successfully created. Returning to Dashboard");
              return true;
            } else {
              Controller.moveToMenu(UserDashboardMenu.getMenuId());
            }
          }
          if (inputAsInt == 2) {
            if (TOSAuthentication.getTOSAgreement()) {
              Controller.CurrentUser.setUserSavingAccountNumber(
                  CreateSavingAccount.run(Controller.CurrentUser.getUserId()));
              Controller.CurrentUser.setUserHasSavingAccount(true);
              System.out.println("Saving Account successfully created. Returning to Dashboard");
              return true;
            } else {
              Controller.moveToMenu(UserDashboardMenu.getMenuId());
            }
          }
          if (inputAsInt == 3) {
            if (TOSAuthentication.getTOSAgreement()) {
              Controller.CurrentUser.setUserCheckingAccountNumber(
                  CreateCheckingAccount.run(Controller.CurrentUser.getUserId()));
              Controller.CurrentUser.setUserHasCheckingAccount(true);
              Controller.CurrentUser.setUserSavingAccountNumber(
                  CreateSavingAccount.run(Controller.CurrentUser.getUserId()));
              Controller.CurrentUser.setUserHasSavingAccount(true);
              System.out.println(
                  "Checking Account and Saving Account successfully created. Returning to Dashboard");
              return true;
            } else {
              Controller.moveToMenu(UserDashboardMenu.getMenuId());
            }
          }
        } catch (Exception e) {
        }
      }
      System.out.println("Please make a valid Selection");
    }
    return false;
  }
}
