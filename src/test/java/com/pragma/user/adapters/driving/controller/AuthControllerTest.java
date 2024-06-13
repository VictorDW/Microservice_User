package com.pragma.user.adapters.driving.controller;

import com.pragma.user.adapters.driving.adapter.IAuthHandler;
import com.pragma.user.adapters.driving.dto.request.AuthRequest;
import com.pragma.user.adapters.driving.dto.response.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  @InjectMocks
  private AuthController authController;

  @Mock
  private IAuthHandler authHandler;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
  }


  public static Stream<Arguments> provideAuthRequestWithValidationErrors() {
    return Stream.of(
        Arguments.of("{\"email\":\"\",\"password\":\"12345\""),
        Arguments.of("{\"email\":\"viactor@gmail.com\",\"password\":\"\"}"),
        Arguments.of("{\"email\":\"viactor@gmail.com\",\"password\":\"123\"}"),
        Arguments.of("{\"email\":\"viactor@gmail.com\",\"password\":\"123456789123456789123\"}")
    );
  }

  @Test
  @DisplayName("Valid request returns an OK response with the expected response body")
  void test1() {

    // GIVEN
    AuthRequest authRequest = new AuthRequest("username", "password");
    AuthResponse expectedResponse = new AuthResponse(1L, "firstName", "lastName", "role", "token");
    given(authHandler.login(authRequest)).willReturn(expectedResponse);

    // WHEN
    ResponseEntity<AuthResponse> response = authController.login(authRequest);

    // THEN
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }

  @ParameterizedTest()
  @MethodSource("provideAuthRequestWithValidationErrors")
  @DisplayName("should return a status 400 (bad request) when sending a email and password with validation errors")
  void test2(String bodyRequest) throws Exception {

    //WHEN
    MockHttpServletRequestBuilder requestBuilder = post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(bodyRequest);

    //THEN
    mockMvc.perform(requestBuilder)
        .andExpect(status().isBadRequest());
  }
}