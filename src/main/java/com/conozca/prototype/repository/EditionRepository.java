package com.conozca.prototype.repository;

import com.conozca.prototype.model.Edition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EditionRepository extends JpaRepository<Edition, Integer> {
}
