package com.conozca.prototype.service;


import com.conozca.prototype.DTO.AuthorDto;
import com.conozca.prototype.model.Author;
import com.conozca.prototype.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la lógica de negocio relacionada con autores.
 * Implementa operaciones CRUD usando búsqueda por nombre y apellido en lugar de ID,
 * para facilitar la interacción con clientes no técnicos.
 */
@Service
public class AuthorService implements IAuthorService {

    @Autowired
    private AuthorRepository repositorioAutores;

    /**
     * Obtiene todos los autores registrados en la base de datos.
     * @return Lista completa de autores.
     * Nota: Si el volumen crece, se recomienda implementar paginación.
     */
    @Override
    public List<AuthorDto> getAllAuthors() {
        return repositorioAutores.findAll().stream()
                .map(AuthorDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca autores cuyo nombre o apellido contengan el texto ingresado (ignorando mayúsculas/minúsculas).
     * @param query Texto de búsqueda parcial.
     * @return Lista de autores que coinciden con el criterio.
     * Nota: Puede devolver múltiples resultados. No garantiza unicidad.
     */
    @Override
    public List<Author> searchAuthors(String query) {
        return repositorioAutores.findByAuthorNameContainingIgnoreCaseOrAuthorLastNameContainingIgnoreCase(query, query);
    }

    /**
     * Crea un nuevo autor en la base de datos, validando que los campos esenciales estén completos
     * y que no exista un autor duplicado con el mismo nombre, apellido y email.
     * @param author Autor a crear.
     * @return Autor guardado.
     * @throws IllegalArgumentException si los datos están incompletos o el autor ya existe.
     * Sugerencia: Podés usar DTOs con anotaciones @Valid para delegar parte de esta validación.
     */
    @Override
    public Author createAuthor(Author author) {
        if (author.getAuthorName() == null || author.getAuthorLastName() == null ||
                author.getAuthorDescription() == null || author.getAuthorEmail() == null || author.getAuthorPhoto() == null) {
            throw new IllegalArgumentException("Los datos ingresados son incorrectos o están incompletos");
        }

        boolean exists = repositorioAutores.existsByAuthorNameAndAuthorLastNameAndAuthorEmail(
                author.getAuthorName(), author.getAuthorLastName(), author.getAuthorEmail());

        if (exists) {
            throw new IllegalArgumentException("El autor ya existe");
        }

        return repositorioAutores.save(author);
    }

    /**
     * Actualiza el primer autor que coincida parcialmente con el nombre o apellido ingresado.
     * @param query Texto de búsqueda parcial.
     * @param updatedAuthor Datos nuevos para actualizar.
     * @return Autor actualizado.
     * @throws EntityNotFoundException si no se encuentra ningún autor que coincida.
     * Advertencia: Si hay múltiples coincidencias, solo se actualiza el primero.
     * Sugerencia: Podés mostrar la lista de coincidencias al cliente antes de actualizar.
     */
    @Override
    public Author updateAuthor(String query, Author updatedAuthor) {
        List<Author> matchingAuthors = repositorioAutores
                .findByAuthorNameContainingIgnoreCaseOrAuthorLastNameContainingIgnoreCase(query, query);

        if (matchingAuthors.isEmpty()) {
            throw new EntityNotFoundException("Autor no encontrado con nombre y apellido que contengan: " + query);
        }

        Author existingAuthor = matchingAuthors.get(0); // Tomamos el primero

        existingAuthor.setAuthorName(updatedAuthor.getAuthorName());
        existingAuthor.setAuthorLastName(updatedAuthor.getAuthorLastName());
        existingAuthor.setAuthorDescription(updatedAuthor.getAuthorDescription());
        existingAuthor.setAuthorPhoto(updatedAuthor.getAuthorPhoto());
        existingAuthor.setAuthorEmail(updatedAuthor.getAuthorEmail());

        return repositorioAutores.save(existingAuthor);
    }

    /**
     * Elimina el primer autor que coincida parcialmente con el nombre o apellido ingresado.
     * @param query Texto de búsqueda parcial.
     * @throws EntityNotFoundException si no se encuentra ningún autor que coincida.
     * Advertencia: Si hay múltiples coincidencias, solo se elimina el primero.
     * Sugerencia: Validar que el autor no esté vinculado a publicaciones antes de eliminar.
     */
    @Override
    public void deleteAuthor(String query) {
        List<Author> matchingAuthors = repositorioAutores
                .findByAuthorNameContainingIgnoreCaseOrAuthorLastNameContainingIgnoreCase(query, query);

        if (matchingAuthors.isEmpty()) {
            throw new EntityNotFoundException("No se encontró ningún autor con nombre y apellido que contengan: " + query);
        }

        Author authorToDelete = matchingAuthors.get(0); // Tomamos el primero

        repositorioAutores.delete(authorToDelete);
    }
}
