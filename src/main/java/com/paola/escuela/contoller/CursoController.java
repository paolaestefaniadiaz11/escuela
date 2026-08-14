package com.paola.escuela.contoller;

import com.paola.escuela.dto.curso.CursosRequest;
import com.paola.escuela.dto.curso.CursosResponse;
import com.paola.escuela.services.curso.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cursos")
public class CursoController extends CommonController<CursosRequest, CursosResponse, CursoService> {

    public CursoController(CursoService service) {
        super(service);
    }

}
