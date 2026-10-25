package com.jdaniek.bibliotecaapi.services;

import com.jdaniek.bibliotecaapi.dto.AutorRequestDTO;
import com.jdaniek.bibliotecaapi.dto.AutorResponseDTO;
import com.jdaniek.bibliotecaapi.models.Autor;
import com.jdaniek.bibliotecaapi.repositories.AutorRepository;
import com.jdaniek.bibliotecaapi.repositories.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public AutorService(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public AutorResponseDTO crear(AutorRequestDTO autorDTO) {
        String nombre = autorDTO.getNombre().trim();

        if (autorRepository.existsByNombreIgnoreCase(nombre)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un autor con ese nombre");
        }

        Autor autor = new Autor();
        autor.setNombre(nombre);
        return convertirADTO(autorRepository.save(autor));
    }

    public List<AutorResponseDTO> listarTodos() {
        return autorRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public AutorResponseDTO obtenerPorId(Long id) {
        return convertirADTO(buscarPorId(id));
    }

    public AutorResponseDTO actualizar(Long id, AutorRequestDTO autorDTO) {
        Autor autor = buscarPorId(id);
        String nombre = autorDTO.getNombre().trim();

        if (autorRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un autor con ese nombre");
        }

        autor.setNombre(nombre);
        return convertirADTO(autorRepository.save(autor));
    }

    public void eliminar(Long id) {
        Autor autor = buscarPorId(id);

        if (libroRepository.existsByAutorId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar el autor porque tiene libros asociados");
        }

        autorRepository.delete(autor);
    }

    private Autor buscarPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El autor no existe"));
    }

    private AutorResponseDTO convertirADTO(Autor autor) {
        return new AutorResponseDTO(autor.getId(), autor.getNombre());
    }
}
