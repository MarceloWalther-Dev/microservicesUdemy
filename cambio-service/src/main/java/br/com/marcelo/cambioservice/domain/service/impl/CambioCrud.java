package br.com.marcelo.cambioservice.domain.service.impl;

import br.com.marcelo.cambioservice.api.dto.CambioDTO;

import java.math.BigDecimal;

public interface CambioCrud {

    CambioDTO getCambio(BigDecimal amount, String from, String to);
}
