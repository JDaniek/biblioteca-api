package com.jdaniek.bibliotecaapi.services;

import com.jdaniek.bibliotecaapi.dto.PrestamoRequest;
import com.jdaniek.bibliotecaapi.dto.PrestamoResponse;
import com.jdaniek.bibliotecaapi.models.Prestamo;
import com.jdaniek.bibliotecaapi.models.Ejemplar;
import com.jdaniek.bibliotecaapi.models.User;
import com.jdaniek.bibliotecaapi.repositories.PrestamoRepository;
import com.jdaniek.bibliotecaapi.repositories.EjemplarRepository;
import com.jdaniek.bibliotecaapi.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final EjemplarRepository ejemplarRepository;
    private final UserRepository userRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, EjemplarRepository ejemplarRepository, UserRepository userRepository) {
        this.prestamoRepository = prestamoRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PrestamoResponse crearPrestamo(PrestamoRequest request) {
        LocalDateTime hoy = LocalDateTime.now();

        if (request.getFechaDevolucionAsignada() == null) {
            throw new IllegalArgumentException("Debes seleccionar una fecha límite en el calendario.");
        }

        long diasSolicitados = ChronoUnit.DAYS.between(hoy.toLocalDate(), request.getFechaDevolucionAsignada().toLocalDate());

        if (diasSolicitados > 21) {
            throw new IllegalArgumentException("La fecha seleccionada supera el límite máximo de 3 semanas (21 días).");
        }
        if (diasSolicitados < 1) {
            throw new IllegalArgumentException("La fecha de vencimiento debe ser posterior al día de hoy.");
        }

        User usuario = userRepository.findById(request.getIdUsuario()).orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + request.getIdUsuario() + " no existe."));

        Ejemplar ejemplar = ejemplarRepository.findById(request.getIdEjemplar()).orElseThrow(() -> new IllegalArgumentException("El ejemplar con ID " + request.getIdEjemplar() + " no existe."));

        if (ejemplar.isPrestado()) {
            throw new IllegalStateException("Este ejemplar ya se encuentra prestado actualmente.");
        }

        ejemplar.setPrestado(true);
        ejemplarRepository.save(ejemplar);

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaDevolucionAsignada(request.getFechaDevolucionAsignada());
        prestamo.setEstatus("ACTIVO");

        Prestamo prestamoGuardado = prestamoRepository.save(prestamo);

        return convertirADto(prestamoGuardado);
    }

    @Transactional
    public PrestamoResponse registrarDevolucion(Long idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo).orElseThrow(() -> new IllegalArgumentException("El préstamo con ID " + idPrestamo + " no existe."));

        if (prestamo.getFechaDevolucionReal() != null) {
            throw new IllegalStateException("Este préstamo ya fue devuelto con anterioridad.");
        }

        prestamo.setFechaDevolucionReal(LocalDateTime.now());
        prestamo.setEstatus("DEVUELTO");

        Ejemplar ejemplar = prestamo.getEjemplar();
        ejemplar.setPrestado(false);
        ejemplarRepository.save(ejemplar);

        return convertirADto(prestamoRepository.save(prestamo));
    }

    public List<PrestamoResponse> listarTodos() {
        return prestamoRepository.findAll().stream().map(this::convertirADto).collect(Collectors.toList());
    }

    public List<PrestamoResponse> listarActivos() {
        return prestamoRepository.findByFechaDevolucionRealIsNull().stream().map(this::convertirADto).collect(Collectors.toList());
    }

    private PrestamoResponse convertirADto(Prestamo prestamo) {
        PrestamoResponse response = new PrestamoResponse();
        response.setIdPrestamo(prestamo.getId());
        response.setIdUsuario(prestamo.getUsuario().getId());
        response.setNombreUsuario(prestamo.getUsuario().getNombre() + " " + prestamo.getUsuario().getApellidos());
        response.setIdEjemplar(prestamo.getEjemplar().getId());
        response.setTituloLibro(prestamo.getEjemplar().getLibro().getTitulo());
        response.setEstatus(prestamo.getEstatus());
        response.setFechaPrestamo(prestamo.getFechaPrestamo());
        response.setFechaDevolucionAsignada(prestamo.getFechaDevolucionAsignada());
        response.setFechaDevolucionReal(prestamo.getFechaDevolucionReal());
        return response;
    }
}
