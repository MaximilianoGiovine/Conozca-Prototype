package com.conozca.prototype.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer edition_id;
    String edition_year;
    String edition_section;

    @OneToMany(mappedBy = "edition")
    @JsonManagedReference("edition-post")
    private List<Post> posts;
}
