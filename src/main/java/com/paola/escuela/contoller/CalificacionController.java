package com.paola.escuela.contoller;

import com.paola.escuela.dto.calificacion.CalificacionRequest;
import com.paola.escuela.dto.calificacion.CalificacionResponse;
import com.paola.escuela.services.calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService> {
    public CalificacionController(CalificacionService service) {
        super(service);
    }

}
