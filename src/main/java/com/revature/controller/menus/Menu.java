package com.revature.controller.menus;

public interface Menu {
  
  public void start();
  public int askForInput();
  public boolean checkValidInput(String input);
  public int submitInput(String input);
}
