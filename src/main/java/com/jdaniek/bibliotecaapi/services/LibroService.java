package com.jdaniek.bibliotecaapi.services;

import com.jdaniek.bibliotecaapi.dto.LibroRequestDTO;
import com.jdaniek.bibliotecaapi.dto.LibroResponseDTO;
import com.jdaniek.bibliotecaapi.models.Libro;
import com.jdaniek.bibliotecaapi.repositories.AutorRepository;
import com.jdaniek.bibliotecaapi.repositories.EditorialRepository;
import com.jdaniek.bibliotecaapi.repositories.GeneroRepository;
import com.jdaniek.bibliotecaapi.repositories.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final GeneroRepository generoRepository;
    private final EditorialRepository editorialRepository;

    public LibroService(LibroRepository libroRepository,
                        AutorRepository autorRepository,
                        GeneroRepository generoRepository,
                        EditorialRepository editorialRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.generoRepository = generoRepository;
        this.editorialRepository = editorialRepository;
    }

    private void asignarDatos(Libro libro, LibroRequestDTO libroDTO) {

        libro.setTitulo(libroDTO.getTitulo());
        libro.setIsbn(libroDTO.getIsbn());

        libro.setAutor(autorRepository.findById(libroDTO.getIdAutor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El autor no existe")));


        libro.setGenero(null);
        if (libroDTO.getIdGenero() != null) {
            libro.setGenero(generoRepository.findById(libroDTO.getIdGenero())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El genéro no existe")));
        }

        libro.setEditorial(null);
        if (libroDTO.getIdEditorial() != null) {
            libro.setEditorial(editorialRepository.findById(libroDTO.getIdEditorial())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La editorial no existe")));
        }
    }

    public LibroResponseDTO crear(LibroRequestDTO libroDTO) {
        Libro libro = new Libro();
        asignarDatos(libro, libroDTO);
        return convertirADTO(libroRepository.save(libro));
    }

    public List<LibroResponseDTO> listarTodos() {
        return libroRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    private LibroResponseDTO convertirADTO(Libro libro) {
        return new LibroResponseDTO(
                libro.getId(),
                libro.getTitulo(),
                libro.getIsbn(),
                libro.getAutor().getNombre(),
                libro.getGenero() != null ? libro.getGenero().getNombre() : null,
                libro.getEditorial() != null ? libro.getEditorial().getNombre() : null
        );
    }
}