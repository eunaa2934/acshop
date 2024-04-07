package com.naeun2934.acshop.email;

import com.naeun2934.acshop.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
@Slf4j
@RequestMapping("/emails")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * 이메일 전송 처리
     * 전송할 이메일 종류 확인
     * - 회원가입 인증메일 (전송 성공시, 나중에 인증확인을 위해 인증 관련 정보 Session에 저장)
     * - 상황에 따라 추가
     *
     * @param email 이메일 관련 정보
     */
    @ResponseBody
    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody Email email, Locale locale, HttpSession httpSession) throws Exception {

        // 회원가입 인증 메일의 경우
        if (EmailType.SIGNUPAUTH.equals(email.getEmailType())) {
            // 메일 전송
            String emailCodeForVerify = emailService.sendSignupAuthEmail(email.getSendTo(), locale);

            // 인증 관련 정보 Session에 저장
            httpSession.setAttribute(Constant.EMAIL_FOR_VERIFY, email.getSendTo());
            httpSession.setAttribute(Constant.EMAIL_CODE_FOR_VERIFY, emailCodeForVerify);

        }
    }
}
