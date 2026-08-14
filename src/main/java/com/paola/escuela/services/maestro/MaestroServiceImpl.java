package com.paola.escuela.services.maestro;

import com.paola.escuela.dto.maestro.MaestroRequest;
import com.paola.escuela.dto.maestro.MaestroResponse;
import com.paola.escuela.entities.Maestro;
import com.paola.escuela.exceptions.EntidadRelacionadaException;
import com.paola.escuela.mappers.MaestroMapper;
import com.paola.escuela.repositories.GrupoRepository;
import com.paola.escuela.repositories.MaestroReporitory;
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

public class MaestroServiceImpl implements MaestroService {

    private final MaestroReporitory maestroReporitory;

    private MaestroMapper maestroMapper;

    private final GrupoRepository grupoRepository;

    @Override
    @Transactional (readOnly = true)

    public List<MaestroResponse> listar() {

        log.info("Listando todos los maestros");

        return maestroReporitory.findAll().stream()
                .map(maestroMapper::entidadAResponse).toList();
    }

    @Override
    public MaestroResponse obtenerPorId(Long id) {

        return maestroMapper.entidadAResponse(obtenerMaestro(id));

    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {

        log.info("Registrando nuevo idMaestro");

        validarDatosUnicos(request);

        Maestro maestro = maestroMapper.requestAEntidad(request);

        maestroReporitory.save(maestro);

        log.info("Nuevo idMaestro: {} registrado", maestro.getNombre());

        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {

        Maestro maestro = obtenerMaestro(id);

        log.info("Actualizando idMaestro{}: ",id);

        validarCambiosUnicos(request.email(),request.telefono(),id);

        maestro.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.email(),
                request.telefono()

        );

        log.info("idMaestro {} actualizado correctamente", maestro.getNombre());

        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public void eliminar(Long id) {

        Maestro maestro = obtenerMaestro(id);

        log.info("Eliminando idMaestro con id:{} ",id);

        if (grupoRepository.existsByMaestroId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el idMaestro ya que tiene grupos asignados");


        maestroReporitory.delete(maestro);

        log.info("Maestro {} eliminado correctamente", maestro.getNombre());

    }

    private Maestro obtenerMaestro(Long id){
        return ServiceUtils.obtenerEntidadOExcepcion(maestroReporitory,id, Maestro.class);
    }

    private void validarDatosUnicos(MaestroRequest request){
        log.info("Validando email unico");

        if (maestroReporitory.existsByEmailIgnoreCase(request.email().trim()))
            throw new IllegalArgumentException("Ya existe un idMaestro registrado con el email: "+request.email());

        log.info("Validando telefono unico");

        if (maestroReporitory.existsByTelefono(request.telefono().trim()))
            throw new IllegalArgumentException("Ya existe un idMaestro registrado con el email: "+request.telefono());


    }

    private void validarCambiosUnicos(String email,String telefono, Long id){
        log.info("Validando email unico");

        if (maestroReporitory.existsByEmailIgnoreCaseAndIdNot(email,id))
            throw new IllegalArgumentException("Ya existe un idMaestro registrado con el email: "+email);

        log.info("Validando telefono unico");

        if (maestroReporitory.existsByTelefonoAndIdNot(telefono,id))
            throw new IllegalArgumentException("Ya existe un idMaestro registrado con el email: "+telefono);


    }
}
