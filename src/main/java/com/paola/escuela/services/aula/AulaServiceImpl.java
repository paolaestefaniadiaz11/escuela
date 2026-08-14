package com.paola.escuela.services.aula;

import com.paola.escuela.dto.aula.AulaRequest;
import com.paola.escuela.dto.aula.AulaResponse;
import com.paola.escuela.entities.Aula;
import com.paola.escuela.exceptions.EntidadRelacionadaException;
import com.paola.escuela.mappers.AulaMapper;
import com.paola.escuela.repositories.AulaRepository;
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

public class AulaServiceImpl implements AulaService{

    private final AulaRepository aulaRepository;

    private AulaMapper aulaMapper;

    private final GrupoRepository grupoRepository;

    @Override
    public List<AulaResponse> listar() {
        log.info("Listando todas las aulas");

        return aulaRepository.findAll().stream()
                .map(aulaMapper::entidadAResponse).toList();
    }

    @Override
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entidadAResponse(obtenerAula(id));

    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Registrando nueva aula");

        validarDatosUnicos(request);

        Aula aula = aulaMapper.requestAEntidad(request);

        aulaRepository.save(aula);

        log.info("Nueva aula: {} registrado",aula.getNombre());

        return aulaMapper.entidadAResponse(aula);

    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {

        Aula aula = obtenerAula(id);

        log.info("Actualizando aula: id - {},nombre - {}, capacidad - {} ",id,request.nombre(), request.capacidad());

        validarCambiosUnicos(request.nombre(),id);

        aula.actualizar(
                request.nombre(),
                request.capacidad()
        );

        log.info("Aula: {} actualizada correctamente", aula.getNombre());

        return aulaMapper.entidadAResponse(aula);

    }

    @Override
    public void eliminar(Long id) {

        Aula aula = obtenerAula(id);

        log.info("Eliminando Aula con id:{} ",id);

        if (grupoRepository.existsById(id))
            throw new EntidadRelacionadaException("No se puede eliminar el aula ya que tiene grupos asignados");


        aulaRepository.delete(aula);

        log.info("Aula: {} eliminada correctamente", aula.getNombre());

    }

    private Aula obtenerAula(Long id){
        return ServiceUtils.obtenerEntidadOExcepcion(aulaRepository,id,Aula.class);
    }

    private void validarDatosUnicos(AulaRequest request){
        log.info("Validando nombre de aula unico");

        if (aulaRepository.existsByNombre(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe un aula con el nombre: "+request.nombre());

    }

    private void validarCambiosUnicos(String nombre, Long id){
        log.info("Validando nombre de aula unico");

        if (aulaRepository.existsByNombreIgnoreCaseAndIdNot(nombre,id))
            throw new IllegalArgumentException("Ya existe un aula con el nombre: "+nombre);

    }
}
