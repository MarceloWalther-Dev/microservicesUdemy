package br.com.marcelo.cambioservice.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class CambioBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static CambioDto getDto() {
        CambioDto dto = new CambioDto();
        dto.setId("1");
        return dto;
    }
}