package br.com.marcelo.bookservice.api.controller;

import java.util.List;

public class BookBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static BookDto getDto() {
        BookDto dto = new BookDto();
        dto.setId("1");
        return dto;
    }
}