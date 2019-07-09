package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.RoleDTO;
import com.hybridit.HybridLibrary.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleDTOToRoleConverter implements Converter<RoleDTO, Role> {

    @Override
    public Role convert(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        return role;
    }

    public List<Role> convert(List<RoleDTO> dtos) {
        return dtos.stream().map(roleDTO -> convert(roleDTO)).collect(Collectors.toList());
    }

}
