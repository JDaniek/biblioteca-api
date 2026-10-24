package com.jdaniek.bibliotecaapi.services;

import com.jdaniek.bibliotecaapi.dto.LibroRequestDTO;
import com.jdaniek.bibliotecaapi.dto.LibroResponseDTO;
import com.jdaniek.bibliotecaapi.models.Ejemplar;
import com.jdaniek.bibliotecaapi.models.Libro;
import com.jdaniek.bibliotecaapi.repositories.*;
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
    private final EjemplarRepository ejemplarRepository;

    public LibroService(LibroRepository libroRepository,
                        AutorRepository autorRepository,
                        GeneroRepository generoRepository,
                        EditorialRepository editorialRepository,
                        EjemplarRepository ejemplarRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.generoRepository = generoRepository;
        this.editorialRepository = editorialRepository;
        this.ejemplarRepository = ejemplarRepository;
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

    public LibroResponseDTO actualizar(Long id, LibroRequestDTO libroDTO) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El libro no existe"));

        asignarDatos(libro, libroDTO);
        return convertirADTO(libroRepository.save(libro));
    }

    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El libro no existe");
        }

        libroRepository.deleteById(id);
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
                libro.getEditorial() != null ? libro.getEditorial().getNombre() : null,
                ejemplarRepository.countByLibroId(libro.getId()),
                ejemplarRepository.countByLibroIdAndPrestadoFalse(libro.getId())
        );
    }
}