package com.pragma.user.domain.util;

public class DomainConstants {

	private DomainConstants() {
		throw new IllegalStateException("Utility class");
	}

	public enum Class {
		USUARIO,
		ROL;
	}

	public static final String ALREADY_EXIST_MESSAGE = "Este usuario con email %s que quieres registrar ya existe";
	public static final String NOT_FOUND_MESSAGE = "El %s no fue encontrado";
	public static final String WITHOUT_PERMISSIONS_MESSAGE = " No tienes los permisos para crear un %s";
	public static final String INITIAL_ENDPOINT_UNAVAILABLE = "El primer administrador ya ha sido registrado. Utilice el servicio alternativo para registrar";
}
