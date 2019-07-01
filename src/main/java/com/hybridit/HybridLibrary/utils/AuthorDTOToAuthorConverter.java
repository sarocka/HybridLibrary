package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.AuthorDTO;
import com.hybridit.HybridLibrary.model.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorDTOToAuthorConverter implements Converter<AuthorDTO, Author> {
    @Override
    public Author convert(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setId(authorDTO.getId());
        return author;
    }
    public List<Author> convert(List<AuthorDTO> authorDTOS){
        return authorDTOS.stream().map(authorDTO -> convert(authorDTO)).collect(Collectors.toList());
    }
}
