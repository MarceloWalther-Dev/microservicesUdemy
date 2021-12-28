package br.com.marcelo.cambioservice.api.controller;

import br.com.marcelo.cambioservice.api.dto.CambioDTO;
import br.com.marcelo.cambioservice.domain.service.impl.CambioCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

    private final Environment environment;
    private final CambioCrud cambioCrud;


    @Autowired
    public CambioController(Environment environment, CambioCrud cambioCrud) {
        this.environment = environment;
        this.cambioCrud = cambioCrud;
    }

    @GetMapping(value = "/{amount}/{from}/{to}")
    public ResponseEntity<CambioDTO> getCambio(@PathVariable("amount")BigDecimal amount,
                                               @PathVariable("from")String from, @PathVariable("to") String to){
        return ResponseEntity.ok(cambioCrud.getCambio(amount, from, to));
    }

}
