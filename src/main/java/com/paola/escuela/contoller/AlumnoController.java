package com.paola.escuela.contoller;


import com.paola.escuela.dto.alumno.AlumnosRequest;
import com.paola.escuela.dto.alumno.AlumnosResponse;
import com.paola.escuela.services.alumnos.AlumnoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController extends CommonController<AlumnosRequest, AlumnosResponse, AlumnoService> {

    public AlumnoController(AlumnoService service) {
        super(service);
    }
}
