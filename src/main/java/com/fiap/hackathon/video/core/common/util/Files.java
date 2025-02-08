package com.fiap.hackathon.video.core.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

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

    public static Path createTempFile() {
        try {
            return java.nio.file.Files.createTempFile(null, null);
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
