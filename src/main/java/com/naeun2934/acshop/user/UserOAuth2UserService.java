package com.naeun2934.acshop.user;

import com.naeun2934.acshop.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Transactional(readOnly = true)
public class UserOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public UserOAuth2UserService(UserRepository userRepository, HttpServletRequest httpServletRequest) {
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String userName = super.loadUser(userRequest).getAttribute("name");
        String userEmail = super.loadUser(userRequest).getAttribute("email");

        // 계정이 없으면 회원가입
        User user = userRepository.findByUserEmail(userEmail);
        if (user == null) {
            user = new User(userEmail, userName, null, null, UserProvider.GOOGLE, null, null);
            userRepository.save(user);
            user = userRepository.findByUserEmail(userEmail);
        }

        // Session에 회원정보 저장
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute(Constant.LOGIN_USEREMAIL, user.getUserEmail());
        httpSession.setAttribute(Constant.LOGIN_USERID, user.getId());

        return super.loadUser(userRequest);
    }

}