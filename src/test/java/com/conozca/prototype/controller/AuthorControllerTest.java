package com.conozca.prototype.controller;

import com.conozca.prototype.DTO.AuthorDto;
import com.conozca.prototype.TestConfig;
import com.conozca.prototype.exception.AuthorAlreadyExistsException;
import com.conozca.prototype.exception.AuthorNotFoundException;
import com.conozca.prototype.model.Author;
import com.conozca.prototype.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@Import(TestConfig.class)
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void shouldReturnAllAuthors() throws Exception {
        AuthorDto a1 = new AuthorDto();
        a1.setAuthorName("Juan");
        a1.setAuthorLastName("Pérez");
        a1.setAuthorDescription("Autor chileno");
        a1.setAuthorPhoto("foto1.jpg");
        a1.setAuthorEmail("juan@example.com");

        AuthorDto a2 = new AuthorDto();
        a2.setAuthorName("Ana");
        a2.setAuthorLastName("Gómez");
        a2.setAuthorDescription("Autora argentina");
        a2.setAuthorPhoto("foto2.jpg");
        a2.setAuthorEmail("ana@example.com");

        List<AuthorDto> authors = Arrays.asList(a1, a2);

        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].authorName").value("Juan"))
                .andExpect(jsonPath("$[1].authorName").value("Ana"));

    }

    @Test
    void searchAuthors_OK() throws Exception {
        Author a1 = new Author(3, "Carlos", "López", "Autor peruano", "foto3.jpg", "carlos@example.com", null);
        List<Author> result = List.of(a1);

        when(authorService.searchAuthors("Carlos")).thenReturn(result);

        mockMvc.perform(get("/api/authors/search").param("query", "Carlos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].lastName").value("López"));
    }

    @Test
    void createAuthor_OK() throws Exception {
        Author input = new Author(null, "Laura", "Méndez", "Autora mexicana", "foto4.jpg", "laura@example.com", null);
        Author saved = new Author(3, "Laura", "Méndez", "Autora mexicana", "foto4.jpg", "laura@example.com", null);

        when(authorService.createAuthor(any())).thenReturn(saved);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Laura"))
                .andExpect(jsonPath("$.lastName").value("Méndez"))
                .andExpect(jsonPath("$.description").value("Autora mexicana"))
                .andExpect(jsonPath("$.photo").value("foto4.jpg"))
                .andExpect(jsonPath("$.email").value("laura@example.com"))
                .andExpect(jsonPath("$.posts").value(nullValue()));
    }

    @Test
    void createAuthor_Conflict() throws Exception {
        Author input = new Author(null, "Laura", "Méndez", "Autora mexicana", "foto4.jpg", "laura@example.com", null);

        when(authorService.createAuthor(any())).thenThrow(new AuthorAlreadyExistsException("El autor ya existe."));

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("El autor ya existe."));
    }

    @Test
    void updateAuthor_OK() throws Exception {
        int id = 1;
        Author input = new Author(null, "Laura", "Méndez", "Actualizada", "foto4.jpg", "laura@example.com", null);
        Author updated = new Author(id, "Laura", "Méndez", "Actualizada", "foto4.jpg", "laura@example.com", null);

        when(authorService.updateAuthor(String.valueOf(id), input)).thenReturn(updated);

        mockMvc.perform(put("/api/authors/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Laura"))
                .andExpect(jsonPath("$.lastName").value("Méndez"))
                .andExpect(jsonPath("$.description").value("Actualizada"))
                .andExpect(jsonPath("$.photo").value("foto4.jpg"))
                .andExpect(jsonPath("$.email").value("laura@example.com"));
    }

    @Test
    void updateAuthor_NotFound() throws Exception {
        int id = 99;
        Author input = new Author(null, "Laura", "Méndez", "Actualizada", "foto4.jpg", "laura@example.com", null);

        when(authorService.updateAuthor(String.valueOf(id), input)).thenThrow(new AuthorNotFoundException());

        mockMvc.perform(put("/api/authors/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAuthor_OK() throws Exception {
        int id = 1;

        doNothing().when(authorService).deleteAuthor(String.valueOf(id));

        mockMvc.perform(delete("/api/authors/{id}", id))
                .andExpect(status().isNoContent());

        verify(authorService).deleteAuthor(String.valueOf(id));
    }

    @Test
    void deleteAuthor_NotFound() throws Exception {
        int id = 99;

        doThrow(new AuthorNotFoundException()).when(authorService).deleteAuthor(String.valueOf(id));

        mockMvc.perform(delete("/api/authors/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAuthor_InvalidEmail() throws Exception {
        Author input = new Author(null, "Laura", "Méndez", "Autora mexicana", "foto4.jpg", "correo-invalido", null);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createAuthor_MissingRequiredFields() throws Exception {
        Author input = new Author(null, "", "", "", "", "", null);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}