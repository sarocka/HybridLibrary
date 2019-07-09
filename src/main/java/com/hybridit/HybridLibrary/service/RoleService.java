package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Role;

import java.util.List;

public interface RoleService {

    Role findOne(Long id);
    List<Role> findAll();
    Role save(Role role);
    Role delete(Long id);
    Role update(Role role, Long id);

}
