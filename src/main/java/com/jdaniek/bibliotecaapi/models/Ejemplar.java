package com.jdaniek.bibliotecaapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ejemplar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ver si es ManyToOne o OneToOne
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    private boolean isLoan = false;
}
