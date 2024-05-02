package org.example.task5.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtil {

  public static void writeToFile(Path path, String payload) throws IOException {
    if (path.getParent() != null && !Files.exists(path.getParent())) {
      Files.createDirectories(path.getParent());
    }
    Files.writeString(path, payload);
  }

  public static String readFromFile(Path path) throws IOException {
    if (Files.exists(path)) {
      return Files.readString(path);
    } else {
      String message = String.format("Failed to red file %s.", path);
      log.error(message);
      throw new FileNotFoundException(message);
    }
  }

}
