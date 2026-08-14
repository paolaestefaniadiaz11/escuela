package com.paola.escuela.services.horario;

import com.paola.escuela.dto.horario.HorarioRequest;
import com.paola.escuela.dto.horario.HorarioResponse;
import com.paola.escuela.entities.*;
import com.paola.escuela.enums.DiaSemana;
import com.paola.escuela.exceptions.EntidadRelacionadaException;
import com.paola.escuela.mappers.HorarioMapper;
import com.paola.escuela.repositories.GrupoRepository;
import com.paola.escuela.repositories.HorarioRepository;
import com.paola.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;

    private final HorarioMapper horarioMapper;

    private final GrupoRepository grupoRepository;


    @Override
    public List<HorarioResponse> listar() {
        return horarioRepository.findAll().stream()
                .map(horarioMapper::entidadAResponse).toList();

    }

    @Override
    public HorarioResponse obtenerPorId(Long id) {
        return horarioMapper.entidadAResponse(obtenerHorario(id));

    }

    @Override
        public HorarioResponse registrar(HorarioRequest request) {
            log.info("Registrando nuevo horario para el grupo ID: {}", request.idGrupo());

        validarFormatoYCoherenciaHoras(request.horaInicio(), request.horaFin());
            Grupo grupo = grupoRepository.findById(request.idGrupo())
                    .orElseThrow(() -> new IllegalArgumentException("Curso: " +request.idGrupo() +" no encontrado"));

            DiaSemana diaSemana = DiaSemana.obtenerDiaSemanaPorDescripcion(request.dia());

            validarHorarioDisponible(grupo.getId(), diaSemana, request.horaInicio(), request.horaFin());

            Horario horario = Horario.builder()
                    .grupo(grupo)
                    .dia(diaSemana)
                    .horaInicio(request.horaInicio().trim())
                    .horaFin(request.horaFin().trim())
                    .build();

            horarioRepository.save(horario);

            log.info("Horario registrado exitosamente con ID: {}", horario.getId());

            return horarioMapper.entidadAResponse(horario);


        }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {

        Horario horario = obtenerHorario(id);

        log.info("Actualizando Grupo: Curso: {}, Maestro: {}, Aula: {} y Período: {}",
                id,
                request.idGrupo(),
                request.dia(),
                request.horaInicio(),
                request.horaFin());

        DiaSemana diaSemana = DiaSemana.obtenerDiaSemanaPorDescripcion(request.dia());

        validarHorarioDisponibleCambio(request.idGrupo(),diaSemana,request.horaInicio(),request.horaFin());

        Grupo grupo = grupoRepository.findById(request.idGrupo())
                .orElseThrow(() -> new IllegalArgumentException("Grupo: " +request.idGrupo() +" no encontrado"));


        horario.actualizar(grupo,
                DiaSemana.obtenerDiaSemanaPorDescripcion(request.dia()),
                request.horaInicio(),
                request.horaFin());


        //log.info("Grupo: {} actualizado correctamente", grupo.getId());

        return horarioMapper.entidadAResponse(horario);

    }

    @Override
    public void eliminar(Long id) {

        Horario horario = obtenerHorario(id);

        log.info("Inicia eliminado horario con id:{} ",id);


        if (grupoRepository.existsById(id))
            throw new EntidadRelacionadaException("No se puede eliminar el grupo ya que tiene inscripciones asignadas");

        horarioRepository.delete(horario);

        log.info("Horario: {} eliminado correctamente", horario.getId());


    }

    private Horario obtenerHorario(Long id){
        return ServiceUtils.obtenerEntidadOExcepcion(horarioRepository,id, Horario.class);
    }



    private void validarHorarioDisponible(Long idGrupo, DiaSemana dia, String horaInicio, String horaFin) {

        log.info("Validando horario disponible unico");

        log.info("Hora Inicio:"+horaInicio+" Hora Fin:"+horaFin);

        int conteo = horarioRepository.existsSolapamientoHorario(
                idGrupo,
                dia.name(),
                horaInicio,
                horaFin);

       if (conteo > 0)
            throw new IllegalArgumentException(
                    "No hay horario disponible de:"+horaInicio+" a "+horaFin);


    }

    private void validarHorarioDisponibleCambio(Long idGrupo, DiaSemana dia, String horaInicio, String horaFin) {

        log.info("Validando horario ingresado unico");

        log.info("Hora Inicio:"+horaInicio+" Hora Fin:"+horaFin);

        int conteo = horarioRepository.existsSolapamientoHorario(
                idGrupo,
                dia.name(),
                horaInicio,
                horaFin);

        log.info("Conteo: "+conteo) ;
        if (conteo > 0)
            throw new IllegalArgumentException(
                    "Ya existe un horario asignado a las horas, Hora Inicio - "+horaInicio+" a Hora Fin - "+horaFin);


    }

    private void validarFormatoYCoherenciaHoras(String horaInicioStr, String horaFinStr) {
        LocalTime horaInicio;
        LocalTime horaFin;

        try {
            horaInicio = LocalTime.parse(horaInicioStr.trim());
            horaFin = LocalTime.parse(horaFinStr.trim());
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException(
                    "El formato de las horas es inválido. Debe usar el formato HH:mm (ejemplo: 08:00, 14:30)."
            );
        }

        if (!horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException(
                    "La hora de inicio (" + horaInicioStr + ") debe ser anterior a la hora de fin (" + horaFinStr + ")."
            );
        }
    }
}
