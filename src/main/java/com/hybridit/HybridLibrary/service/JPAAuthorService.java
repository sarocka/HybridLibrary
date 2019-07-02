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
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
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
        if (!authorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with provided id does not exist");
        } else if (!authorRepository.getOne(id).getBooks().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot delete the author connected with books in database");
        } else {
            Author deleted = authorRepository.getOne(id);
            authorRepository.deleteById(id);
            return deleted;
        }
    }

    @Override
    public Author update(Author fromRequestBody, Long id) {
        return authorRepository.findById(id).map(author -> {
            author.setName(fromRequestBody.getName());
            authorRepository.save(author);
            return author;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with provided id does not exist"));
    }
}
