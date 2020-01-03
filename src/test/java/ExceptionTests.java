import org.junit.Test;
import com.revature.controller.Controller;
import com.revature.exception.MenuFailedException;
import com.revature.exception.QuestionNotFoundException;
import com.revature.service.creationwizard.RequestSecurityQuestion;

public class ExceptionTests {

  @Test(expected = QuestionNotFoundException.class)
  public void badQuestionId() throws QuestionNotFoundException {
    RequestSecurityQuestion.getQuestionById(8);
  }
  
  @Test(expected = MenuFailedException.class)
  public void navigatefails() throws MenuFailedException {
    Controller.moveToMenu(10);
  }

}
