package de.cityfeedback.feedbackverwaltung.application.services;

import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCacheServiceTest {

  private UserCacheService userCacheService;
  private UserDto userDto;

  @BeforeEach
  void setUp() {
    userCacheService = new UserCacheService();
    userDto = new UserDto();
    userDto.setUserId(1L);
    userDto.setUserName("John Doe");
  }

  @Test
  void cacheUser_ShouldStoreUserInCache() {
    userCacheService.cacheUser(userDto);
    UserDto cachedUser = userCacheService.getUserFromCache(1L);
    assertEquals(userDto, cachedUser);
  }

  @Test
  void getUserFromCache_ShouldReturnNullIfUserNotInCache() {
    UserDto cachedUser = userCacheService.getUserFromCache(1L);
    assertNull(cachedUser);
  }

  @Test
  void remove_ShouldRemoveUserFromCache() {
    userCacheService.cacheUser(userDto);
    userCacheService.remove(1L);
    UserDto cachedUser = userCacheService.getUserFromCache(1L);
    assertNull(cachedUser);
  }

  @Test
  void clearCache_ShouldClearAllUsersFromCache() {
    userCacheService.cacheUser(userDto);
    userCacheService.clearCache();
    UserDto cachedUser = userCacheService.getUserFromCache(1L);
    assertNull(cachedUser);
  }
}
