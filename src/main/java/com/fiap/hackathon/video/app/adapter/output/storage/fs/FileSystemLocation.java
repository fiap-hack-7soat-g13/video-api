package com.fiap.hackathon.video.app.adapter.output.storage.fs;

import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;

@Getter
@AllArgsConstructor
public class FileSystemLocation implements Location {

    private final Path directory;

}
