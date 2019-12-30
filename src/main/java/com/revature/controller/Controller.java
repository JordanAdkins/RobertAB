package com.revature.controller;

import java.sql.Connection;
import com.revature.exception.MenuFailedException;
import com.revature.repository.ConnectionToDatabase;

public class Controller {

  public static Connection connection = null;

  private static Menu currentMenu;

  public Controller() {
    super();
    System.out.println("Controller Established");
    try {
      this.moveToMenu(1);
    } catch (MenuFailedException e) {
      e.printStackTrace();
    }
    this.establishConnection();
  }

  public void moveToMenu(int MenuId) throws MenuFailedException {

    switch (MenuId) {
      case 1: {
        currentMenu = new LoginMenu();
        currentMenu.start();
        break;
      }
      case 2: {
        // currentMenu = new OtherMenu();
      }
      default: {
        throw new MenuFailedException();
      }
    }
  }
  
  public void establishConnection() {
    if (ConnectionToDatabase.establishConnection()) {
      System.out.println("Connected");
    } else {
      System.out.println("Failed To Establish Connection");
    }
  }
}

