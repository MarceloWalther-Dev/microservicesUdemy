package br.com.marcelo.cambioservice.domain.service.impl;

import br.com.marcelo.cambioservice.api.dto.CambioDTO;
import br.com.marcelo.cambioservice.domain.exception.CambioNotFoundException;
import br.com.marcelo.cambioservice.domain.model.Cambio;
import br.com.marcelo.cambioservice.domain.repository.CambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public class CambioService implements CambioCrud{

    private final Environment environment;
    private final CambioRepository cambioRepository;


    @Autowired
    public CambioService(Environment environment, CambioRepository cambioRepository) {
        this.environment = environment;
        this.cambioRepository = cambioRepository;
    }

    @Override
    public CambioDTO getCambio(BigDecimal amount,String from,String to){
        Cambio cambio = cambioRepository.findByFromAndTo(from, to);

        if(cambio == null){
            throw new CambioNotFoundException("currence unsupported");
        }

        String port = environment.getProperty("local.server.port");

        BigDecimal conversionFactory = cambio.getConversionFactor();
        BigDecimal convertedValue = conversionFactory.multiply(amount);


        return convertDTO(cambio, port, convertedValue);

    }

    private CambioDTO convertDTO(Cambio cambio, String port, BigDecimal convertedValue) {
        CambioDTO cambioDTO = new CambioDTO();
        cambioDTO.setId(cambio.getId());
        cambioDTO.setFrom(cambio.getFrom());
        cambioDTO.setTo(cambio.getTo());
        cambioDTO.setConversionFactor(cambio.getConversionFactor());
        cambioDTO.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        cambioDTO.setEnvironment(port);
        return cambioDTO;
    }

}