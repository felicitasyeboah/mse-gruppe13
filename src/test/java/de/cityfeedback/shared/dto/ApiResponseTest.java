package de.cityfeedback.shared.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ApiResponseTest {

  @Test
  void testNoArgsConstructor() {
    ApiResponse apiResponse = new ApiResponse();
    assertNull(apiResponse.getMessage());
    assertNull(apiResponse.getData());
  }

  @Test
  void testAllArgsConstructor() {
    String message = "Test message";
    Object data = new Object();
    ApiResponse apiResponse = new ApiResponse(message, data);
    assertEquals(message, apiResponse.getMessage());
    assertEquals(data, apiResponse.getData());
  }

  @Test
  void testSettersAndGetters() {
    ApiResponse apiResponse = new ApiResponse();
    String message = "Test message";
    Object data = new Object();
    apiResponse.setMessage(message);
    apiResponse.setData(data);
    assertEquals(message, apiResponse.getMessage());
    assertEquals(data, apiResponse.getData());
  }
}
