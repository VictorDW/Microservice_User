package com.pragma.user.configuration.jwt;

public interface IJwtServiceAuthorization {

  String getSubjectFromToken(String token);
}
