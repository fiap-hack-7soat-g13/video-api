package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ThumbnailDownloadUseCaseTest {

	private final FileStorage fileStorage = mock(FileStorage.class);
	private final ThumbnailDownloadUseCase useCase = new ThumbnailDownloadUseCase(fileStorage);

	@Test
	void execute_shouldReturnLinkWhenIdIsValid() {
		Long id = 1L;
		String link = "link";
		Location location = mock(Location.class);

		when(fileStorage.generateDownloadLink(Location.THUMBNAIL, id.toString())).thenReturn(link);

		String result = useCase.execute(id);

		assertEquals(link, result);
		verify(fileStorage).generateDownloadLink(location, id.toString());
	}

	@Test
	void execute_shouldHandleNullId() {
		assertThrows(NullPointerException.class, () -> useCase.execute(null));
	}

	@Test
	void execute_shouldHandleNonExistentId() {
		Long id = 999L;

		when(fileStorage.download(Location.THUMBNAIL, id.toString())).thenReturn(null);

		String result = useCase.execute(id);

		assertNull(result);
		verify(fileStorage).generateDownloadLink(Location.THUMBNAIL, id.toString());
	}

}
