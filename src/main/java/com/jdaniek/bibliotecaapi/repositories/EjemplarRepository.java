package com.jdaniek.bibliotecaapi.repositories;

import com.jdaniek.bibliotecaapi.models.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {
    List<Ejemplar> findByLibroId(Long idLibro);
    long countByLibroId(Long idLibro);
    long countByLibroIdAndPrestadoFalse(Long idLibro);
}
