package com.conozca.prototype.repository;

import com.conozca.prototype.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Author.
 * Proporciona métodos personalizados para búsquedas flexibles por nombre y apellido,
 * además de validaciones de existencia.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Busca autores cuyo nombre o apellido contengan el texto ingresado (ignorando mayúsculas/minúsculas).
     * @param author_name Parte del nombre a buscar.
     * @param author_lastName Parte del apellido a buscar.
     * @return Lista de autores que coincidan con el criterio.
     * Uso común: barra de búsqueda en frontend.
     * Nota: Puede devolver múltiples resultados. No garantiza unicidad.
     */
    List<Author> findByAuthorNameContainingIgnoreCaseOrAuthorLastNameContainingIgnoreCase(String name, String lastName);

    /**
     * Verifica si existe un autor con el nombre, apellido y email especificados.
     * @param AuthorName Nombre exacto.
     * @param author_lastName Apellido exacto.
     * @param author_email Email exacto.
     * @return true si existe al menos un autor con esos datos; false en caso contrario.
     * Uso común: validación previa a la creación para evitar duplicados.
     * Sugerencia: Si querés reforzar unicidad, podés agregar una restricción en la base de datos.
     */
    boolean existsByAuthorNameAndAuthorLastNameAndAuthorEmail(String name, String lastName, String email);
}