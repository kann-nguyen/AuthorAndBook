package com.kann.database.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.kann.database.domain.dto.AuthorDto;
import com.kann.database.domain.entity.AuthorEntity;
import com.kann.database.mappers.Mapper;


@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto>{

    private ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
    
}
