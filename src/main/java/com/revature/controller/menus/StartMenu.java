package com.revature.controller.menus;


import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.exception.MenuFailedException;
import com.revature.service.ScanForUserInput;

public class StartMenu implements Menu {

  private static Logger log = Logger.getLogger(StartMenu.class);

  public StartMenu() {
    super();
    log.trace("Start Menu Created");
  }

  public static int getMenuId() {
    return 0;
  }

  @Override
  public void start() {
    log.trace("Start Menu Started");
    System.out.println(
        "Welcome to Robert Ayye Banking software (Rob.A Bank LLC). Anytime during your usage");
    System.out.println("You may enter 0 to log out, or type: CLOSE PROGRAM to close this program ");
    System.out.println("Please select from the following: ");
    System.out.println("'1' - Login to existing account");
    System.out.println("'2' - Create new account");
    try {
      Controller.moveToMenu(this.askForInput());
    } catch (MenuFailedException e) {
      log.fatal("Failed to locate Menu", e);
    }
  }

  public int askForInput() {
    boolean lookingForInput = true;
    while (lookingForInput) {
      log.trace("Awaiting Input");
      String inputedString = ScanForUserInput.getUserInputStream();
      log.trace("Input received");
      if (this.checkValidInput(inputedString)) {
        log.trace("valid input received");
        return this.submitInput(inputedString);
      } else {
        log.error("Invalid Input received, attempting retry");
        System.out.println("Please make a valid Selection");
      }
    }
    return 0;
  }

  public boolean checkValidInput(String input) {
    input = input.trim();
    if (input.length() == 1) {
      try {
        int inputAsInt = Integer.parseInt(input);
        if (inputAsInt == 1 || inputAsInt == 2) {
          return true;
        }
      } catch (Exception e) {
      }
    }
    return false;
  }

  public int submitInput(String input) {
    input = input.trim();
    int inputAsInt = Integer.parseInt(input);
    if (inputAsInt == 1) {
      log.trace("User inputed 1");
      return LoginMenu.getMenuId();
    } else if (inputAsInt == 2) {
      log.trace("User inputed 2");
      return CreateAccountMenu.getMenuId();
    }
    log.fatal(
        "Invalid input has been passed through the menu selection, the program will now terminate");
    return -1;
  }
}
