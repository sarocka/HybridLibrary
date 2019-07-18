package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Role;
import com.hybridit.HybridLibrary.repository.RoleRepository;
import com.hybridit.HybridLibrary.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JPARoleService implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public JPARoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository= userRepository;
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
        roleRepository.getOne(id).getUsers().stream().forEach(user -> userRepository.delete(user));
        roleRepository.deleteById(id);
        return deleted;
    }

    @Override
    public Role update(Role fromRequestBody, Long id) {
        return roleRepository.findById(id).map(role -> {
            role.setName(fromRequestBody.getName());
            roleRepository.save(role);
            return role;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with provided id does not exist"));
    }
}
