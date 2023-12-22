package com.kann.database.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kann.database.domain.dto.AuthorDto;
import com.kann.database.domain.entity.AuthorEntity;
import com.kann.database.mappers.Mapper;
import com.kann.database.services.AuthorService;

@RestController
public class AuthorController {
    
    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }


    //create a author
    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    //get all authors in db
    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors(){

        List<AuthorEntity> authors = authorService.findAll();
        
        return authors.stream()
            .map(authorMapper::mapTo)
            .collect(Collectors.toList());
    }

    //get author by id
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {

        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        return foundAuthor.map(authorEntity ->{
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    
    }

    //full update author
    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAutho
    (@PathVariable("id") Long id,
    @RequestBody AuthorDto authorDto ) {

        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);

        AuthorEntity savedauthorEntity = authorService.save(authorEntity);

        return new ResponseEntity<>(
                authorMapper.mapTo(savedauthorEntity),
                HttpStatus.OK
        );
    }

    //partial update author
    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(
        @PathVariable("id") Long id,
        @RequestBody AuthorDto authorDto
    ) {

        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthor = authorService.partialUpdate(id, authorEntity);
        return new ResponseEntity<>(
            authorMapper.mapTo(updatedAuthor),
            HttpStatus.OK
        );
    }

    //delete author
    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorEntity> deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
