package com.pragma.user.adapters.driving.controller;

import com.pragma.user.adapters.driving.adapter.IUserHandler;
import com.pragma.user.adapters.driving.dto.request.UserAdminRequest;
import com.pragma.user.adapters.driving.dto.request.UserRequest;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private IUserHandler handler;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }


  public static Stream<Arguments> provideRequestWithValidationErrors() {
    return Stream.of(
        Arguments.of("{\"firstName\":\"\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"\"}"),
        Arguments.of("{\"firstName\":\"J\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"C1\"}"),
        Arguments.of("{\"firstName\":\"JuanPabloJuanPabloJuanPabloJuanPabloJuanPabloJuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"R\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"RodriguezRodriguezRodriguezRodriguezRodriguezRodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"1\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"123456789012\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"1\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"12345678901\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"jj.com\",\"password\":\"Contraseña123\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"C1\"}"),
        Arguments.of("{\"firstName\":\"JuanPablo\",\"lastName\":\"Rodriguez\",\"dni\":\"1234567890\",\"cellphone\":\"1234567890\",\"email\":\"juanpablo@example.com\",\"password\":\"Contraseña12345678912345\"}")
    );
  }



  @Test
  @DisplayName("Registering a new admin user with valid request")
  void test1() {

    // GIVEN
    UserAdminRequest request = new UserAdminRequest("Victor", "Agudelo", "123456789", "3124541245", "victor@gmail", "password");

    // WHEN
    ResponseEntity<String> response = userController.registerAdmin(request);

    // THAT
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("User admin created successfully", response.getBody());
    verify(handler, times(1)).register(request);
  }

  @Test
  @DisplayName("Registering a new user with valid request")
  void test2() {

    // GIVEN
    UserRequest request = new UserRequest("Victor", "Agudelo", "123456789", "3124541245", "victor@gmail", 2L, "password");

    // WHEN
    ResponseEntity<String> response = userController.register(request);

    // THAT
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("User created successfully", response.getBody());
    verify(handler, times(1)).register(request);
  }

  @ParameterizedTest()
  @MethodSource("provideRequestWithValidationErrors")
  @DisplayName("should return a status 400 (incorrect request) when sending data with validation errors")
  void test2(String bodyRequest) throws Exception {

    //WHEN
    MockHttpServletRequestBuilder requestBuilder = post("/api/users/register/admin")
        .contentType(MediaType.APPLICATION_JSON)
        .content(bodyRequest);

    //THEN
    mockMvc.perform(requestBuilder)
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest()
  @MethodSource("provideRequestWithValidationErrors")
  @DisplayName("should return a status 400 (incorrect request) when sending data with validation errors")
  void test3(String bodyRequest) throws Exception {

    //WHEN
    MockHttpServletRequestBuilder requestBuilder = post("/api/users/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(bodyRequest);

    //THEN
    mockMvc.perform(requestBuilder)
        .andExpect(status().isBadRequest());
  }


}