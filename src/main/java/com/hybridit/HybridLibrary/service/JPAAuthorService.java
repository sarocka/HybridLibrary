package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Author;
import com.hybridit.HybridLibrary.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JPAAuthorService implements AuthorService {

    private final AuthorRepository authorRepository;

    public JPAAuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author findOne(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the entity with a given id does not exist"));
    }

    @Override
    public List<Author> findAll() {
        List<Author>authors= authorRepository.findAll();
        if(authors.isEmpty()){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors to display");
        }
        return authors;
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author delete(Long id) {
        return authorRepository.findById(id).
                map(author -> {authorRepository.delete(author);
                    return author;})
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with provided id does not exist"));
    }

    @Override
    public Author update(Author author, Long id) {
        if(authorRepository.existsById(author.getId())){
          Author updated=authorRepository.save(author);
          return updated;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with provided id does not exist");
    }
}
