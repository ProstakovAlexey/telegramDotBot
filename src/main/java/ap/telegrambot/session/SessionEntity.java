package ap.telegrambot.session;

import lombok.Getter;
import lombok.Setter;

public class SessionEntity {
  @Getter
  private final String chatId;
  @Getter
  @Setter
  private String dotId;
  @Getter
  @Setter
  private String currentVertex;

  public SessionEntity(String chatId) {
    this.chatId = chatId;
  }

}
