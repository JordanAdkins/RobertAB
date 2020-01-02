package com.revature.controller.menus;

import org.apache.log4j.Logger;

public class ExternalAccountsTransferMenu implements Menu {
  
  private static Logger log = Logger.getLogger(ExternalAccountsTransferMenu.class);

  public ExternalAccountsTransferMenu() {
    log.trace("InternalTransferMenu Created");
  }
  
  public static int getMenuId() {
    return 6;
  }

  @Override
  public void start() {
    // TODO Auto-generated method stub

  }

}