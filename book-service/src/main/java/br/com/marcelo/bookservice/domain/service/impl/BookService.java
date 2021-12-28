package br.com.marcelo.bookservice.domain.service.impl;

import br.com.marcelo.bookservice.api.dto.BookDTO;
import br.com.marcelo.bookservice.api.proxy.CambioProxy;
import br.com.marcelo.bookservice.api.response.Cambio;
import br.com.marcelo.bookservice.domain.exception.BookNotFoundException;
import br.com.marcelo.bookservice.domain.model.Book;
import br.com.marcelo.bookservice.domain.repository.BookRepository;
import br.com.marcelo.bookservice.domain.service.BookCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class BookService implements BookCrud {

    private final Environment environment;
    private final BookRepository bookRepository;
    private final CambioProxy cambioProxy;

    @Autowired
    public BookService(Environment environment, BookRepository bookRepository, CambioProxy cambioProxy) {
        this.environment = environment;
        this.bookRepository = bookRepository;
        this.cambioProxy = cambioProxy;
    }

    @Override
    public BookDTO getBook(Long id, String currency) {
        Cambio cambio;
        Book book = findBookByIdOrException(id);

        cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency).getBody();

        book.setPrice(cambio.getConvertedValue());

        return transformBookInBookDTO(book);
    }

//    @Override
//    public BookDTO getBook(Long id, String currency) {
//        Cambio cambio;
//        Book book = findBookByIdOrException(id);
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("amount", book.getPrice().toString());
//        params.put("from", "USD");
//        params.put("to", currency);
//
//
//        ResponseEntity<Cambio> response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
//        cambio = response.getBody();
//
//        book.setPrice(cambio.getConvertedValue());
//
//        return transformBookInBookDTO(book);
//    }
//

    private BookDTO transformBookInBookDTO(Book book) {
        String port = environment.getProperty("local.server.port");
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setAuthor(book.getAuthor());
        dto.setCurrency(book.getCurrency());
        dto.setLaunchDate(book.getLaunchDate());
        dto.setPrice(book.getPrice());
        dto.setTitle(book.getTitle());
        dto.setEnvironment(port + " feing");
        return dto;
    }

    private Book findBookByIdOrException(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

}