package com.revature.service;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;
import com.revature.controller.menus.UserDashboardMenu;
import com.revature.exception.MenuFailedException;

public class TOSAuthentication {

  private static Logger log = Logger.getLogger(CreateCheckingAccount.class);

  public static boolean getTOSAgreement() {
    log.trace("testing if this machine supports DESKTOP");
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      log.debug("Desktop supported, attempting to open browser");
      try {
        Desktop.getDesktop().browse(new URI("https://tinyurl.com/RobertABank"));
        System.out.println("Do you agree to comply with our terms of service?");
        System.out.println("Please enter YES or NO");
        String inputedString = ScanForUserInput.getUserInputStream();
        if (inputedString.equalsIgnoreCase("yes") || inputedString.equalsIgnoreCase("y")) {
          return true;
        } else {
          System.out.println("Thank you for your interest with Rob.A Bank. Returning to dashboard");
          try {
            Controller.moveToMenu(UserDashboardMenu.getMenuId());
          } catch (MenuFailedException e) {
            log.fatal("Failed to locate menu, closing program to preserve data");
            System.exit(1);
          }
        }
      } catch (IOException e) {
        // TODO fix this maybe
        log.error("Failed to open user browser. Gonna assume that means they agree");
        System.out.println("It appears your default browser does not support our code");
        System.out.println("We at Rob.A Banking trust that you are going to agree to our TOS");
        return true;
      } catch (URISyntaxException e) {
        // TODO fix this maybe
        log.error("Failed to open user browser. Gonna assume that means they agree");
        System.out.println("It appears your default browser does not support our code");
        System.out.println("We at Rob.A Banking trust that you are going to agree to our TOS");
        return true;
      }
    }
    log.error("Failed to open user browser. Gonna assume that means they agree");
    System.out.println("It appears your default browser does not support our code");
    System.out.println("We at Rob.A Banking trust that you are going to agree to our TOS");
    return false;
  }
}
