package ap.telegrambot.graph;

import ap.telegrambot.graph.exeption.DotNotFoundException;
import ap.telegrambot.graph.exeption.DotsEmtryException;
import java.util.List;

public interface DotRepository {
  List<DotEntity> getAllDots() throws DotsEmtryException;
  DotEntity getDotById(String dotId) throws DotNotFoundException;

}
