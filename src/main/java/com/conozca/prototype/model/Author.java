package com.conozca.prototype.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad JPA que representa un autor en el sistema.
 * Incluye información personal, biográfica y de contacto.
 * Se vincula con múltiples publicaciones mediante una relación OneToMany.
 *
 * Lombok genera automáticamente:
 * - Getters y setters (@Data)
 * - Constructor vacío (@NoArgsConstructor)
 * - Constructor con todos los campos (@AllArgsConstructor)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    /**
     * Identificador único del autor.
     * Se genera automáticamente por la base de datos.
     * No se expone al cliente final, pero es útil internamente para relaciones y persistencia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer author_id;

    /**
     * Nombre del autor.
     * Campo obligatorio para creación y búsqueda.
     * Sugerencia: validar que no esté vacío en el DTO o controlador.
     */
    private String authorName;

    /**
     * Apellido del autor.
     * Campo obligatorio para creación y búsqueda.
     */
    private String authorLastName;

    /**
     * Descripción biográfica o profesional del autor.
     * Campo obligatorio.
     * Puede incluir trayectoria, intereses, publicaciones, etc.
     */
    private String authorDescription;

    /**
     * URL o ruta de la foto del autor.
     * Campo obligatorio.
     * Sugerencia: validar formato si se usa en frontend.
     */
    private String authorPhoto;

    /**
     * Email del autor.
     * Campo obligatorio.
     * Sugerencia: validar formato con @Email en el DTO.
     */
    private String authorEmail;

    /**
     * Relación uno-a-muchos con publicaciones.
     * Un autor puede tener múltiples posts.
     * mappedBy = "author" indica que el campo 'author' en la clase Post es el dueño de la relación.
     * Sugerencia: considerar @JsonIgnore si se expone en API para evitar recursividad.
     */
    @OneToMany(mappedBy = "author")
    @JsonManagedReference("author-post")
    private List<Post> posts;
}
