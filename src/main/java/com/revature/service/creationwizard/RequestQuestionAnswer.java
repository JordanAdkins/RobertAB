package com.revature.service.creationwizard;

import org.apache.log4j.Logger;
import com.revature.exception.QuestionNotFoundException;
import com.revature.service.ScanForUserInput;

public class RequestQuestionAnswer {

  private static Logger log = Logger.getLogger(RequestQuestionAnswer.class);

  public static String run(int questionId) {
    String questionAnswer;
    try {
      System.out.println("You have selected the question:");
      System.out.println(RequestSecurityQuestion.getQuestionById(questionId));
      System.out
          .println("Please enter your answer (please be aware that answers ARE case-sensitive");
    } catch (QuestionNotFoundException e) {
      log.fatal("fatal error occured when accessing questions by id");
      System.exit(1);
    }
    questionAnswer = ScanForUserInput.getUserInputStream();
    return questionAnswer;
  }
}
