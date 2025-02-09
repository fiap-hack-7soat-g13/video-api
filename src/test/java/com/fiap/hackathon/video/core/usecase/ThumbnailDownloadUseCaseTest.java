package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ThumbnailDownloadUseCaseTest {

	private final FileStorage fileStorage = mock(FileStorage.class);
	private final ThumbnailDownloadUseCase useCase = new ThumbnailDownloadUseCase(fileStorage);

	@Test
	void execute_shouldReturnInputStreamSourceWhenIdIsValid() {
		Long id = 1L;
		InputStreamSource inputStreamSource = mock(InputStreamSource.class);

		when(fileStorage.download(any(Location.class), eq(id.toString()))).thenReturn(inputStreamSource);

		InputStreamSource result = useCase.execute(id);

		verify(fileStorage).download(any(), eq(id.toString()));
	}

	@Test
	void execute_shouldHandleNullId() {
		assertThrows(NullPointerException.class, () -> useCase.execute(null));
	}

	@Test
	void execute_shouldHandleNonExistentId() {
		Long id = 999L;

		when(fileStorage.download(any(Location.class), eq(id.toString()))).thenReturn(null);

		InputStreamSource result = useCase.execute(id);

		assertNull(result);
		verify(fileStorage).download(any(), eq(id.toString()));
	}
}