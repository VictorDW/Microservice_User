package com.pragma.user.configuration;

public class Constants {

	private Constants()  {
		throw new IllegalStateException("Utility class");
	}

	//REGULAR EXPRESSION
	public static final String PATTERN_LETTERS = "^[A-Za-z\\s]+$";
	public static final String PATTERN_NUMBERS= "^\\d+$";
	public static final String PATTERN_PASSWORD = "^[A-Za-z0-9@.]+$";

//______________________________________________________________________________________________

	public static final String FIELD_EMPTY_MESSAGE = "Field cannot be empty";
	public static final String FIELD_NOT_NULL_MESSAGE = "Must not be null";
	public static final String ONLY_LETTERS_MESSAGE = "Only letters are allowed";
	public static final String ONLY_NUMBERS_MESSAGE = "Only numbers are allowed";
	public static final String DEFAULT_SIZE_MESSAGE = "Must contain a minimum of 4 characters and a maximum of 50 characters";
	public static final String DNI_SIZE_MESSAGE = "Must contain a minimum of 8 digits and a maximum of 11 digits";
	public static final String CELLPHONE_SIZE_MESSAGE = "Error in cell phone number format";
	public static final String EMAIL_INVALID_MESSAGE = "Invalid email address";
	public static final String PASSWORD_INVALID_MESSAGE = "It must not contain special characters other than @ and .";
	public static final String PASSWORD_SIZE_MESSAGE = "Must contain a minimum of 4 characters and a maximum of 20 characters.";
	public static final String USER_NOT_FOUND_MESSAGE = "User with email: %s not found";
	public static final String BAD_CREDENTIALS_MESSAGE = "Incorrect Credentials";
	public static final String ACCESS_DENIED_MESSAGE = "Access Denied";
	public static final String PERMISSIONS_ACCESS_DENIED_MESSAGE = "Don´t have permissions to access this endpoint";



}
