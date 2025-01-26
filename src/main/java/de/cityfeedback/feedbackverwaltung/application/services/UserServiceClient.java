package de.cityfeedback.feedbackverwaltung.application.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import de.cityfeedback.shared.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {
  private final RestTemplate restTemplate;
  private final UserCacheService userCacheService;

  public UserServiceClient(RestTemplate restTemplate, UserCacheService userCacheService) {
    this.restTemplate = restTemplate;
    this.userCacheService = userCacheService;
  }

  public UserDto fetchUserById(Long userId) {

    UserDto user = userCacheService.getUserFromCache(userId);
    if (user != null) {
      return user;
    }

    // Lazy-load user data if not found in cache
    user = fetchUserFromService(userId);

    // cache user for later use
    if (user != null) {
      userCacheService.cacheUser(user);
    }

    return user;
  }

  private UserDto fetchUserFromService(Long userId) {
    UserDto user;
    String url = "http://localhost:8081/user/" + userId;
    ResponseEntity<ApiResponse> response = restTemplate.getForEntity(url, ApiResponse.class);

    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
      // Ensure the data is converted to UserDto
      ObjectMapper objectMapper = new ObjectMapper();
      user = objectMapper.convertValue(response.getBody().getData(), UserDto.class);
    } else if (response.getStatusCode().is4xxClientError()) {

      throw new RuntimeException(
          "Client error: " + (response.getBody() != null ? response.getBody().getMessage() : ""));
    } else if (response.getStatusCode().is5xxServerError()) {
      throw new RuntimeException(
          "Server error: " + (response.getBody() != null ? response.getBody().getMessage() : ""));
    } else {
      throw new RuntimeException("Unexpected error: " + response.getStatusCode());
    }
    return user;
  }
}
