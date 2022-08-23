package ap.telegrambot.graph;

import ap.telegrambot.graph.exeption.DotNotFoundException;
import ap.telegrambot.graph.exeption.DotsEmtryException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class DotRepositoryMemory implements DotRepository{
  private final Map<String, DotEntity> store;

  @Value("${dot.path}")
  private String path;

  public DotRepositoryMemory() throws IOException {
    store = new ConcurrentHashMap<>();
    Set<String> dotNames = getAllNameInDirectory();
    for (String name : dotNames) {
      try {
        store.put(name, new DotEntity(path, name));
      } catch (Exception e) {
        log.error("Не смог добавить dot {}: {}", name, e.getLocalizedMessage());
      }
    }
  }

  private Set<String> getAllNameInDirectory() throws IOException {
    try (Stream<Path> pathStream = Files.list(Paths.get(path))) {
      return new HashSet<>(pathStream
          .filter( file -> !Files.isDirectory(file))
          .map( file -> FilenameUtils.removeExtension(file.getFileName().toString()))
          .toList());
    }
  }

  @Override
  public List<DotEntity> getAllDots() throws DotsEmtryException {
    if (store.isEmpty()) {
      throw new DotsEmtryException();
    }
    return new ArrayList<>(store.values());
  }

  @Override
  public DotEntity getDotById(String dotId) throws DotNotFoundException {
    DotEntity dotEntity = store.get(dotId);
    if (dotEntity == null) {
      throw new DotNotFoundException(dotId);
    }
    return dotEntity;
  }
}
