package com.naeun2934.acshop.user;

import java.util.List;

public interface UserRepository {
    void save(User user);

    User findOne(Long id);

    List<User> findAll();

    User findByUserEmail(String userEmail);

}
