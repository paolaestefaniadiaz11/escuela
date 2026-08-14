package com.paola.escuela.services.curso;

import com.paola.escuela.dto.curso.CursosRequest;
import com.paola.escuela.dto.curso.CursosResponse;
import com.paola.escuela.entities.Curso;
import com.paola.escuela.exceptions.EntidadRelacionadaException;
import com.paola.escuela.mappers.CursoMapper;
import com.paola.escuela.repositories.CursoRepository;
import com.paola.escuela.repositories.GrupoRepository;
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

public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    private CursoMapper cursoMapper;

    private final GrupoRepository grupoRepository;

    @Override
    public List<CursosResponse> listar() {
        log.info("Listando todos los cursos");

        return cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse).toList();
    }

    @Override
    public CursosResponse obtenerPorId(Long id) {
        return cursoMapper.entidadAResponse(obtenerCurso(id));

    }

    @Override
    public CursosResponse registrar(CursosRequest request) {
        log.info("Registrando nuevo curso");

        validarDatosUnicos(request);

        Curso curso = cursoMapper.requestAEntidad(request);

        cursoRepository.save(curso);

        log.info("Nueva aula: {} registrado",curso.getNombre());

        return cursoMapper.entidadAResponse(curso);

    }

    @Override
    public CursosResponse actualizar(CursosRequest request, Long id) {
        Curso curso = obtenerCurso(id);

        log.info("Actualizando el curso: id - {},nombre - {}, descripcion - {},creditos - {} ",id,request.nombre(), request.descripcion(),request.creditos());

        validarCambiosUnicos(request.nombre(),id);

        curso.actualizar(
                request.nombre(),
                request.descripcion(),
                request.creditos()
        );

        log.info("Curso: {} actualizado correctamente", curso.getNombre());

        return cursoMapper.entidadAResponse(curso);

    }

    @Override
    public void eliminar(Long id) {

        Curso curso = obtenerCurso(id);

        log.info("Eliminando curso con id:{} ",id);

        if (grupoRepository.existsByCursoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el curso ya que tiene grupos asignados");

        cursoRepository.delete(curso);

        log.info("Curso: {} eliminado correctamente", curso.getNombre());

    }

    private Curso obtenerCurso(Long id){
        return ServiceUtils.obtenerEntidadOExcepcion(cursoRepository,id,Curso.class);
    }

    private void validarDatosUnicos(CursosRequest request){
        log.info("Validando nombre de curso unico");

        if (cursoRepository.existsByNombre(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe un curso con el nombre: "+request.nombre());

    }

    private void validarCambiosUnicos(String nombre, Long id){
        log.info("Validando que nombre de curso sea unico");

        if (cursoRepository.existsByNombreIgnoreCaseAndIdNot(nombre,id))
            throw new IllegalArgumentException("Ya existe un curso con el nombre: "+nombre);

    }

}
