package com.conozca.prototype.service;

import com.conozca.prototype.model.Edition;
import com.conozca.prototype.repository.EditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EditionService implements IEditionService{
    @Autowired
    private EditionRepository repositorioEdiciones;

    @Override
    public List<Edition> listarEdiciones(){
        return this.repositorioEdiciones.findAll();
    }

    @Override
    public Edition buscarEdicionPorId(Integer edition_id){
        Edition edition = this.repositorioEdiciones.findById(edition_id).orElse(null);
        return edition;
    }

    @Override
    public Edition guardarEdicion(Edition edition){
        return this.repositorioEdiciones.save(edition);
    }

    @Override
    public void eliminarEdicionPorId(Integer edition_id){
        this.repositorioEdiciones.deleteById(edition_id);
    }

}
