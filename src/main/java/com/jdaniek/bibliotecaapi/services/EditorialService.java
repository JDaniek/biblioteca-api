package com.jdaniek.bibliotecaapi.services;

import com.jdaniek.bibliotecaapi.dto.EditorialRequestDTO;
import com.jdaniek.bibliotecaapi.dto.EditorialResponseDTO;
import com.jdaniek.bibliotecaapi.models.Editorial;
import com.jdaniek.bibliotecaapi.repositories.EditorialRepository;
import com.jdaniek.bibliotecaapi.repositories.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EditorialService {

    private final EditorialRepository editorialRepository;
    private final LibroRepository libroRepository;

    public EditorialService(EditorialRepository editorialRepository, LibroRepository libroRepository) {
        this.editorialRepository = editorialRepository;
        this.libroRepository = libroRepository;
    }

    public EditorialResponseDTO crear(EditorialRequestDTO editorialDTO) {
        String nombre = editorialDTO.getNombre().trim();

        if (editorialRepository.existsByNombreIgnoreCase(nombre)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una editorial con ese nombre");
        }

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        return convertirADTO(editorialRepository.save(editorial));
    }

    public List<EditorialResponseDTO> listarTodos() {
        return editorialRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public EditorialResponseDTO obtenerPorId(Long id) {
        return convertirADTO(buscarPorId(id));
    }

    public EditorialResponseDTO actualizar(Long id, EditorialRequestDTO editorialDTO) {
        Editorial editorial = buscarPorId(id);
        String nombre = editorialDTO.getNombre().trim();

        if (editorialRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una editorial con ese nombre");
        }

        editorial.setNombre(nombre);
        return convertirADTO(editorialRepository.save(editorial));
    }

    public void eliminar(Long id) {
        Editorial editorial = buscarPorId(id);

        if (libroRepository.existsByEditorialId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar la editorial porque tiene libros asociados");
        }

        editorialRepository.delete(editorial);
    }

    private Editorial buscarPorId(Long id) {
        return editorialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La editorial no existe"));
    }

    private EditorialResponseDTO convertirADTO(Editorial editorial) {
        return new EditorialResponseDTO(editorial.getId(), editorial.getNombre());
    }
}
