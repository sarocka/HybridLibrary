package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.RoleDTO;
import com.hybridit.HybridLibrary.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleToRoleDTOConverter implements Converter<Role, RoleDTO> {

    @Override
    public RoleDTO convert(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    public List<RoleDTO> convert(List<Role> roles) {
        return roles.stream().map(role -> convert(role)).collect(Collectors.toList());
    }


}
