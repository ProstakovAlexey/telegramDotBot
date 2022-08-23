package ap.telegrambot.graph.exeption;

public class DotNotFoundException extends Exception {

  public DotNotFoundException(String dotId) {
    super("Алгоритм " + dotId + " не найден.");
  }
}
