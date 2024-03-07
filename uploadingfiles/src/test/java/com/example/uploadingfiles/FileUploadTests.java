package com.example.uploadingfiles;

import com.example.uploadingfiles.storage.StorageFileNotFoundException;
import com.example.uploadingfiles.storage.StorageService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FileUploadTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	StorageService storageService;

	@Test
	void shouldListAllFiles() throws Exception {
		given(
				storageService.loadAll()
		).willReturn(
				Stream.of(Paths.get("first.txt"), Paths.get("second.txt"))
		);

		mockMvc.perform(
				get("/")
		).andExpect(
				model().attribute(
						"files",
						Matchers.contains("http://localhost/files/first.txt", "http://localhost/files/second.txt")
				)
		);
	}

	@Test
	void shouldSaveUploadedFile() throws Exception {
		MockMultipartFile multipartFile =
				new MockMultipartFile("file", "test.txt", "text/plain", "Spring Framework".getBytes());

		mockMvc.perform(
				multipart("/").file(multipartFile)
		).andExpect(
				status().isFound()
		).andExpect(
				header().string("Location", "/")
		);

		then(storageService)
				.should()
				.store(multipartFile);
	}

	@SuppressWarnings("unchecked")
	@Test
	void should404WhenMissingFile() throws Exception {
		given(
				storageService.loadAsResource("test.txt")
		).willThrow(
				StorageFileNotFoundException.class
		);

		mockMvc.perform(
				get("/files/test.txt")
		).andExpect(
				status().isNotFound()
		);
	}


}
