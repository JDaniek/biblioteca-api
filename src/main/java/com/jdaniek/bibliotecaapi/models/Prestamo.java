package com.jdaniek.bibliotecaapi.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "prestamo")
@Data
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "id_ejemplar", nullable = false)
    private Ejemplar ejemplar;

    @Column(nullable = false)
    private String estatus;

    @Column(name = "fecha_prestamo", nullable = false, updatable = false)
    private LocalDateTime fechaPrestamo;

    @Column(name = "fecha_devolucion_asignada", nullable = false)
    private LocalDateTime fechaDevolucionAsignada;

    @Column(name = "fecha_devolucion_real")
    private LocalDateTime fechaDevolucionReal;

    @PrePersist
    protected void onCreate() {
        if (this.fechaPrestamo == null) {
            this.fechaPrestamo = LocalDateTime.now();
        }
    }
}
