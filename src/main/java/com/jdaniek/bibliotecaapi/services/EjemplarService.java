package com.jdaniek.bibliotecaapi.services;

import com.jdaniek.bibliotecaapi.dto.EjemplarRequestDTO;
import com.jdaniek.bibliotecaapi.dto.EjemplarResponseDTO;
import com.jdaniek.bibliotecaapi.models.Ejemplar;
import com.jdaniek.bibliotecaapi.models.Libro;
import com.jdaniek.bibliotecaapi.repositories.EjemplarRepository;
import com.jdaniek.bibliotecaapi.repositories.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EjemplarService {

    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;

    public EjemplarService(EjemplarRepository ejemplarRepository, LibroRepository libroRepository) {
        this.ejemplarRepository = ejemplarRepository;
        this.libroRepository = libroRepository;
    }

    public List<EjemplarResponseDTO> crear(Long idLibro, EjemplarRequestDTO ejemplarDTO) {
        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El libro no existe"));

        List<Ejemplar> ejemplares = new ArrayList<>();
        for (int i = 0; i < ejemplarDTO.getCantidad(); i++) {
            Ejemplar ejemplar = new Ejemplar();
            ejemplar.setLibro(libro);
            ejemplares.add(ejemplar);
        }

        return ejemplarRepository.saveAll(ejemplares).stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<EjemplarResponseDTO> listarPorLibro(Long idLibro) {
        if (!libroRepository.existsById(idLibro)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El libro no existe");
        }

        return ejemplarRepository.findByLibroId(idLibro).stream()
                .map(this::convertirADTO)
                .toList();
    }

    private EjemplarResponseDTO convertirADTO(Ejemplar ejemplar) {
        return new EjemplarResponseDTO(
                ejemplar.getId(),
                ejemplar.getLibro().getId(),
                ejemplar.isPrestado()
        );
    }
}
