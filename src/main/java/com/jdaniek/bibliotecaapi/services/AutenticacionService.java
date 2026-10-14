package com.jdaniek.bibliotecaapi.services;
import com.jdaniek.bibliotecaapi.dto.LoginRequestDTO;
import com.jdaniek.bibliotecaapi.dto.LoginResponseDTO;
import com.jdaniek.bibliotecaapi.models.Administrador;
import com.jdaniek.bibliotecaapi.repositories.AdministradorRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AutenticacionService {

    private final AdministradorRepository administradorRepository;

    public AutenticacionService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    public LoginResponseDTO autenticar(LoginRequestDTO loginDTO) {

        Optional<Administrador> adminOpcional = administradorRepository.findByEmail(loginDTO.getEmail());

        if (adminOpcional.isEmpty()) {
            return new LoginResponseDTO( "El correo electrónico no está registrado", null);
        }

        Administrador admin = adminOpcional.get();

        if (!admin.getPassword().equals(loginDTO.getPassword())) {
            return new LoginResponseDTO( "Contraseña incorrecta", null);
        }

        return new LoginResponseDTO( "¡Ingreso exitoso!", admin.getNombre());
    }
}
