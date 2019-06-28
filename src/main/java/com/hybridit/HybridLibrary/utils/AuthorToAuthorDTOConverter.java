package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.AuthorDTO;
import com.hybridit.HybridLibrary.model.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorToAuthorDTOConverter implements Converter<Author, AuthorDTO> {
    @Override
    public AuthorDTO convert(Author author) {
        AuthorDTO authorDTO= new AuthorDTO();
        authorDTO.setName(author.getName());
        authorDTO.setId(author.getId());
        return authorDTO;

    }
    public List<AuthorDTO> convert(List<Author>authors){
        return authors.stream().map(author -> convert(author)).collect(Collectors.toList());
    }
}
