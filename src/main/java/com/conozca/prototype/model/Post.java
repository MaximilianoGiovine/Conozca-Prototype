package com.conozca.prototype.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer post_id;

    @ManyToOne
    @JoinColumn(name = "post_author")
    @JsonBackReference("author-post")
    Author author;

    Date date;

    @ManyToOne
    @JoinColumn(name = "post_edition")
    @JsonBackReference ("edition-post")
    Edition edition;

    String post_title;
    String post_subtitle;
    String post_body;
}
