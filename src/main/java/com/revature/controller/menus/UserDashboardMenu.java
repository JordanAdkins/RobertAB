package com.revature.controller.menus;

import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.service.CreateCheckingAccount;
import com.revature.service.CreateSavingAccount;
import com.revature.service.ScanForUserInput;

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
    System.out.println("Welcome to the Dashboard " + Controller.CurrentUser.getUserName());
    if (Controller.CurrentUser.isUserHasCheckingAccount()
        & Controller.CurrentUser.isUserHasSavingAccount()) {
      userHasNoAccounts();
    }
    if (Controller.CurrentUser.isUserHasCheckingAccount()) {
      System.out.println("Your Checking Account ending in "
          + (Controller.CurrentUser.getUserCheckingAccountNumber() - 411450000) + " Has a balance of: "
          + Controller.CurrentUser.getUserCheckingAccountBalance());
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
            Controller.CurrentUser.setUserCheckingAccountNumber(
                CreateCheckingAccount.run(Controller.CurrentUser.getUserId()));
            Controller.CurrentUser.setUserHasCheckingAccount(true);
            System.out.println("Checking Account successfully created. Returning to Dashboard");
            return true;
          }
          if (inputAsInt == 2) {
            Controller.CurrentUser.setUserSavingAccountNumber(
                CreateSavingAccount.run(Controller.CurrentUser.getUserId()));
            Controller.CurrentUser.setUserHasSavingAccount(true);
            System.out.println("Saving Account successfully created. Returning to Dashboard");
            return true;
          }
          if (inputAsInt == 3) {
            Controller.CurrentUser.setUserCheckingAccountNumber(
                CreateCheckingAccount.run(Controller.CurrentUser.getUserId()));
            Controller.CurrentUser.setUserHasCheckingAccount(true);
            Controller.CurrentUser.setUserSavingAccountNumber(
                CreateSavingAccount.run(Controller.CurrentUser.getUserId()));
            Controller.CurrentUser.setUserHasSavingAccount(true);
            System.out.println(
                "Checking Account and Saving Account successfully created. Returning to Dashboard");
            return true;
          }
        } catch (Exception e) {
        }
      }
      System.out.println("Please make a valid Selection");
    }
    return false;
  }

  @Override
  public int askForInput() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean checkValidInput(String input) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int submitInput(String input) {
    // TODO Auto-generated method stub
    return 0;
  }

}
