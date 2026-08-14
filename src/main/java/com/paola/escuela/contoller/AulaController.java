package com.paola.escuela.contoller;


import com.paola.escuela.dto.aula.AulaRequest;
import com.paola.escuela.dto.aula.AulaResponse;
import com.paola.escuela.services.aula.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aula")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService> {

    public AulaController(AulaService service) {
        super(service);
    }
}
