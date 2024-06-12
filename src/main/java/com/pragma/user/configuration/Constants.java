package com.pragma.user.configuration;

public class Constants {

	private Constants()  {
		throw new IllegalStateException("Utility class");
	}

	//REGULAR EXPRESSION
	public static final String PATTERN_LETTERS = "^[A-Za-z\\s]+$";
	public static final String PATTERN_NUMBERS= "^\\d+$";
	public static final String PATTERN_PASSWORD = "^[A-Za-z0-9ñÑ@.]+$";

//______________________________________________________________________________________________

	public static final String FIELD_EMPTY_MESSAGE = "El campo no puede estar vacío";
	public static final String FIELD_NOT_NULL_MESSAGE = "No debe ser nulo";
	public static final String ONLY_LETTERS_MESSAGE = "Sólo se admiten letras";
	public static final String ONLY_NUMBERS_MESSAGE = "Sólo se admiten numeros";
	public static final String DEFAULT_SIZE_MESSAGE = "Debe contener un mínimo de 4 caracteres y un máximo de 50 caracteres";
	public static final String DNI_SIZE_MESSAGE = "Debe contener un mínimo de 8 dígitos y un máximo de 11 dígitos";
	public static final String CELLPHONE_SIZE_MESSAGE = "Error en el formato del número de teléfono";
	public static final String EMAIL_INVALID_MESSAGE = "Dirección de correo electrónico no válida";
	public static final String PASSWORD_INVALID_MESSAGE = "No debe contener caracteres especiales distintos de @ y .";
	public static final String PASSWORD_SIZE_MESSAGE = "Debe contener un mínimo de 4 caracteres y un máximo de 20.";
	public static final String USER_NOT_FOUND_MESSAGE = "Usuario con email: %s no encontrado";
	public static final String BAD_CREDENTIALS_MESSAGE = "Las credenciales proporcionadas no son correctas. Por favor, verifica.";
	public static final String ACCESS_DENIED_MESSAGE = "Acceso denegado a este recurso";
	public static final String PERMISSIONS_ACCESS_DENIED_MESSAGE = "No tienes los permisos necesarios para acceder a este recurso";
	public static final String ROLE_NOT_NULL_MESSAGE = "El id del rol no debe ser inferior a 1";


}
