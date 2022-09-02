package ap.telegrambot.session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Хранение информации по сессии в ОЗУ
 */
@Component
public class SessionRepositoryMemory implements SessionRepository {
  private Map<String, SessionEntity> storeMap = new ConcurrentHashMap<>();

  @Override
  public Optional<SessionEntity> getSessionByChatId(String chatId) {
    SessionEntity sessionEntity = storeMap.getOrDefault(chatId, null);
    if (sessionEntity != null) {
      return Optional.of(sessionEntity);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public void save(SessionEntity sessionEntity) {
    storeMap.put(sessionEntity.getChatId(), sessionEntity);
  }
}
