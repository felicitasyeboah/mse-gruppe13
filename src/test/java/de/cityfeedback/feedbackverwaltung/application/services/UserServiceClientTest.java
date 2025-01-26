package de.cityfeedback.feedbackverwaltung.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import de.cityfeedback.shared.dto.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class UserServiceClientTest {

  @Mock private RestTemplate restTemplate;
  @Mock private UserCacheService userCacheService;
  @InjectMocks private UserServiceClient userServiceClient;

  private UserDto userDto;
  private ApiResponse apiResponse;

  @BeforeEach
  void setUp() {
    userDto = new UserDto();
    userDto.setUserId(1L);
    userDto.setUserName("John Doe");

    apiResponse = new ApiResponse();
    apiResponse.setData(userDto);
  }

  @Test
  void fetchUserById_ShouldReturnUserFromCache() {
    when(userCacheService.getUserFromCache(1L)).thenReturn(userDto);

    UserDto result = userServiceClient.fetchUserById(1L);

    assertEquals(userDto, result);
    verify(userCacheService, times(1)).getUserFromCache(1L);
    verify(restTemplate, never()).getForEntity(anyString(), eq(ApiResponse.class));
  }

  @Test
  void fetchUserById_ShouldReturnUserFromServiceAndCacheIt() {
    when(userCacheService.getUserFromCache(1L)).thenReturn(null);
    when(restTemplate.getForEntity("http://localhost:8081/user/1", ApiResponse.class))
        .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

    UserDto result = userServiceClient.fetchUserById(1L);

    assertEquals(userDto, result);
    verify(userCacheService, times(1)).getUserFromCache(1L);
    verify(restTemplate, times(1)).getForEntity("http://localhost:8081/user/1", ApiResponse.class);
    verify(userCacheService, times(1)).cacheUser(userDto);
  }

  @Test
  void fetchUserById_ShouldThrowClientError() {
    when(userCacheService.getUserFromCache(1L)).thenReturn(null);
    when(restTemplate.getForEntity("http://localhost:8081/user/1", ApiResponse.class))
        .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST));

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              userServiceClient.fetchUserById(1L);
            });

    assertTrue(exception.getMessage().contains("Client error"));
    verify(userCacheService, times(1)).getUserFromCache(1L);
    verify(restTemplate, times(1)).getForEntity("http://localhost:8081/user/1", ApiResponse.class);
  }

  @Test
  void fetchUserById_ShouldThrowServerError() {
    when(userCacheService.getUserFromCache(1L)).thenReturn(null);
    when(restTemplate.getForEntity("http://localhost:8081/user/1", ApiResponse.class))
        .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR));

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              userServiceClient.fetchUserById(1L);
            });

    assertTrue(exception.getMessage().contains("Server error"));
    verify(userCacheService, times(1)).getUserFromCache(1L);
    verify(restTemplate, times(1)).getForEntity("http://localhost:8081/user/1", ApiResponse.class);
  }
}
