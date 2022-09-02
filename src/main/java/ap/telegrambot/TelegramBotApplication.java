package ap.telegrambot;

import ap.telegrambot.bot.Bot;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@SpringBootApplication
public class TelegramBotApplication implements CommandLineRunner {
  private final Environment environment;
  private final ApplicationEventPublisher publisher;

  public TelegramBotApplication(Environment environment, ApplicationEventPublisher publisher) {
    this.environment = environment;
    this.publisher = publisher;
  }

  public static void main(String[] args) {
    SpringApplication.run(TelegramBotApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    try {
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      String botName = environment.getProperty("bot.username");
      String token = environment.getProperty("bot.token");
      botsApi.registerBot(new Bot(botName, token, publisher));
      log.info("But register success");
    } catch (TelegramApiException e) {
      log.error("But register error");
      e.printStackTrace();
      System.exit(1);
    }
  }
}
