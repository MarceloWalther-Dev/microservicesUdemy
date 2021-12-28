package br.com.marcelo.bookservice.api.proxy;

import br.com.marcelo.bookservice.api.response.Cambio;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cambio-service", url = "localhost:8000")
public interface CambioProxy {

    @GetMapping(value = "/cambio-service/{amount}/{from}/{to}")
    public ResponseEntity<Cambio> getCambio(@PathVariable("amount") Double amount,@PathVariable("from")String from, @PathVariable("to") String to);


}
