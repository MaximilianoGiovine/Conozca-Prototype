package com.conozca.prototype.controller;

import com.conozca.prototype.DTO.AuthorDto;
import com.conozca.prototype.model.Author;
import com.conozca.prototype.service.IAuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * Controlador REST para gestionar autores.
 * Expone endpoints HTTP para operaciones CRUD, usando búsqueda por nombre/apellido en lugar de ID.
 * Ideal para clientes que interactúan con nombres directamente.
 */
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private IAuthorService authorService;

    /**
     * Obtiene todos los autores registrados.
     * @return Lista completa de autores.
     * Método GET: /api/authors
     */
    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    /**
     * Busca autores cuyo nombre o apellido contengan el texto ingresado.
     * @param query Texto parcial para búsqueda.
     * @return Lista de autores coincidentes.
     * Método GET: /api/authors/search?query=texto
     */
    @GetMapping("/search")
    public ResponseEntity<List<Author>> searchAuthors(@RequestParam String query) {
        List<Author> authors = authorService.searchAuthors(query);
        return ResponseEntity.ok(authors);
    }

    /**
     * Crea un nuevo autor en la base de datos.
     * @param author Datos del autor a crear.
     * @return Autor creado.
     * Método POST: /api/authors
     * @throws 400 si los datos están incompletos o el autor ya existe.
     */
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author created = authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza el primer autor que coincida con el texto ingresado.
     * @param query Texto parcial para búsqueda.
     * @param updatedAuthor Datos nuevos para actualizar.
     * @return Autor actualizado.
     * Método PUT: /api/authors?query=texto
     * @throws 404 si no se encuentra ningún autor.
     */
    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable String id,
            @RequestBody Author updatedAuthor) {
        try {
            Author updated = authorService.updateAuthor(id, updatedAuthor);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina el primer autor que coincida con el texto ingresado.
     * @param query Texto parcial para búsqueda.
     * Método DELETE: /api/authors?query=texto
     * @throws 404 si no se encuentra ningún autor.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
