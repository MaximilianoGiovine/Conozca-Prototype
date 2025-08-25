package com.conozca.prototype.service;

import com.conozca.prototype.model.Post;
import com.conozca.prototype.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository repositorioDePosts;

    @Override
    public List<Post> listarPosts(){
     return this.repositorioDePosts.findAll();
    }

    @Override
    public Post buscarPostPorId(Integer post_id){
        Post post = this.repositorioDePosts.findById(post_id).orElse(null);
        return post;
    }

    @Override
    public Post guardarPost(Post post){
        return this.repositorioDePosts.save(post);
    }

    @Override
    public void eliminarPostPorId(Integer post_id){
        this.repositorioDePosts.deleteById(post_id);
    }
}
