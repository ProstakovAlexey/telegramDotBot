package ap.telegrambot.bot;

import ap.telegrambot.bot.events.UpdateEvent;
import ap.telegrambot.graph.DotEntity;
import ap.telegrambot.graph.GraphService;
import ap.telegrambot.session.SessionEntity;
import ap.telegrambot.session.SessionRepository;
import ap.telegrambot.session.SessionService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class Bot extends TelegramLongPollingBot {
  private final static String SOURCE = "Bot";
  private final String userName;
  private final String botToken;
  private final ApplicationEventPublisher publisher;

  public Bot(String userName, String token, ApplicationEventPublisher publisher) {
    super();
    this.userName = userName;
    this.botToken = token;
    this.publisher = publisher;
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      UpdateEvent updateEvent = new UpdateEvent(SOURCE, update);
      publisher.publishEvent(updateEvent);
    } else {
      log.debug("Update without message");
    }
  }

  private void sendMessage(SendMessage message) {
    if (message != null) {
      try {
        execute(message);
      } catch (TelegramApiException e) {
        log.error("Ошибка при отправке сообщения: {}", message);
      }
    }
  }

  @Override
  public String getBotUsername() {
    return userName;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  /*
  private SendMessage getAllGraph(String chatId) {
    List<DotEntity> dots = graphService.getAllDots();

    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    List<InlineKeyboardButton> buttons = new ArrayList<>();
    int i = 1;
    StringBuilder messageTextBuilder = new StringBuilder();
    messageTextBuilder.append("Сейчас загружены следующие алгоритмы: \n");
    for (DotEntity dot : dots) {
      InlineKeyboardButton button = new InlineKeyboardButton();
      button.setText(String.valueOf(i));
      button.setCallbackData(dot.getId());
      buttons.add(button);
      messageTextBuilder.append("**");
      messageTextBuilder.append(i);
      messageTextBuilder.append(") ");
      messageTextBuilder.append(dot.getName());
      messageTextBuilder.append("** ");
      messageTextBuilder.append(dot.getDescription());
      messageTextBuilder.append("\n");
      i += 1;
    }
    messageTextBuilder.append("Выберите один из них по номеру (кнопки ниже)");
    inlineKeyboardMarkup.setKeyboard(rowList(buttons));
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setReplyMarkup(inlineKeyboardMarkup);
    message.setText(messageTextBuilder.toString());
    message.enableMarkdown(true);
    return message;
  }

   */

  /**
   * Размещает кнопки в ряды
   * @param buttons список кнопок, которые нужно разместить
   * @return список рядов
   */
  private static List<List<InlineKeyboardButton>> rowList(List<InlineKeyboardButton> buttons) {
    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
    int i = 0;
    List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
    for(InlineKeyboardButton button : buttons) {
      keyboardButtonsRow.add(button);
      i += 1;
      if (i == 5) {
        rowList.add(new ArrayList<>(keyboardButtonsRow));
        keyboardButtonsRow = new ArrayList<>();
      }
    }
    if (!keyboardButtonsRow.isEmpty()) {
      rowList.add(keyboardButtonsRow);
    }
    return rowList;
  }


  public static SendMessage sendInlineKeyBoardMessage(String chatId) {
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText("Тык");
    inlineKeyboardButton1.setCallbackData("Button \"Тык\" has been pressed");
    inlineKeyboardButton2.setText("Тык2");
    inlineKeyboardButton2.setCallbackData("Button \"Тык2\" has been pressed");
    List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
    List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
    keyboardButtonsRow1.add(inlineKeyboardButton1);
    keyboardButtonsRow2.add(inlineKeyboardButton2);
    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
    rowList.add(keyboardButtonsRow1);
    rowList.add(keyboardButtonsRow2);
    inlineKeyboardMarkup.setKeyboard(rowList);
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setReplyMarkup(inlineKeyboardMarkup);
    message.setText("Привет");
    return message;
  }

}
