package com.fiap.hackathon.video.core.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Files {

    public static void createTempFileAndExecute(Consumer<Path> consumer) {
        Path tempFile = null;
        try {
            tempFile = createTempFile();
            consumer.accept(tempFile);
        } finally {
            deleteQuietly(tempFile);
        }
    }

    public static <T> T createTempDirAndExecute(Function<Path, T> function) {
        Path tempDir = null;
        try {
            tempDir = createTempDir();
            return function.apply(tempDir);
        } finally {
            deleteQuietly(tempDir);
        }
    }

    public static String detectContentType(Path path) {
        try {
            return new Tika().detect(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path createTempFile() {
        try {
            return java.nio.file.Files.createTempFile(null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path createTempDir() {
        try {
            return java.nio.file.Files.createTempDirectory(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteQuietly(Path path) {
        if (path == null) {
            return;
        }
        try {
            java.nio.file.Files.delete(path);
        } catch (IOException e) {
            log.debug("Failed to delete temporary file {}", path);
        }
    }

}
