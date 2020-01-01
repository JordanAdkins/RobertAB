package com.revature.controller.menus;


import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.exception.MenuFailedException;
import com.revature.service.AccountCreation;

public class CreateAccountMenu implements Menu {
  
  private static Logger log = Logger.getLogger(CreateAccountMenu.class);

  public CreateAccountMenu() {
    log.trace("Account Creation Menu Created");
  }
  
  public static int getMenuId() {
    return 2;
  }

  @Override
  public void start() {
    log.trace("Account Creation Menu Started");
    System.out.println("Welcome to the account creation menu");
    log.debug("Accessing Acount Creation Wizard");
    Controller.CurrentUser = AccountCreation.accountCreationWizard();
    try {
      Controller.moveToMenu(UserDashboardMenu.getMenuId());
    } catch (MenuFailedException e) {
      log.fatal("Menu Not found, closing program to prevent data loss");
      System.exit(1);
    }
  }

  /**
   * This Method is not used for this menu
   */
  @Override
  public int askForInput() {
   return -1;
  }
  /**
   * This Method is not used for this menu
   */
  @Override
  public boolean checkValidInput(String input) {
    return false;
  }
  /**
   * This Method is not used for this menu
   */
  @Override
  public int submitInput(String input) {
    return 0;
  }

}
