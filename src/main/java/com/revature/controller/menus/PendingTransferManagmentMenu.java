package com.revature.controller.menus;

import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.exception.MenuFailedException;
import com.revature.service.AcceptTransfer;
import com.revature.service.DeclineTransfer;
import com.revature.service.ScanForUserInput;

public class PendingTransferManagmentMenu implements Menu {

  private static Logger log = Logger.getLogger(PendingTransferManagmentMenu.class);

  public PendingTransferManagmentMenu() {
    log.trace("Pending Transfer Menu Created");
  }
  public static int getMenuId() {
    return 8;
  }

  @Override
  public void start() {
    log.trace("Pending Transfer Menu Started");

    System.out.println("Welcome to the pending transfer menu.");
    System.out.println(
        "You have a transfer Pending from " + Controller.CurrentUser.getPendingTransferSenderName()
            + " in the amount of: $" + Controller.CurrentUser.getPendingTransferAmount());
    boolean lookingForInput = true;
    while (lookingForInput) {
      System.out.println("Would you like to Accept or Decline this transfer?");
      System.out.println("1:) Accept");
      System.out.println("2:) Decline");
      log.trace("Awaiting Input");
      String inputedString = ScanForUserInput.getUserInputStream();
      log.trace("Input received");
      if (inputedString.equals("1")) {
        AcceptTransfer.run();
      }
      if (inputedString.equals("2")) {
        if(DeclineTransfer.run()) {
          System.out.println("Succesfully declined transfer, returning to your dashboard");
          try {
            Controller.moveToMenu(UserDashboardMenu.getMenuId());
          } catch (MenuFailedException e) {
            log.fatal("Failed To find menu, closing program to save data");
            System.exit(1);
          }
        }
      }
      System.out.println("invalid input");
      System.out.println("Please enter either a 1 or 2");
    }
  }

}
