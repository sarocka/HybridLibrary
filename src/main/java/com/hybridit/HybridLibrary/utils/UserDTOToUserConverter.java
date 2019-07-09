package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.BookCopyDTO;
import com.hybridit.HybridLibrary.dto.UserDTO;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.model.User;
import com.hybridit.HybridLibrary.service.BookService;
import com.hybridit.HybridLibrary.service.RoleService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOToUserConverter implements Converter<UserDTO, User> {

    private final RoleService roleService;

    public UserDTOToUserConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public User convert(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setRole(roleService.findOne(userDTO.getRoleId()));
        return user;
    }

    public List<User> convert(List<UserDTO> dtos) {
       return dtos.stream().map(dto->convert(dto)).collect(Collectors.toList());
    }

}
