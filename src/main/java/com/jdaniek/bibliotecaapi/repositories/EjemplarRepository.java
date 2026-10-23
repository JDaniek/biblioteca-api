package com.jdaniek.bibliotecaapi.repositories;

import com.jdaniek.bibliotecaapi.models.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {
}
