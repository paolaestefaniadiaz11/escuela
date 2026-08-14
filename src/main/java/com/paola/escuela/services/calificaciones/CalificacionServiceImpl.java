package com.paola.escuela.services.calificaciones;

import com.paola.escuela.dto.calificacion.CalificacionRequest;
import com.paola.escuela.dto.calificacion.CalificacionResponse;
import com.paola.escuela.entities.*;
import com.paola.escuela.mappers.CalificacionMapper;
import com.paola.escuela.repositories.CalificacionRepository;
import com.paola.escuela.repositories.InscripcionRepository;
import com.paola.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j

public class CalificacionServiceImpl implements CalificacionService {
    private final CalificacionRepository calificacionRepository;

    private final CalificacionMapper calificacionMapper;
    private final InscripcionRepository inscripcionRepository;



    @Override
    public List<CalificacionResponse> listar() {
        log.info("Listando todas las aulas");

        return calificacionRepository.findAll().stream()
                .map(calificacionMapper::entidadAResponse).toList();

    }

    @Override
    public CalificacionResponse obtenerPorId(Long id) {
        return calificacionMapper.entidadAResponse(obtenerCalificacion(id));

    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {
        log.info("Registrando nueva calificación para la inscripción ID: {}", request.idInscripcion());

        validarDatosUnicos(request);

        Inscripcion inscripcion = inscripcionRepository.findById(request.idInscripcion())
                .orElseThrow(() -> new IllegalArgumentException("Inscripcion: " +request.idInscripcion() +" no encontrado"));

        Calificacion calificacion = Calificacion.builder()
                .inscripcion(inscripcion)
                .calificacion(request.calificacion())
                .fechaRegistro(LocalDate.now())
                .build();

        Calificacion calificacionGuardada = calificacionRepository.save(calificacion);

        return calificacionMapper.entidadAResponse(calificacionGuardada);}

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request,Long id) {

        Calificacion calificacion = calificacionRepository.findById(id).orElse(null);
        if (calificacion == null) {
            throw new IllegalArgumentException("No se encontró la calificación con el ID: " + id);
        }

        log.info("Actualizando Calificacion ID: {} -> idInscripcion: {}, calificacion: {}",
                id, request.idInscripcion(), request.calificacion());


        validarCambiosUnicos(id, request.calificacion());

        Inscripcion inscripcion = inscripcionRepository.findById(request.idInscripcion())
                .orElseThrow(() -> new IllegalArgumentException("idInscripcion: " +request.idInscripcion() +" no encontrado"));

        calificacion.actualizar(inscripcion, request.calificacion());

        calificacionRepository.save(calificacion);

        log.info("Calificación: {} actualizada correctamente", calificacion.getId());

        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {
        Calificacion calificacion = obtenerCalificacion(id);

        log.info("Inicia eliminado Calificacion con id: {}", id);

        if (calificacion.getInscripcion() != null) {
            calificacion.getInscripcion().desvincularCalificacion();
        }
        calificacionRepository.delete(calificacion);

        log.info("Calificación con ID: {} eliminada correctamente", id);
    }

    private Calificacion obtenerCalificacion(Long id){
        return ServiceUtils.obtenerEntidadOExcepcion(calificacionRepository,id,Calificacion.class);
    }

    private void validarDatosUnicos(CalificacionRequest request){
        if (!inscripcionRepository.existsById(request.idInscripcion())) {
            throw new IllegalArgumentException(
                    "No se encontró la inscripción con el ID: " + request.idInscripcion()
            );
        }


        if (calificacionRepository.existsByInscripcion_Id(request.idInscripcion())) {
            throw new IllegalArgumentException(
                    "La inscripción con el ID: " + request.idInscripcion() + " ya cuenta con una calificación asignada."
            );
        }
    }

    private void validarCambiosUnicos(Long idInscripcion, BigDecimal idCalificacionActual ) {
        if (calificacionRepository.existsByInscripcion_IdAndIdNot(idInscripcion, idCalificacionActual)) {
            throw new IllegalArgumentException(
                    "La inscripción con el ID: " + idInscripcion + " ya está asignada a otra calificación."
            );
        }
    }
}
