package com.naeun2934.acshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean verifyEmail(String emailForVerify, String emailCodeForVerify, User user) {
        if (emailCodeForVerify == null || emailForVerify == null) {
            return false;
        }
        return (emailCodeForVerify.equals(user.getEmailCode()) && emailForVerify.equals(user.getUserEmail()));
    }


    @Override
    @Transactional
    // 회원 가입
    public void signup(User user) {
        validateDupicateUser(user);
        user.setUserPwd(bCryptPasswordEncoder.encode(user.getUserPwd()));
        userRepository.save(user);
    }

    // 유저 중복확인
    private void validateDupicateUser(User user) {
        User findUser = userRepository.findByUserEmail(user.getUserEmail());
        if (findUser != null) {
            throw new IllegalStateException("duplicateUser");
        }
    }

    @Override
    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        User findUser = userRepository.findByUserEmail(username);

        // User가 없거나 HomePage를 통한 가입이 아닌 경우 null 반환
        if (findUser == null || !findUser.getUserProvider().equals(UserProvider.HOMEPAGE)) {
            return null;
        } else {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(findUser.getUserEmail())
                    .password(findUser.getUserPwd())
                    .roles("user")
                    .build();

        }

    }

    @Override
    public User makeUserUpdateForm(Long userId) {
        User kizonUser = userRepository.findOne(userId);
        return new User(kizonUser.getId(), kizonUser.getUserEmail(), kizonUser.getUserName(), kizonUser.getAddress(), kizonUser.getUserProvider());
    }

    @Override
    @Transactional
    public void save(User user, Long userId) {

        // 패스워드 확인
        User kizonUser = userRepository.findOne(userId);
        if (!kizonUser.getUserProvider().equals(UserProvider.GOOGLE) && !bCryptPasswordEncoder.matches(user.getUserPwd(), kizonUser.getUserPwd())) {
            // 패스워드가 틀릴경우 에러
            throw new IllegalStateException("incorrectUserPwd");
        } else {
            kizonUser.setUserName(user.getUserName());
            // 패스 워드 변경이 있을 경우
            if (user.getUserNewPwd() != null) {
                kizonUser.setUserPwd(bCryptPasswordEncoder.encode(user.getUserNewPwd()));
            }
            kizonUser.setAddress(user.getAddress());
        }
        userRepository.save(kizonUser);
    }
}
