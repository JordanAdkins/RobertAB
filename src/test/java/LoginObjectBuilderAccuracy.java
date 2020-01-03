import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.revature.controller.Controller;
import com.revature.controller.menus.DepositMoneyMenu;
import com.revature.controller.menus.WithdrawMoneyMenu;
import com.revature.exception.MenuFailedException;
import com.revature.model.User;

public class LoginObjectBuilderAccuracy {
  
  public static User testuser;

  @Before
  public void buildMockUser() {
    testuser = new User(24, "J4Testing","J4Testing","J4Testing",1,"J4Testing",444444444
        ,411456624,211454324, true, true, 49999,49999);
  }
  @Test
  public void testDeposit() throws MenuFailedException {
    Controller.CurrentUser = testuser;
    Controller.moveToMenu(DepositMoneyMenu.getMenuId());
    Assert.assertEquals(50000, Controller.CurrentUser.getUserCheckingAccountBalance(), 0.001);
    
  }
  @Test
  public void testWithdraw() throws MenuFailedException {
    Controller.CurrentUser = testuser;
    Controller.moveToMenu(WithdrawMoneyMenu.getMenuId());
    Assert.assertEquals(49998, Controller.CurrentUser.getUserCheckingAccountBalance(), 0.001);
    
  }

}
