package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Role;
import com.hybridit.HybridLibrary.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JPARoleService implements RoleService {

    private final RoleRepository roleRepository;

    public JPARoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findOne(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with id provided does not exist");
        }
        return roleRepository.getOne(id);
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No roles to display");
        }
        return roles;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with provided id does not exist");
        }
        Role deleted = roleRepository.getOne(id);
        roleRepository.deleteById(id);
        return deleted;
    }

    @Override
    public Role update(Role fromRequestBody, Long id) {
        return roleRepository.findById(id).map(author -> {
            author.setName(fromRequestBody.getName());
            roleRepository.save(author);
            return author;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with provided id does not exist"));
    }
}
