package com.jdaniek.bibliotecaapi.repositories;

import com.jdaniek.bibliotecaapi.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}