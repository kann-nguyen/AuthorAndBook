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
import com.kann.database.domain.entity.AuthorEntity;
import com.kann.database.services.AuthorService;

@SpringBootTest
// @ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {

    private AuthorService authorService;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc, AuthorService authorService) {
        this.authorService = authorService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    //maybe get no tests found matching 
    //check import org.junit.jupiter.api.Test
    @Test
    public void testThatCreateAuthorSuccessfullyReturnHttp201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);

        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatFindAllAuthorSuccessfullyReturnHttp200Created() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFindAllAuthorSuccessfullyReturnListOfAuthors() throws Exception {

        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].age").value(80)
        );
    }

    @Test
    public void testThatFindAuthorByIdSuccessfullyReturnHttp200Created() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/" + testAuthorEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFindAuthorByIdSuccessfullyReturnAAuthor() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/" + testAuthorEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

}
