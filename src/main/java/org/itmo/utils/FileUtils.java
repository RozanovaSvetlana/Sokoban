package org.itmo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtils {
    
    /**
     * Opens file from resource folders
     *
     * @param fileName name of file from resources to open
     * @return InputStream with content of file
     */
    public InputStream getFileFromResource(String fileName) {
        InputStream stream = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
        if (stream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        return stream;
    }
    
    /**
     * Get file by name for input
     *
     * @param fileName name of file to open
     * @return InputStream with content of file
     */
    public InputStream getFile(String fileName) throws IOException {
        return Files.newInputStream(Path.of(fileName));
    }
    
    /**
     * Gets the contents of a file by name
     *
     * @param fileName name of file
     * @return File content as a string
     * @throws IOException
     */
    public String getAllFileAsString(String fileName) throws IOException {
        return Files.readAllLines(Path.of(fileName)).stream().collect(Collectors.joining("\n"));
    }
    
    /**
     * Returns the name of the file at the specified path
     *
     * @param file - file path
     * @return
     */
    public String getFileName(String file) {
        return Paths.get(file).getFileName().toString();
    }
}
