package com.paola.escuela.services.grupo;

import com.paola.escuela.dto.grupo.GrupoRequest;
import com.paola.escuela.dto.grupo.GrupoResponse;
import com.paola.escuela.entities.Aula;
import com.paola.escuela.entities.Curso;
import com.paola.escuela.entities.Grupo;
import com.paola.escuela.entities.Maestro;
import com.paola.escuela.exceptions.EntidadRelacionadaException;
import com.paola.escuela.mappers.GrupoMapper;
import com.paola.escuela.repositories.*;
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

public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;

    private final GrupoMapper grupoMapper;
    private final CursoRepository cursoRepository;
    private final MaestroReporitory maestroRepository;
    private final AulaRepository aulaRepository;
    private final InscripcionRepository inscripcionRepository;
    private final HorarioRepository horarioRepository;


    @Override
    public List<GrupoResponse> listar() {

        return grupoRepository.findAll().stream()
                .map(grupoMapper::entidadAResponse).toList();

    }

    @Override
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entidadAResponse(obtenerGrupo(id));

    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {
        log.info("Registrando nuevo grupo");

        validarDatosUnicos(request);

        Curso curso = cursoRepository.findById(request.idCurso())
                .orElseThrow(() -> new IllegalArgumentException("Curso: " +request.idCurso() +" no encontrado"));

        Maestro maestro = maestroRepository.findById(request.idMaestro())
                .orElseThrow(() -> new IllegalArgumentException("Maestro: "+request.idMaestro()+" no encontrado"));

        Aula aula = aulaRepository.findById(request.idAula())
                .orElseThrow(() -> new IllegalArgumentException("Aula: "+request.idAula()+" no encontrada"));

        Grupo grupo = Grupo.builder()
                .curso(curso)
                .maestro(maestro)
                .aula(aula)
                .periodo(request.periodo())
                .build();

        Grupo grupoGuardado = grupoRepository.save(grupo);

        log.info("Nuevo grupo registrado con ID: {}", grupoGuardado.getId());

        return grupoMapper.entidadAResponse(grupoGuardado);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {

        Grupo grupo = obtenerGrupo(id);

        log.info("Actualizando Grupo: Curso: {}, Maestro {}, Aula {} y Período {}",
                id,
                request.idCurso(),
                request.idMaestro(),
                request.idAula(),
                request.periodo());

        validarCambiosUnicos(request.idCurso(),request.idMaestro(),request.idAula(),request.periodo(),id);

        Curso curso = cursoRepository.findById(request.idCurso())
                .orElseThrow(() -> new IllegalArgumentException("Curso: " +request.idCurso() +" no encontrado"));

        Maestro maestro = maestroRepository.findById(request.idMaestro())
                .orElseThrow(() -> new IllegalArgumentException("Maestro: "+request.idMaestro()+" no encontrado"));
        Aula aula = aulaRepository.findById(request.idAula())
                .orElseThrow(() -> new IllegalArgumentException("Aula: "+request.idAula()+" no encontrada"));

        grupo.actualizar(curso,
                         maestro,
                         aula, request.periodo());


        log.info("Grupo: {} actualizado correctamente", grupo.getId());

        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {

        Grupo grupo = obtenerGrupo(id);

        log.info("Inicia eliminado Grupo con id:{} ",id);


        if (inscripcionRepository.existsByGrupo_Id(id))
            throw new EntidadRelacionadaException("No se puede eliminar el grupo ya que tiene inscripciones asignadas");

        if (horarioRepository.existsByGrupo_Id(id))
            throw new EntidadRelacionadaException("No se puede eliminar el grupo ya que tiene horarios asignados");

        grupoRepository.delete(grupo);

        log.info("Grupo: {} eliminada correctamente", grupo.getId());


    }

    private Grupo obtenerGrupo(Long id){
        return ServiceUtils.obtenerEntidadOExcepcion(grupoRepository,id,Grupo.class);
    }

    private void validarDatosUnicos(GrupoRequest request){
        log.info("Validando que la combinación de Curso, Maestro, Aula y Período sea única");

        log.info("idCurso:"+ request.idCurso()+" idMaestro:"+request.idMaestro()+" idAula:"+request.idAula());


        if (grupoRepository.existsByCurso_IdAndMaestro_IdAndAula_IdAndPeriodo(
                request.idCurso(),
                request.idMaestro(),
                request.idAula(),
                request.periodo()


        ))
            throw new IllegalArgumentException(
                    "Ya existe un grupo asignado para el Curso:"+request.idCurso()+" , Maestro:"+request.idMaestro()+" ,Aula:" +request.idAula()+" ,Periodo:"+request.periodo());

    }

    private void validarCambiosUnicos(
            Long idCurso,
            Long idMaestro,
            Long idAula,
            String periodo,
            Long id
    ){
        log.info("Validando que la combinación de Curso, Maestro, Aula y Período sea única");

        log.info("idCurso:"+idCurso+" idMaestro:"+idMaestro+" idAula:"+idAula+" periodo:"+periodo);


        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
                idCurso,
                idMaestro,
                idAula,
                periodo,
                id
        ))
            throw new IllegalArgumentException(
                    "Ya existe un grupo asignado para el Curso:"+idCurso+" , Maestro:"+idMaestro+" , Aula:" +idAula+" , Periodo:"+periodo);

    }
}
