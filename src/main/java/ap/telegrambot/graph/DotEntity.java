package ap.telegrambot.graph;

import ap.telegrambot.graph.exeption.DotFlowException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.dot.DOTImporter;

public class DotEntity {
  private final ResourceBundle bundle = ResourceBundle.getBundle("message");
  @Getter
  private final Graph<String, DefaultEdge> directedGraph;
  @Getter
  private final String name;
  @Getter
  private final String description;
  @Getter
  private final String id;

  public DotEntity(String path, String id) throws DotFlowException, IOException {
    this.id = id;

    // Load dot from file
    Path dotFilePath = Path.of(path, id + ".dot");
    File file = dotFilePath.toFile();
    directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    DOTImporter<String, DefaultEdge> dotImporter = new DOTImporter<>();
    dotImporter.setVertexFactory(label -> label);
    dotImporter.importGraph(directedGraph, file);

    // Load name and description
    Path descriptionFilePath = Path.of(path, id + ".txt");
    List<String> lines = Files.readAllLines(descriptionFilePath);
    if (lines.isEmpty()) {
      throw new DotFlowException(String.format("Файл %s должен содержать название (первая строка, обязательно) и описание (последующие строки, необязательно)", descriptionFilePath));
    }
    name = lines.get(0);
    if (lines.size() > 1) {
      StringBuilder descriptionBuilder = new StringBuilder();
      descriptionBuilder.append(lines.get(1));
      for (int i = 2; i<lines.size(); i++) {
        descriptionBuilder.append("\n");
        descriptionBuilder.append(lines.get(i));
      }
      description = descriptionBuilder.toString();
    } else {
      description = "";
    }
  }



}
