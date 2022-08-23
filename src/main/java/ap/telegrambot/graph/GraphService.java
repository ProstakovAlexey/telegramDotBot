package ap.telegrambot.graph;

import ap.telegrambot.graph.exeption.DotFlowException;
import ap.telegrambot.graph.exeption.DotNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GraphService {
  private final DotRepositoryMemory dotRepositoryMemory;

  public List<DotEntity> getAllDots() {
    List<DotEntity> dots = new ArrayList<>();
    try {
      dots = dotRepositoryMemory.getAllDots();
      log.info("Получил  {} графов", dots.size());
    } catch (Exception e) {
      log.error(" Не смог получить список алгоритмов: {}", e.getMessage());
    }
    return dots;
  }

  /**
   * Перемещается по графу на один шаг.
   *
   * Предполагаемое использование: получить из стороннего объекта его текущее положение на
   * графе, например status который хранит имя текущей вершины. И имя следующей вершины, куда
   * объект хочет переместиться. Вызвать данные метод.
   * Метод проверяет: существуют ли текущая и целевая вершина, можно ли перейти от текущей к
   * целевой за один шаг (с учетом наличия и направления связи).
   *
   * @param currentVertex имя текущей вершины
   * @param targetVertex имя целевой вершины
   * @return имя целевой вершины, если удалось переместиться
   */
  public String step(String dotId, String currentVertex, String targetVertex)
      throws DotFlowException, DotNotFoundException {
    DotEntity dotEntity = dotRepositoryMemory.getDotById(dotId);
    Graph<String, DefaultEdge>  directedGraph = dotEntity.getDirectedGraph();
    checkVertex(directedGraph, currentVertex);
    checkVertex(directedGraph, targetVertex);
    DefaultEdge edge = directedGraph.getEdge(currentVertex, targetVertex);
    if (edge == null) {
      throw new DotFlowException(String.format("Перейти от %s к %s не возможно", currentVertex, targetVertex));
    }
    return targetVertex;
  }

  private void checkVertex(Graph<String, DefaultEdge>  directedGraph, String vertexName) throws DotFlowException {
    if (vertexName == null) {
      throw new DotFlowException("Вершина не должна быть null");
    }
    if (!directedGraph.containsVertex(vertexName)) {
      throw new DotFlowException(
          String.format("Вершина %s не существует", vertexName));
    }
  }
}
