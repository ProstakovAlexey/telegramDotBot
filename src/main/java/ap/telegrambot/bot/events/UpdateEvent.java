package ap.telegrambot.bot.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class UpdateEvent extends ApplicationEvent {
  private final Update update;


  public UpdateEvent(Object source, Update update) {
    super(source);
    this.update = update;
  }
}
