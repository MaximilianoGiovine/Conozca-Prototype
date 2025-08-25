package com.conozca.prototype.service;


import com.conozca.prototype.DTO.AuthorDto;
import com.conozca.prototype.model.Author;

import java.util.List;

/**
 * Interfaz que define las operaciones del servicio de autores.
 * Permite desacoplar la lógica de negocio de su implementación concreta.
 * Ideal para pruebas, escalabilidad y mantenimiento.
 */
public interface IAuthorService {

    /**
     * Obtiene todos los autores registrados.
     * @return Lista completa de autores.
     */
    List<AuthorDto> getAllAuthors();

    /**
     * Busca autores cuyo nombre o apellido contengan el texto ingresado.
     * @param query Texto parcial para búsqueda.
     * @return Lista de autores coincidentes.
     */
    List<Author> searchAuthors(String query);

    /**
     * Crea un nuevo autor en la base de datos.
     * @param author Datos del autor a crear.
     * @return Autor creado.
     * @throws IllegalArgumentException si los datos están incompletos o el autor ya existe.
     */
    Author createAuthor(Author author);

    /**
     * Actualiza el primer autor que coincida con el texto ingresado.
     * @param query Texto parcial para búsqueda.
     * @param updatedAuthor Datos nuevos para actualizar.
     * @return Autor actualizado.
     * @throws EntityNotFoundException si no se encuentra ningún autor.
     */
    Author updateAuthor(String query, Author updatedAuthor);

    /**
     * Elimina el primer autor que coincida con el texto ingresado.
     * @param query Texto parcial para búsqueda.
     * @throws EntityNotFoundException si no se encuentra ningún autor.
     */
    void deleteAuthor(String query);
}