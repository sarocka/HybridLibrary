package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.UserDTO;
import com.hybridit.HybridLibrary.model.User;
import com.hybridit.HybridLibrary.service.UserService;
import com.hybridit.HybridLibrary.utils.UserDTOToUserConverter;
import com.hybridit.HybridLibrary.utils.UserToUserDTOConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;
    private final UserDTOToUserConverter userDTOToUserConverter;
    private final UserToUserDTOConverter userToUserDTOConverter;

    public UserController(UserService userService, UserDTOToUserConverter userDTOToUserConverter,
                          UserToUserDTOConverter userToUserDTOConverter) {
        this.userService = userService;
        this.userDTOToUserConverter = userDTOToUserConverter;
        this.userToUserDTOConverter = userToUserDTOConverter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(userToUserDTOConverter.convert(userService.findAll()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(userToUserDTOConverter.convert(userService.findOne(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(userToUserDTOConverter.convert(userService.delete(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        User user = userService.save(userDTOToUserConverter.convert(userDTO));
        return new ResponseEntity<>(userToUserDTOConverter.convert(user), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", value = "/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        User user = userDTOToUserConverter.convert(userDTO);
        return new ResponseEntity<>(userToUserDTOConverter.convert(userService.update(user, id)), HttpStatus.OK);
    }
}
