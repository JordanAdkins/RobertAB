package com.revature.service.creationwizard;

import org.apache.log4j.Logger;
import com.revature.service.ScanForUserInput;

public class RequestName {
  
  private static Logger log = Logger.getLogger(RequestName.class);
  
  public static String run() {
    String name;
    System.out.println("Please enter your name");
    log.trace("Requesting name");
    String retrievedString = ScanForUserInput.getUserInputStream();
    log.trace("Input Received");
    name = retrievedString;
    return name;
    
  }
}
