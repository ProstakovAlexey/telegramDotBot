package ap.telegrambot.graph.exeption;

public class DotsEmtryException extends Exception {

  public DotsEmtryException() {
    super("Список алгоритмов пустой");
  }
}
