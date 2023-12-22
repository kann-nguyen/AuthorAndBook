package com.kann.database.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.kann.database.domain.dto.BookDto;
import com.kann.database.domain.entity.BookEntity;
import com.kann.database.mappers.Mapper;
import com.kann.database.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/book")
public class BookController {
    
    private BookService bookService;
    
    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    //create a new book
    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> save(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedbookEntity = bookService.save(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedbookEntity);

        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    //find all books
    @GetMapping(path = "/books")
    public List<BookDto> listBooks() {

        List<BookEntity> books = bookService.findAll();

        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    //find a book by isbn
    @GetMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {

        Optional<BookEntity> foundBook = bookService.findOne(isbn);

        return foundBook.map(
            bookEntity -> {
                BookDto bookDto = bookMapper.mapTo(bookEntity);
                return new ResponseEntity<>(bookDto, HttpStatus.OK);
            }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        
    }

    //full update book
    @PutMapping(path = "/update/{isbn}")
    public ResponseEntity<BookDto> fullUpdateBook(
        @PathVariable("isbn") String isbn,
        @RequestBody BookDto bookDto
    ) {

        if(!bookService.isExists(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //bookDto.setIsbn(isbn);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.save(isbn, bookEntity);

        return new ResponseEntity<>(
                bookMapper.mapTo(savedBookEntity),
                HttpStatus.OK   
        );
    }

    //partial update book
    @PatchMapping(path = "/update/{isbn}")
    public ResponseEntity<BookDto> partialUpdate(
        @PathVariable("isbn") String isbn,
        @RequestBody BookDto bookDto
    ) {

        if(!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.partialUpdate(isbn, bookEntity);

        return new ResponseEntity<>(
            bookMapper.mapTo(savedBookEntity),
            HttpStatus.OK
        );

    }

    //delete book
    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity<BookEntity> deleteBook(@PathVariable("isbn") String isbn)
    {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
