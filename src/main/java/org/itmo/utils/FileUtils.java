package org.itmo.utils;

import java.io.InputStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtils {
    public InputStream getFileFromResource(String fileName) {
        InputStream stream = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
        if (stream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        return stream;
    }
}
