package com.revature.service;

import com.revature.model.User;
import com.revature.service.creationwizard.RequestName;
import com.revature.service.creationwizard.RequestPassword;
import com.revature.service.creationwizard.RequestQuestionAnswer;
import com.revature.service.creationwizard.RequestSSID;
import com.revature.service.creationwizard.RequestSecurityQuestion;
import com.revature.service.creationwizard.RequestUsername;
import org.apache.log4j.Logger;

public class AccountCreation {

  private static Logger log = Logger.getLogger(AccountCreation.class);

  public AccountCreation() {}

  public static User accountCreationWizard() {
    
    String username = RequestUsername.run();
    String password = RequestPassword.run();
    String name = RequestName.run();
    int questionId = RequestSecurityQuestion.getId();
    String questionAnswer = RequestQuestionAnswer.run(questionId);
    int SSID = RequestSSID.run(); 

    System.out.println(username + password + name + questionId + questionAnswer + SSID);
    return null;
  }

}
