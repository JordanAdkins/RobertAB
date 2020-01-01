package com.revature.service.creationwizard;

import org.apache.log4j.Logger;
import com.revature.exception.QuestionNotFoundException;
import com.revature.service.ScanForUserInput;

public class RequestSecurityQuestion {

  private static Logger log = Logger.getLogger(RequestSecurityQuestion.class);

  static String question1 = "1:) What is your mother's maiden name?";
  static String question2 = "2:) What was the name of your favorite teacher?";
  static String question3 = "3:) What was the name of your first pet?";
  static String question4 = "4:) In what city where you born?";

  public static int getId() {
    int securityQuestionId = -1;
    System.out.println("Please select a security question from the list below");
    System.out.println(question1);
    System.out.println(question2);
    System.out.println(question3);
    System.out.println(question4);
    while (true) {
      log.trace("Requesting Sequrity Question Selection");
      String retrievedString = ScanForUserInput.getUserInputStream();
      log.trace("Input Received");
      retrievedString = retrievedString.trim();
      if (retrievedString.length() == 1) {
        try {
          securityQuestionId = Integer.parseInt(retrievedString);
          if (securityQuestionId == 1 || securityQuestionId == 2 || securityQuestionId == 3
              || securityQuestionId == 4) {
            return securityQuestionId;
          }
        } catch (Exception e) {
        }
      }
      log.debug("Invalid Selection made, attempting again");
      System.out.println("Please make a valid Selection");
    }
  }

  public static String getQuestionById(int questionId) throws QuestionNotFoundException {
    log.trace("Question by Id called, with question id: " + questionId);
    if (questionId == 1) {
      return question1;
    }
    if (questionId == 2) {
      return question2;
    }
    if (questionId == 3) {
      return question3;
    }
    if (questionId == 4) {
      return question4;
    }
    log.fatal("Question does not exist, throwing fatal exception");
    throw new QuestionNotFoundException();
  }
}
