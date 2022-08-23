package ap.telegrambot.bot;

import ap.telegrambot.graph.GraphService;
import ap.telegrambot.session.SessionEntity;
import ap.telegrambot.session.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Service
public class BotService {
  private final SessionService sessionService;

  public BotService(Environment environment, BotService botService) {
    try {
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      String botName = environment.getProperty("bot.username");
      String token = environment.getProperty("bot.token");
      botsApi.registerBot(new Bot(botName, token, botService));
      log.info("But register success");
    } catch (TelegramApiException e) {
      log.error("But register error");
      e.printStackTrace();
    }
  }

  public SendMessage messageProcessing(Update update) {
    String chatId = receivedMessage.getChatId().toString();
    SessionEntity sessionEntity = sessionService.getSessionByChatId(chatId);
    if (update.hasCallbackQuery()) {
      return buttonProcessing(chatId, sessionEntity, receivedMessage);
    }
    if (update.hasMessage()) {
      return textProcessing(chatId, sessionEntity, receivedMessage);
    }
    return null;
  }



}
