package com.erp.backend.dtos.mappers;

import com.erp.backend.dtos.AuthorDto;
import com.erp.backend.entities.Author;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class AuthorDtoMapper implements Function<Author, AuthorDto> {
    @Override
    public AuthorDto apply(Author author) {
        return new AuthorDto(author.getId(),author.getName(),author.getDescription());
    }
}
