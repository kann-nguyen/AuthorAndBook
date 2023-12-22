package com.kann.database.services;

import java.util.List;
import java.util.Optional;

import com.kann.database.domain.entity.BookEntity;

public interface BookService {
    BookEntity save(String isbn, BookEntity book);

    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);

    boolean isExists(String isbn);

    void delete(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);
}
