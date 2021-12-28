package br.com.marcelo.bookservice.api.controller;

import br.com.marcelo.bookservice.api.dto.BookDTO;
import br.com.marcelo.bookservice.domain.service.BookCrud;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book-service")
public class BookController {

    private final BookCrud bookCrud;

    public BookController(BookCrud bookCrud) {
        this.bookCrud = bookCrud;
    }


    @GetMapping(value = "/{id}/{currency}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("id")Long id, @PathVariable("currency")String currency){

        return ResponseEntity.ok(bookCrud.getBook(id, currency));
    }

}