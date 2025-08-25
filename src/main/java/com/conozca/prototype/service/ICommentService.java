package com.conozca.prototype.service;

import com.conozca.prototype.model.Comment;

import java.util.List;

public interface ICommentService {

    List<Comment> listarComentarios();

    Comment buscarComentarioPorId(Integer comment_id);

    Comment guardarComentario (Comment comment);

    void eliminarComentarioPorId(Integer comment_id);
}
