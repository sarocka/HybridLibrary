package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.User;

import java.util.List;

public interface UserService {

    User findOne(Long id);
    List<User> findAll();
    User save(User user);
    User delete(Long id);
    User update(User user, Long id);

}
