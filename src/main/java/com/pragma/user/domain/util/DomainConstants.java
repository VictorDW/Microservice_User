package com.pragma.user.domain.util;

public class DomainConstants {

	private DomainConstants() {
		throw new IllegalStateException("Utility class");
	}

	public enum Class {
		USER,
		ROL;
	}

	public static final String ALREADY_EXIST_MESSAGE = "This user with email %s you want to register already exists";
	public static final String NOT_FOUND_MESSAGE = "The %s not found";
}
