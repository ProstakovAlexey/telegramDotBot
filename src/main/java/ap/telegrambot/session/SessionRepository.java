package ap.telegrambot.session;

import java.util.Optional;

public interface SessionRepository {
  Optional<SessionEntity> getSessionByChatId(String chatId);
  void save(SessionEntity sessionEntity);

}
