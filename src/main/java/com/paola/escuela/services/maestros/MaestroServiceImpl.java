package com.paola.escuela.services.maestros;

import com.paola.escuela.dto.maestros.MaestroRequest;
import com.paola.escuela.dto.maestros.MaestroResponse;
import com.paola.escuela.entities.Maestros;
import com.paola.escuela.mappers.MaestroMapper;
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

    @Override
    @Transactional (readOnly = true)

    public List<MaestroResponse> listar() {

        log.info("Listando todos los maestros");

        return maestroReporitory.findAll().stream()
                .map(maestroMapper::entidadAResponse).toList();
    }

    @Override
    public MaestroResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {

        log.info("Registrando nuevo maestro");

        validarDatosUnicos(request);

        Maestros maestros = maestroMapper.requestAEntidad(request);

        maestroReporitory.save(maestros);

        log.info("Nuevo maestro: {} registrado",maestros.getNombre());

        return maestroMapper.entidadAResponse(maestros);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {

        Maestros maestros = obtenerMaestro(id);

        log.info("Actualizando maestro{}: ",id);

        validarCambiosUnicos(request.email(),request.telefono(),id);

        maestros.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.email(),
                request.telefono()

        );

        log.info("maestro {} actualizado correctamente", maestros.getNombre());

        return maestroMapper.entidadAResponse(maestros);
    }

    @Override
    public void eliminar(Long id) {

        Maestros maestros = obtenerMaestro(id);

        log.info("Eliminando maestro con id:{} ",id);

        maestroReporitory.delete(maestros);

        log.info("Maestro {} eliminado correctamente", maestros.getNombre());

    }

    private Maestros obtenerMaestro(Long id){
        return ServiceUtils.obtenerEntidadOExcepcion(maestroReporitory,id,Maestros.class);
    }

    private void validarDatosUnicos(MaestroRequest request){
        log.info("Validando email unico");

        if (maestroReporitory.existsByEmailIgnoreCase(request.email().trim()))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: "+request.email());

        log.info("Validando telefono unico");

        if (maestroReporitory.existsByTelefono(request.telefono().trim()))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: "+request.telefono());


    }

    private void validarCambiosUnicos(String email,String telefono, Long id){
        log.info("Validando email unico");

        if (maestroReporitory.existsByEmailIgnoreCaseAndIdNot(email,id))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: "+email);

        log.info("Validando telefono unico");

        if (maestroReporitory.existsByTelefonoAndIdNot(telefono,id))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: "+telefono);


    }
}
