package com.naeun2934.acshop.email;

import java.util.Locale;

public interface EmailService {
    /**
     * 이메일 전송(인증관련)
     * 처음 로그인 화면에 접속한 경우, error가 false인 경우
     * - 이전 Url정보(previousUrl) 저장
     *
     * @param to     받는 사람
     * @param locale 지역, 지역에 따른 언어로 이메일 전송하기 위해
     * @return 인증번호 (Session에 저장해둔 뒤, 이후 유저가 입력한 인증번호와 일치한지 확인하기 위해)
     */
    String sendSignupAuthEmail(String to, Locale locale) throws Exception;
}
