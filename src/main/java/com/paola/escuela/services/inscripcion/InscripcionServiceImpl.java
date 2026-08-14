package com.paola.escuela.services.inscripcion;

import com.paola.escuela.dto.inscripciones.InscripcionRequest;
import com.paola.escuela.dto.inscripciones.InscripcionResponse;
import com.paola.escuela.entities.Alumno;
import com.paola.escuela.entities.Grupo;
import com.paola.escuela.entities.Horario;
import com.paola.escuela.entities.Inscripcion;
import com.paola.escuela.exceptions.EntidadRelacionadaException;
import com.paola.escuela.mappers.InscripcionMapper;
import com.paola.escuela.repositories.AlumnoRepository;
import com.paola.escuela.repositories.CalificacionRepository;
import com.paola.escuela.repositories.GrupoRepository;
import com.paola.escuela.repositories.InscripcionRepository;
import com.paola.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final InscripcionMapper inscripcionMapper;
    private final GrupoRepository grupoRepository;
    private final AlumnoRepository alumnoRepository;
    private final CalificacionRepository calificacionRepository;


    @Override
    public List<InscripcionResponse> listar() {
        return inscripcionRepository.findAll().stream()
                .map(inscripcionMapper::entidadAResponse).toList();

    }

    @Override
    public InscripcionResponse obtenerPorId(Long id) {
        return inscripcionMapper.entidadAResponse(obtenerInscripcion(id));

    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {
        log.info("Registrando una nueva inscripcion");

        validarDatosUnicos(request);

        Alumno alumno = alumnoRepository.findById(request.idAlumno())
                .orElseThrow(() -> new IllegalArgumentException("Alumno: " +request.idAlumno() +" no encontrado"));

        Grupo grupo = grupoRepository.findById(request.idGrupo())
                .orElseThrow(() -> new IllegalArgumentException("Grupo: " +request.idGrupo() +" no encontrado"));

        validarCapacidadDisponible(grupo);

        Inscripcion inscripcion = Inscripcion.builder()
                .grupo(grupo)
                .alumno(alumno)
                .build();

        inscripcionRepository.save(inscripcion);

        log.info("Inscripcion registrada exitosamente con idGrupo: {} idAlumno: {}", inscripcion.getGrupo(), inscripcion.getAlumno());

        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {

        log.info("Actualizando inscripción ID: {}", id);

        Inscripcion inscripcion = obtenerInscripcion(id);

        log.info("Actualizando Inscripcion: idAlumno: {}, idGrupo {}",
                request.idAlumno(),
                request.idGrupo());

        validarCambiosUnicos(request.idAlumno(),request.idGrupo());

        Alumno alumno = alumnoRepository.findById(request.idAlumno())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el alumno con el ID: " + request.idAlumno()
                ));

        Grupo grupo = grupoRepository.findById(request.idGrupo())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el grupo con el ID: " + request.idGrupo()
                ));

        inscripcion.actualizar(alumno,
                grupo);


        log.info("Grupo: {} actualizado correctamente", grupo.getId());

        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Inicia eliminado de inscripcion con id:{} ",id);

        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se puede eliminar: No existe la inscripción con el ID: " + id
                ));

        if (inscripcion.getCalificacion() != null)
            throw new IllegalArgumentException(
                    "No se puede eliminar la inscripción ID " + id +
                            " porque ya tiene una calificación asignada."
            );

        inscripcionRepository.delete(inscripcion);

        log.info("Inscripcion: {} eliminada correctamente", inscripcion.getId());


    }

    private Inscripcion obtenerInscripcion(Long id){
        return ServiceUtils.obtenerEntidadOExcepcion(inscripcionRepository,id, Inscripcion.class);
    }

    private void validarDatosUnicos(InscripcionRequest request){
        log.info("Validando que la combinación de Grupo y Alumno");

        log.info("idGrupo:"+ request.idGrupo()+" idAlumno:"+request.idAlumno());

        if (inscripcionRepository.existsByAlumno_Id(
                request.idAlumno()

        ))
            throw new IllegalArgumentException(
                    "Ya existe una inscripcion con un alumno asignado:"+request.idAlumno());

        if (inscripcionRepository.existsByGrupo_Id(
                request.idGrupo()
        ))
            throw new IllegalArgumentException(
                    "Ya existe una inscripcion con un grupo asignado:"+request.idAlumno());

    }

    private void validarCapacidadDisponible(Grupo grupo) {
        if (grupo.getAula() != null && grupo.getAula().getCapacidad() != null) {
            long totalInscritos = inscripcionRepository.countByGrupo_Id(grupo.getId());
            Integer capacidadMaxima = grupo.getAula().getCapacidad();

            if (totalInscritos >= capacidadMaxima) {
                throw new IllegalArgumentException(
                        "No se puede inscribir: el grupo ha alcanzado su capacidad máxima (" + capacidadMaxima + " alumnos)."
                );
            }
        }
    }

    private void validarCambiosUnicos(
            Long idAlumno,
            Long idGrupo
    ){
        log.info("Validando que la combinación de Alumno y Grupo sea única");

        log.info("idAlumno:"+idAlumno+" idGrupo:"+idGrupo);

        if (inscripcionRepository.existsByAlumno_IdAndGrupo_Id(
                idAlumno,
                idGrupo
        ))
            throw new IllegalArgumentException(
                    "Ya existe una incripcion asignado para el Alumno:"+idAlumno+" , Grupo:"+idGrupo);

    }



}
