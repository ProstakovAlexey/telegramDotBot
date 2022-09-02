package ap.telegrambot.bot;

import ap.telegrambot.bot.events.UpdateEvent;
import ap.telegrambot.graph.GraphService;
import ap.telegrambot.session.SessionEntity;
import ap.telegrambot.session.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class BotService {
  private final SessionService sessionService;


  @Async
  @EventListener
  public void messageProcessing(UpdateEvent updateEvent) {
    Update update = updateEvent.getUpdate();
    Message receivedMessage = update.getMessage();
    if (receivedMessage == null) {
      return;
    }
    String chatId = receivedMessage.getChatId().toString();
    SessionEntity sessionEntity = sessionService.getSessionByChatId(chatId);
    if (update.hasCallbackQuery()) {
      //return buttonProcessing(chatId, sessionEntity, receivedMessage);
    }
    if (update.hasMessage()) {
      //return textProcessing(chatId, sessionEntity, receivedMessage);
    }
  }

  private SendMessage textProcessing(String chatId, SessionEntity sessionEntity, Message receivedMessage) {
    log.debug("Обработка текстового сообщения");
    return null;
  }

  private SendMessage buttonProcessing(String chatId, SessionEntity sessionEntity, Message receivedMessage) {
    log.debug("Обработка кнопки");
    return null;
  }


}
