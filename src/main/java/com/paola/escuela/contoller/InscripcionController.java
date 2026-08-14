package com.paola.escuela.contoller;

import com.paola.escuela.dto.inscripciones.InscripcionRequest;
import com.paola.escuela.dto.inscripciones.InscripcionResponse;
import com.paola.escuela.services.inscripcion.InscripcionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController extends CommonController<InscripcionRequest, InscripcionResponse, InscripcionService>{

    public InscripcionController(InscripcionService service) {super(service);}

}
