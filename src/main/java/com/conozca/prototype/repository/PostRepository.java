package com.conozca.prototype.repository;

import com.conozca.prototype.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
