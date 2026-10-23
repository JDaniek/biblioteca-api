package com.jdaniek.bibliotecaapi.services;

import com.jdaniek.bibliotecaapi.dto.LibroRequestDTO;
import com.jdaniek.bibliotecaapi.dto.UserRequestDTO;
import com.jdaniek.bibliotecaapi.dto.UserResponseDTO;
import com.jdaniek.bibliotecaapi.models.User;
import com.jdaniek.bibliotecaapi.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void dataUser(User user, UserRequestDTO userDTO) {
        user.setNombre(userDTO.getNombre());
        user.setApellidos(userDTO.getApellido());
        user.setEmail(userDTO.getEmail());
        user.setSexo(userDTO.getSexo());
        user.setTelefono(userDTO.getTelefono());
    }

    public UserResponseDTO createUser(UserRequestDTO userDTO) {
        User user = new User();
        dataUser(user, userDTO);
        return converToDTO(userRepository.save(user));
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario Inexistente"));
        dataUser(user, userDTO);
        return converToDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Este usuario no exite");
        }
        userRepository.deleteById(id);
    }

    public List<UserResponseDTO> listAll() {
        return userRepository.findAll().stream().map(this::converToDTO).toList();
    }

    private UserResponseDTO converToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNombre(),
                user.getApellidos(),
                user.getSexo(),
                user.getEmail(),
                user.getTelefono()
        );
    }
}