package com.revature.controller.menus;

import org.apache.log4j.Logger;

public class LoginMenu implements Menu {

  private static Logger log = Logger.getLogger(LoginMenu.class);

  public LoginMenu() {
   log.trace("Login Menu Created");
  }
  
  public static int getMenuId() {
    return 1;
  }

  @Override
  public void start() {
    log.trace("Login Menu Started");
    System.out.println("Welcome to the Login Menu. Please Enter your username ");

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
