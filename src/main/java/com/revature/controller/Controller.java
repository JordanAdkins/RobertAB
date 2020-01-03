package com.revature.controller;

import java.sql.Connection;
import org.apache.log4j.Logger;
import com.revature.controller.menus.CreateAccountMenu;
import com.revature.controller.menus.DepositMoneyMenu;
import com.revature.controller.menus.ExternalAccountsTransferMenu;
import com.revature.controller.menus.InternalTransferMenu;
import com.revature.controller.menus.LoginMenu;
import com.revature.controller.menus.Menu;
import com.revature.controller.menus.PendingTransferManagmentMenu;
import com.revature.controller.menus.StartMenu;
import com.revature.controller.menus.TransactionHistoryMenu;
import com.revature.controller.menus.UserDashboardMenu;
import com.revature.controller.menus.WithdrawMoneyMenu;
import com.revature.exception.MenuFailedException;
import com.revature.model.User;
import com.revature.repository.ConnectionToDatabase;

public class Controller {

  public static Connection connection = null;

  public static Menu currentMenu;
  
  public static User CurrentUser;

  private static Logger log = Logger.getLogger(Controller.class);

  public Controller() {
    super();
    log.info("Controller Established");
    this.establishConnection();
    try {
      Controller.moveToMenu(0);
    } catch (MenuFailedException e) {
      log.error("Failed to Locate Menu", e);
    }
  }

  public static void moveToMenu(int MenuId) throws MenuFailedException {

    log.trace("Excecuting menu change, MenuID = " + MenuId);
    switch (MenuId) {
      case 0: {
        currentMenu = new StartMenu();
        currentMenu.start();
        break;
      }
      case 1: {
        currentMenu = new LoginMenu();
        currentMenu.start();
        break;
      }
      case 2: {
        currentMenu = new CreateAccountMenu();
        currentMenu.start();
        break;
      }
      case 3: {
        currentMenu = new UserDashboardMenu();
        currentMenu.start();
        break;
      }
      case 4: {
        currentMenu = new DepositMoneyMenu();
        currentMenu.start();
        break;
      }
      case 5: {
        currentMenu = new WithdrawMoneyMenu();
        currentMenu.start();
        break;
      }
      case 6: {
        currentMenu = new InternalTransferMenu();
        currentMenu.start();
        break;
      }
      case 7: {
        currentMenu = new ExternalAccountsTransferMenu();
        currentMenu.start();
        break;
      }
      case 8: {
        currentMenu = new PendingTransferManagmentMenu();
        currentMenu.start();
        break;
      }
      case 9: {
        currentMenu = new TransactionHistoryMenu();
        currentMenu.start();
        break;
      }
      
      default: {
        throw new MenuFailedException();
      }
    }
  }

  public void establishConnection() {
    ConnectionToDatabase.establishConnection();
    
  }
}

