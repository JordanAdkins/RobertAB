package com.revature.service;

import java.util.Scanner;
import org.apache.log4j.Logger;
import com.revature.controller.Controller;

public class ScanForUserInput {
  
  private static Logger log = Logger.getLogger(ScanForUserInput.class);

  public ScanForUserInput() {}

  static Scanner userInputsc = new Scanner(System.in);

  public static String getUserInputStream() {
    String inputedString = userInputsc.nextLine();
    inputedString = inputedString.trim();
    if(inputedString.equalsIgnoreCase("0")) {
      log.debug("User has entered 0, creating new controller object");
      System.out.println("Logging out, one moment...");
      Controller restart = new Controller();
    }
    if(inputedString.equalsIgnoreCase("Close Program")) {
      log.info("User has entered: Close Program, program closing successfully");
      System.exit(0);
    }
    return inputedString;
  }

}
