package com.kann.database;

import com.kann.database.domain.dto.AuthorDto;
import com.kann.database.domain.dto.BookDto;
import com.kann.database.domain.entity.AuthorEntity;
import com.kann.database.domain.entity.BookEntity;

public final class TestDataUtil {
    
    private TestDataUtil(){}


    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                    .id(1L)
                    .name("Abigail Rose")
                    .age(80)
                    .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                    .id(2L)
                    .name("Uchiha Kan")
                    .age(27)
                    .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                    .id(3L)
                    .name("Kaneki Ken")
                    .age(16)
                    .build();
    }

    public static BookEntity createTestBookA(final AuthorEntity author) {
        return BookEntity.builder()
                    .isbn("kan")
                    .title("Minimalism")
                    .author(author)
                    .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity author) {
        return BookEntity.builder()
                    .isbn("nguyen")
                    .title("Soict")
                    .author(author)
                    .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity author) {
        return BookEntity.builder()
                    .isbn("van")
                    .title("Mini Habits")
                    .author(author)
                    .build();
    }

    public static BookEntity createTestBookD(final AuthorEntity author) {
        return BookEntity.builder()
                    .isbn("tan")
                    .title("Slience")
                    .author(author)
                    .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto author) {
        return BookDto.builder()
                    .isbn("kan")
                    .title("Minimalism")
                    .author(author)
                    .build();
    }
}
