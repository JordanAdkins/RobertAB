package com.revature.service.creationwizard;

import org.apache.log4j.Logger;
import com.revature.service.ScanForUserInput;

public class RequestPassword {

  private static Logger log = Logger.getLogger(RequestPassword.class);

  public static String run() {
    String password = "";
    String passPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}";
    while(true) {
    System.out.println(
        "Please enter your desired Password (Please note that passwords ARE case-sensitive)");
    System.out.println("Passwords must contain:");
    System.out.println("at least one UPPERCASE letter");
    System.out.println("at least one lowercase latter");
    System.out.println("at least one number");
    System.out.println("between 8 and 16 characters in length");
    log.trace("Requesting Password");
    String retrievedString = ScanForUserInput.getUserInputStream();
    log.trace("Input Received");
    if(retrievedString.matches(passPattern)) {
      password = retrievedString;
      return password;
    }
    log.debug("bad password received, trying again");
    System.out.println("The password you entered does not match requirments");
    }
  }
}
