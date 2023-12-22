package com.kann.database.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.kann.database.domain.dto.BookDto;
import com.kann.database.domain.entity.BookEntity;
import com.kann.database.mappers.Mapper;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto>{

    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
    
}
