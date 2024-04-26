package com.pragma.user.configuration.springdoc;

public class SpringDocConstants {

  private SpringDocConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String OPERATION_SUMMARY_LOGIN = "User Authentication";
  public static final String OPERATION_SUMMARY_REGISTER_ADMIN = "User Admin Registration";
  public static final String OPERATION_SUMMARY_REGISTER = "User Registration";

  public static final String OPERATION_DESCRIPTION_LOGIN = "This endpoint allows users to authenticate to the system.";
  public static final String OPERATION_DESCRIPTION_REGISTER_ADMIN = "This unrestricted endpoint allows the first administrator user to be created in the system.";
  public static final String OPERATION_DESCRIPTION_REGISTER = " This endpoint allows users with 'ADMIN' or 'TUTOR' roles to register new users in the system.";



}
