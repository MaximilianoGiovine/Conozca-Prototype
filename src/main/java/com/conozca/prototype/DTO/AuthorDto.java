package com.conozca.prototype.DTO;

import com.conozca.prototype.model.Author;
import com.conozca.prototype.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private long   authorId;
    private String authorName;
    private String authorLastName;
    private String authorDescription;
    private String authorPhoto;
    private String authorEmail;
    private List<String> postTitles;

    public AuthorDto(Author author) {
        this.authorId = author.getAuthor_id();
        this.authorName = author.getAuthorName();
        this.authorLastName = author.getAuthorLastName();
        this.authorDescription = author.getAuthorDescription();
        this.authorPhoto = author.getAuthorPhoto();
        this.authorEmail = author.getAuthorEmail();
        this.postTitles = author.getPosts().stream()
                .map(Post::getPost_title)
                .collect(Collectors.toList());
    }
}
