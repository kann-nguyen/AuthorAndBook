package com.kann.database.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kann.database.TestDataUtil;
import com.kann.database.domain.dto.BookDto;
import com.kann.database.domain.entity.BookEntity;
import com.kann.database.services.BookService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    private BookService bookService;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    //maybe get no tests found matching 
    //check import org.junit.jupiter.api.Test
    @Test
    public void testThatCreateBookSuccessfullyReturnHttp201Created() throws Exception {


        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/book/" + bookDto.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnCreatedBook() throws Exception {


        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/book/" + bookDto.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatFindAllBookSuccessfullyReturnHttp200Created() throws Exception {
            
        mockMvc.perform(
        MockMvcRequestBuilders.get("/book/books")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
        MockMvcResultMatchers.status().isOk()
    );

    }

    @Test
    public void testThatFindAllBookSuccessfullyReturnListOfBooks() throws Exception {
            
        BookEntity tesBookEntity = TestDataUtil.createTestBookA(null);
        bookService.save("kan", tesBookEntity);

        mockMvc.perform(
        MockMvcRequestBuilders.get("/book/books")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].isbn").value("kan")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].title").value("Minimalism")
        );
    }

    @Test
    public void testThatFindBookByIsbnSuccessfullyReturnHttp200Created() throws Exception {

        BookEntity tesBookEntity = TestDataUtil.createTestBookA(null);
        bookService.save("kan", tesBookEntity);

        mockMvc.perform(
        MockMvcRequestBuilders.get("/book/" + tesBookEntity.getIsbn())
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
        MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindBookByIsbnSuccessfullyReturnABook() throws Exception {

        BookEntity tesBookEntity = TestDataUtil.createTestBookA(null);
        bookService.save("kan", tesBookEntity);

        mockMvc.perform(
        MockMvcRequestBuilders.get("/book/" + tesBookEntity.getIsbn())
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value("kan")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("Minimalism")
        );
    }
}

