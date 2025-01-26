package de.cityfeedback.feedbackverwaltung.application.services;

import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class UserCacheService {
  private final ConcurrentHashMap<Long, UserDto> userCache = new ConcurrentHashMap<>();

  public void cacheUser(UserDto user) {
    userCache.put(user.getUserId(), user);
  }

  public UserDto getUserFromCache(Long userId) {
    return userCache.get(userId);
  }

  public void remove(Long userId) {
    userCache.remove(userId);
  }

  public void clearCache() {
    userCache.clear();
  }
}
