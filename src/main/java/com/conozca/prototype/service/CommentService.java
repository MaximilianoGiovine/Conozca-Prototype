package com.conozca.prototype.service;

import com.conozca.prototype.model.Comment;
import com.conozca.prototype.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService{

    @Autowired
    private CommentRepository repositorioComentarios;

    @Override
    public List<Comment> listarComentarios(){
        return this.repositorioComentarios.findAll();
    }

    @Override
    public Comment buscarComentarioPorId(Integer comment_id){
        Comment comentario = this.repositorioComentarios.findById(comment_id).orElse(null);
        return comentario;
    }

    @Override
    public Comment guardarComentario(Comment comment){
        return this.repositorioComentarios.save(comment);
    }

    @Override
    public void eliminarComentarioPorId(Integer comment_id){
        this.repositorioComentarios.deleteById(comment_id);
    }
}
