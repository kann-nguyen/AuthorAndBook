package com.kann.database.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.kann.database.TestDataUtil;
import com.kann.database.domain.entity.AuthorEntity;
import com.kann.database.domain.entity.BookEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryImplIntegrationTest {

    private AuthorRepository authorDao;
    private BookRepository underTest;

    @Autowired
    public BookRepositoryImplIntegrationTest(BookRepository underTest, AuthorRepository authorDao){
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {

        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorDao.save(author);

        BookEntity book = TestDataUtil.createTestBookA(author);

        underTest.save(book);
        Optional<BookEntity> result = underTest.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }
    
    @Test
    public void testThatMultipleBookCanBeCreatedAndRecalled() {

        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorDao.save(author);

        BookEntity bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);
        BookEntity bookB = TestDataUtil.createTestBookB(author);
        underTest.save(bookB);
        BookEntity bookC = TestDataUtil.createTestBookC(author);
        underTest.save(bookC);
        BookEntity bookD = TestDataUtil.createTestBookD(author);
        underTest.save(bookD);
        Iterable<BookEntity> result = underTest.findAll();

         assertThat(result).hasSize(4).containsExactly(bookA, bookB, bookC, bookD);
     }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorDao.save(author);

        BookEntity bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);

        bookA.setTitle("UPDATED");
        underTest.save(bookA);

        Optional<BookEntity> result = underTest.findById(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorDao.save(author);

        BookEntity bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);

        underTest.deleteById(bookA.getIsbn());

        Optional<BookEntity> result = underTest.findById(bookA.getIsbn());
        assertThat(result).isNotPresent();
    }
}

