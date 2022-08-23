package ap.telegrambot.session;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {
  private final SessionRepository sessionRepository;

  public SessionEntity getSessionByChatId(String chatId) {
    log.debug("Получение сессии для {}", chatId);
    Optional<SessionEntity> sessionEntityOpt = sessionRepository.getSessionByChatId(chatId);
    return sessionEntityOpt.orElseGet(() -> new SessionEntity(chatId));
  }


}
