package br.com.marcelo.bookservice.domain.service;

import br.com.marcelo.bookservice.api.dto.BookDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface BookCrud {

    public BookDTO getBook(Long id, String currency);
}
