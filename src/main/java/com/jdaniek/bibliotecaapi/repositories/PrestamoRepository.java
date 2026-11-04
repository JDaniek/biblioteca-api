package com.jdaniek.bibliotecaapi.repositories;

import com.jdaniek.bibliotecaapi.models.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByFechaDevolucionRealIsNull();
}
