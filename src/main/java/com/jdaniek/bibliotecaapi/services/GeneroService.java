package com.jdaniek.bibliotecaapi.services;

import com.jdaniek.bibliotecaapi.dto.GeneroRequestDTO;
import com.jdaniek.bibliotecaapi.dto.GeneroResponseDTO;
import com.jdaniek.bibliotecaapi.models.Genero;
import com.jdaniek.bibliotecaapi.repositories.GeneroRepository;
import com.jdaniek.bibliotecaapi.repositories.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;
    private final LibroRepository libroRepository;

    public GeneroService(GeneroRepository generoRepository, LibroRepository libroRepository) {
        this.generoRepository = generoRepository;
        this.libroRepository = libroRepository;
    }

    public GeneroResponseDTO crear(GeneroRequestDTO generoDTO) {
        String nombre = generoDTO.getNombre().trim();

        if (generoRepository.existsByNombreIgnoreCase(nombre)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un género con ese nombre");
        }

        Genero genero = new Genero();
        genero.setNombre(nombre);
        return convertirADTO(generoRepository.save(genero));
    }

    public List<GeneroResponseDTO> listarTodos() {
        return generoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public GeneroResponseDTO obtenerPorId(Long id) {
        return convertirADTO(buscarPorId(id));
    }

    public GeneroResponseDTO actualizar(Long id, GeneroRequestDTO generoDTO) {
        Genero genero = buscarPorId(id);
        String nombre = generoDTO.getNombre().trim();

        if (generoRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un género con ese nombre");
        }

        genero.setNombre(nombre);
        return convertirADTO(generoRepository.save(genero));
    }

    public void eliminar(Long id) {
        Genero genero = buscarPorId(id);

        if (libroRepository.existsByGeneroId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar el género porque tiene libros asociados");
        }

        generoRepository.delete(genero);
    }

    private Genero buscarPorId(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El género no existe"));
    }

    private GeneroResponseDTO convertirADTO(Genero genero) {
        return new GeneroResponseDTO(genero.getId(), genero.getNombre());
    }
}
