package com.kann.database.repositories;

import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.kann.database.TestDataUtil;
import com.kann.database.domain.entity.AuthorEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryImplIntegrationTest {

    private AuthorRepository underTest;


    @Autowired
    public AuthorRepositoryImplIntegrationTest(AuthorRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {

        AuthorEntity author = TestDataUtil.createTestAuthorA();

        underTest.save(author);
        Optional<AuthorEntity> result = underTest.findById(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorCanBeCreatedAndRecalled() {

        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
        underTest.save(authorB);
        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorC);

        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);

        authorA.setName("UPDATED");
        underTest.save(authorA);

        Optional<AuthorEntity> result = underTest.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("UPDATED");

    }

    @Test
    public void testThatAuthorCanBeDeleted() {

        AuthorEntity author = TestDataUtil.createTestAuthorA();
        underTest.save(author);

        underTest.deleteById(author.getId());

        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertThat(result).isNotPresent();
    }
}