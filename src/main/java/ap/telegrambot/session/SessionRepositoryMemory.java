package ap.telegrambot.session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Хранение информации по сессии в ОЗУ
 */
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
