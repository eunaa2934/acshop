package com.naeun2934.acshop.user;

import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    void signup(User user);

    User findOne(Long userId);

    User findByUserEmail(String userEmail);

    User makeUserUpdateForm(Long userId);

    @Transactional
    void save(User user, Long userId);

    boolean verifyEmail(String emailForVerify, String emailCodeForVerify, User user);
}
