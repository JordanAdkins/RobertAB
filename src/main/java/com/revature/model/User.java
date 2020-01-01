package com.revature.model;

public class User {

  private int userId;
  private String userLoginId;
  private String userPassword;
  private String userName;
  private int userQuestionId;
  private String userQuestionAnswer;
  private int userSSID;
  private int userCheckingAccountNumber;
  private int userSavingAccountNumber;
  private boolean userHasSavingAccount = false;
  private boolean userHasCheckingAccount = false;
  private int userCheckingAccountBalance;
  private int userSavingAccountBalance;

  

  public User(int userId, String userLoginId, String userPassword, String userName,
      int userQuestionId, String userQuestionAnswer, int userSSID, int userCheckingAccountNumber,
      int userSavingAccountNumber, boolean userHasSavingAccount, boolean userHasCheckingAccount,
      int userCheckingAccountBalance, int userSavingAccountBalance) {
    super();
    this.userId = userId;
    this.userLoginId = userLoginId;
    this.userPassword = userPassword;
    this.userName = userName;
    this.userQuestionId = userQuestionId;
    this.userQuestionAnswer = userQuestionAnswer;
    this.userSSID = userSSID;
    this.userCheckingAccountNumber = userCheckingAccountNumber;
    this.userSavingAccountNumber = userSavingAccountNumber;
    this.userHasSavingAccount = userHasSavingAccount;
    this.userHasCheckingAccount = userHasCheckingAccount;
    this.userCheckingAccountBalance = userCheckingAccountBalance;
    this.userSavingAccountBalance = userSavingAccountBalance;
  }


  /**
   * @return the userId
   */
  public int getUserId() {
    return userId;
  }


  /**
   * @param userId the userId to set
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }


  /**
   * @return the userLoginId
   */
  public String getUserLoginId() {
    return userLoginId;
  }


  /**
   * @param userLoginId the userLoginId to set
   */
  public void setUserLoginId(String userLoginId) {
    this.userLoginId = userLoginId;
  }


  /**
   * @return the userPassword
   */
  public String getUserPassword() {
    return userPassword;
  }


  /**
   * @param userPassword the userPassword to set
   */
  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }


  /**
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }


  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }


  /**
   * @return the userQuestionId
   */
  public int getUserQuestionId() {
    return userQuestionId;
  }


  /**
   * @param userQuestionId the userQuestionId to set
   */
  public void setUserQuestionId(int userQuestionId) {
    this.userQuestionId = userQuestionId;
  }


  /**
   * @return the userQuestionAnswer
   */
  public String getUserQuestionAnswer() {
    return userQuestionAnswer;
  }


  /**
   * @param userQuestionAnswer the userQuestionAnswer to set
   */
  public void setUserQuestionAnswer(String userQuestionAnswer) {
    this.userQuestionAnswer = userQuestionAnswer;
  }


  /**
   * @return the userSSID
   */
  public int getUserSSID() {
    return userSSID;
  }


  /**
   * @param userSSID the userSSID to set
   */
  public void setUserSSID(int userSSID) {
    this.userSSID = userSSID;
  }


  /**
   * @return the userCheckingAccountNumber
   */
  public int getUserCheckingAccountNumber() {
    return userCheckingAccountNumber;
  }


  /**
   * @param userCheckingAccountNumber the userCheckingAccountNumber to set
   */
  public void setUserCheckingAccountNumber(int userCheckingAccountNumber) {
    this.userCheckingAccountNumber = userCheckingAccountNumber;
  }


  /**
   * @return the userSavingAccountNumber
   */
  public int getUserSavingAccountNumber() {
    return userSavingAccountNumber;
  }


  /**
   * @param userSavingAccountNumber the userSavingAccountNumber to set
   */
  public void setUserSavingAccountNumber(int userSavingAccountNumber) {
    this.userSavingAccountNumber = userSavingAccountNumber;
  }


  /**
   * @return the userHasSavingAccount
   */
  public boolean isUserHasSavingAccount() {
    return userHasSavingAccount;
  }


  /**
   * @param userHasSavingAccount the userHasSavingAccount to set
   */
  public void setUserHasSavingAccount(boolean userHasSavingAccount) {
    this.userHasSavingAccount = userHasSavingAccount;
  }


  /**
   * @return the userHasCheckingAccount
   */
  public boolean isUserHasCheckingAccount() {
    return userHasCheckingAccount;
  }


  /**
   * @param userHasCheckingAccount the userHasCheckingAccount to set
   */
  public void setUserHasCheckingAccount(boolean userHasCheckingAccount) {
    this.userHasCheckingAccount = userHasCheckingAccount;
  }


  /**
   * @return the userCheckingAccountBalance
   */
  public int getUserCheckingAccountBalance() {
    return userCheckingAccountBalance;
  }


  /**
   * @param userCheckingAccountBalance the userCheckingAccountBalance to set
   */
  public void setUserCheckingAccountBalance(int userCheckingAccountBalance) {
    this.userCheckingAccountBalance = userCheckingAccountBalance;
  }


  /**
   * @return the userSavingAccountBalance
   */
  public int getUserSavingAccountBalance() {
    return userSavingAccountBalance;
  }


  /**
   * @param userSavingAccountBalance the userSavingAccountBalance to set
   */
  public void setUserSavingAccountBalance(int userSavingAccountBalance) {
    this.userSavingAccountBalance = userSavingAccountBalance;
  }


}
