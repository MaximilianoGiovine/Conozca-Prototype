package com.conozca.prototype.service;

import com.conozca.prototype.model.Post;

import java.util.List;

public interface IPostService {

    List<Post> listarPosts();

    Post buscarPostPorId(Integer post_id);

    Post guardarPost (Post post);

    void eliminarPostPorId(Integer post_id);
}
