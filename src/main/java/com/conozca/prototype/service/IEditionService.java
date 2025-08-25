package com.conozca.prototype.service;

import com.conozca.prototype.model.Edition;

import java.util.List;

public interface IEditionService {

    List<Edition> listarEdiciones();

    Edition buscarEdicionPorId(Integer edition_id);

    Edition guardarEdicion(Edition edition);

    void eliminarEdicionPorId(Integer edition_id);
}
