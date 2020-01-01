package com.revature.service.creationwizard;

import org.apache.log4j.Logger;
import com.revature.service.ScanForUserInput;

public class RequestPassword {

  private static Logger log = Logger.getLogger(RequestPassword.class);

  public static String run() {
    String password = "";
    System.out.println(
        "Please enter your desired Password (Please note that passwords ARE case-sensitive)");
    log.trace("Requesting Password");
    String retrievedString = ScanForUserInput.getUserInputStream();
    log.trace("Input Received");
    password = retrievedString;
    return password;
  }
}
