package com.paola.escuela.contoller;


import com.paola.escuela.dto.grupo.GrupoRequest;
import com.paola.escuela.dto.grupo.GrupoResponse;
import com.paola.escuela.services.grupo.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService> {

    public GrupoController(GrupoService service) {
        super(service);
    }

}
