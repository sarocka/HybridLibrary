package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.BookDTO;
import com.hybridit.HybridLibrary.dto.RoleDTO;
import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.model.Role;
import com.hybridit.HybridLibrary.service.BookService;
import com.hybridit.HybridLibrary.service.RoleService;
import com.hybridit.HybridLibrary.utils.BookDTOToBookConverter;
import com.hybridit.HybridLibrary.utils.BookToBookDTOConverter;
import com.hybridit.HybridLibrary.utils.RoleDTOToRoleConverter;
import com.hybridit.HybridLibrary.utils.RoleToRoleDTOConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleToRoleDTOConverter roleToRoleDTOConverter;
    private final RoleDTOToRoleConverter roleDTOToRoleConverter;

    public RoleController(RoleService roleService, RoleToRoleDTOConverter roleToRoleDTOConverter,
                          RoleDTOToRoleConverter roleDTOToRoleConverter) {
        this.roleService = roleService;
        this.roleToRoleDTOConverter = roleToRoleDTOConverter;
        this.roleDTOToRoleConverter =roleDTOToRoleConverter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RoleDTO>> getAll() {
        return new ResponseEntity<>(roleToRoleDTOConverter.convert(roleService.findAll()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<RoleDTO> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(roleToRoleDTOConverter.convert(roleService.findOne(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<RoleDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(roleToRoleDTOConverter.convert(roleService.delete(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO roleDTO) {
        Role role = roleDTOToRoleConverter.convert(roleDTO);
        roleService.save(role);
        return new ResponseEntity<>(roleToRoleDTOConverter.convert(role), HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", value = "/{id}")
    public ResponseEntity<RoleDTO> update(@RequestBody RoleDTO roleDTO, @PathVariable Long id) {
        Role role = roleDTOToRoleConverter.convert(roleDTO);
        Role updated= roleService.update(role,id);
        return new ResponseEntity<>(roleToRoleDTOConverter.convert(updated), HttpStatus.OK);
    }
}
