package com.paola.escuela.services.alumnos;

import com.paola.escuela.dto.alumno.AlumnosRequest;
import com.paola.escuela.dto.alumno.AlumnosResponse;
import com.paola.escuela.entities.Alumno;
import com.paola.escuela.exceptions.EntidadRelacionadaException;
import com.paola.escuela.mappers.AlumnoMapper;
import com.paola.escuela.repositories.AlumnoRepository;
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

public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    private final AlumnoMapper alumnoMapper;
    private final InscripcionRepository inscripcionRepository;


    @Override
    @Transactional(readOnly = true)
    public List<AlumnosResponse> listar() {


        log.info("Listando todos los alumnos");

        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse).toList();

    }

    @Override
    public AlumnosResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }

    @Override
    public AlumnosResponse registrar(AlumnosRequest request) {

        log.info("Registrando alumno nuevo");

        Alumno alumno = alumnoMapper.requestAEntidad(
                request,
                generarEmail(request),
                generarMatricula(request)
        );
        alumnoRepository.save(alumno);

        log.info("Nuevo alumno: {} {} {} registrado", alumno.getApellidoPaterno(), alumno.getApellidoMaterno(), alumno.getNombre());

        return alumnoMapper.entidadAResponse(alumno);

    }

    @Override
    public AlumnosResponse actualizar(AlumnosRequest request, Long id) {
        Alumno alumno = obtenerAlumno(id);

        log.info("Actualizando alumno con id: ", id);


        if (alumno.cambioEnDatos(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno())) {

            alumno.actualizar(
                    request.nombre(),
                    request.apellidoPaterno(),
                    request.apellidoMaterno(),
                    generarEmail(request),
                    generarMatricula(request)
            );

            log.info("Datos ACademicos regenerados para el alumno con el id: {} ", id);

        }

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {

        Alumno alumno = obtenerAlumno(id);

        log.info("Eliminando alumno con id: {}",id);
        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el alumno ya que tiene inscripciones asignadas");

        alumnoRepository.delete(alumno);

        log.info("Alumno eliminado: {}",id);

    }

    private Alumno obtenerAlumno(Long id){
        return ServiceUtils.obtenerEntidadOExcepcion(alumnoRepository,id, Alumno.class);
    }

    private String generarMatricula(AlumnosRequest request){

        log.info("Generando matricula");

        String matricula = alumnoRepository.generarMatricula(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno());

        return matricula;

    }


    private String generarEmail(AlumnosRequest request){

        log.info("Generando email alumno");

        String email = alumnoRepository.generarEmail(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno());

        return email;

    }
}
