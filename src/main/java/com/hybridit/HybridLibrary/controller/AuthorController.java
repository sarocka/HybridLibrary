package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.AuthorDTO;
import com.hybridit.HybridLibrary.model.Author;
import com.hybridit.HybridLibrary.service.AuthorService;
import com.hybridit.HybridLibrary.utils.AuthorDTOToAuthorConverter;
import com.hybridit.HybridLibrary.utils.AuthorToAuthorDTOConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorToAuthorDTOConverter authorToAuthorDTOConverter;
    private final AuthorDTOToAuthorConverter authorDTOToAuthorConverter;

    public AuthorController(AuthorService authorService, AuthorToAuthorDTOConverter authorToAuthorDTOConverter,
                            AuthorDTOToAuthorConverter authorDTOToAuthorConverter) {
        this.authorService = authorService;
        this.authorToAuthorDTOConverter = authorToAuthorDTOConverter;
        this.authorDTOToAuthorConverter = authorDTOToAuthorConverter;
    }

    @Secured({"ROLE_MANAGER"})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AuthorDTO>> getAll() {
        return new ResponseEntity<>(authorToAuthorDTOConverter.convert(authorService.findAll()), HttpStatus.OK);
    }

    @Secured({"ROLE_MANAGER"})
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<AuthorDTO> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(authorToAuthorDTOConverter.convert(authorService.findOne(id)), HttpStatus.OK);
    }

    @Secured({"ROLE_MANAGER"})
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<AuthorDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(authorToAuthorDTOConverter.convert(authorService.delete(id)), HttpStatus.OK);
    }

    @Secured({"ROLE_MANAGER"})
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorDTO authorDTO) {
        Author savedAuthor = authorService.save(authorDTOToAuthorConverter.convert(authorDTO));
        return new ResponseEntity<>(authorToAuthorDTOConverter.convert(savedAuthor), HttpStatus.CREATED);
    }

    @Secured({"ROLE_MANAGER"})
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", value = "/{id}")
    public ResponseEntity<AuthorDTO> update(@RequestBody AuthorDTO authorDTO, @PathVariable Long id) {
        Author author = authorDTOToAuthorConverter.convert(authorDTO);
        Author updated = authorService.update(author, id);
        return new ResponseEntity<>(authorToAuthorDTOConverter.convert(updated), HttpStatus.OK);
    }


}
