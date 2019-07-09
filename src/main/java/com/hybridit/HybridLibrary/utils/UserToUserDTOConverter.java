package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.UserDTO;
import com.hybridit.HybridLibrary.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setRoleId(user.getRole().getId());
        userDTO.setRoleName(user.getRole().getName());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    public List<UserDTO> convert(List<User> users) {
        return users.stream().map(user -> convert(user)).collect(Collectors.toList());
        //return users.stream().map(this::convert).collect(Collectors.toList());
    }


}
