package com.revature.service;

import com.revature.model.User;
import com.revature.service.creationwizard.GenerateUserID;
import com.revature.service.creationwizard.RequestName;
import com.revature.service.creationwizard.RequestPassword;
import com.revature.service.creationwizard.RequestQuestionAnswer;
import com.revature.service.creationwizard.RequestSSID;
import com.revature.service.creationwizard.RequestSecurityQuestion;
import com.revature.service.creationwizard.RequestUsername;
import org.apache.log4j.Logger;

public class AccountCreation {

  @SuppressWarnings("unused")
  private static Logger log = Logger.getLogger(AccountCreation.class);

  public AccountCreation() {}

  public static User accountCreationWizard() {
    
    String username = RequestUsername.run();
    String password = RequestPassword.run();
    String name = RequestName.run();
    int questionId = RequestSecurityQuestion.getId();
    String questionAnswer = RequestQuestionAnswer.run(questionId);
    int SSID = RequestSSID.run(); 
    int userID = GenerateUserID.run(username, password, name, questionId, questionAnswer, SSID);
    
    User currentUser = new User(userID, username, password, name, questionId, questionAnswer,
        SSID, 0, 0, false, false, 0, 0);

    return currentUser;
  }

}
