package com.naeun2934.acshop.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EmailServiceImpl implements EmailService {

    JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 이메일 전송(인증관련)
     * 처음 로그인 화면에 접속한 경우, error가 false인 경우
     * - 이전 Url정보(previousUrl) 저장
     *
     * @param to     받는 사람
     * @param locale 지역, 지역에 따른 언어로 이메일 전송하기 위해
     * @return 인증번호 (Session에 저장해둔 뒤, 이후 유저가 입력한 인증번호와 일치한지 확인하기 위해)
     */
    @Override
    public String sendSignupAuthEmail(String to, Locale locale) throws Exception {
        String emailCodeForVerify = UUID.randomUUID().toString().substring(0, 8);

        System.out.println(emailCodeForVerify);

        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to); //보내는 대상

        if (locale.getLanguage().equals(Locale.KOREA.getLanguage())) {
            createSinupAuthEmailKO(message, emailCodeForVerify);
        } else if (locale.getLanguage().equals(Locale.JAPAN.getLanguage())) {
            createSinupAuthEmailJA(message, emailCodeForVerify);
        } else {
            createSinupAuthEmailEN(message, emailCodeForVerify);
        }
        try {
            // 메일 전송
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return emailCodeForVerify;
    }

    /**
     * 이메일 내용 작성(영어)
     *
     * @param emailCodeForVerify 인증번호
     */
    private void createSinupAuthEmailEN(
            MimeMessage message,
            String emailCodeForVerify
    ) throws Exception {
        message.setSubject("[ACShop]Welcome to join");

        String msgText = "";
        msgText += "<div style='margin:20px;'>";
        msgText += "<h1> Hello we're ACShop. </h1>";
        msgText += "<br>";
        msgText += "<p>Please copy the code below.<p>";
        msgText += "<br>";
        msgText += "<p>Thank you.<p>";
        msgText += "<br>";
        msgText +=
                "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgText += "<h3 style='color:blue;'>Membership authentication code.</h3>";
        msgText += "<div style='font-size:130%'>";
        msgText += "CODE : <strong>";
        msgText += emailCodeForVerify + "</strong><div><br/> ";
        msgText += "</div>";
        message.setText(msgText, "utf-8", "html");
        message.setFrom(new InternetAddress("eunaa2934@gmail.com", "eunaa2934"));
    }

    /**
     * 이메일 내용 작성(일본어)
     *
     * @param emailCodeForVerify 인증번호
     */
    private void createSinupAuthEmailJA(
            MimeMessage message,
            String emailCodeForVerify
    ) throws Exception {
        message.setSubject("[ACShop]加入認証");

        String msgText = "";
        msgText += "<div style='margin:20px;'>";
        msgText += "<h1> こんにちは、ACSHOPです。 </h1>";
        msgText += "<br>";
        msgText += "<p>下のコードをコピーして入力してください。<p>";
        msgText += "<br>";
        msgText += "<p>ありがとうございます。<p>";
        msgText += "<br>";
        msgText +=
                "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgText += "<h3 style='color:blue;'>会員登録認証コードです。</h3>";
        msgText += "<div style='font-size:130%'>";
        msgText += "CODE : <strong>";
        msgText += emailCodeForVerify + "</strong><div><br/> ";
        msgText += "</div>";
        message.setText(msgText, "utf-8", "html");
        message.setFrom(new InternetAddress("eunaa2934@gmail.com", "eunaa2934"));
    }

    /**
     * 이메일 내용 작성(한글)
     *
     * @param emailCodeForVerify 인증번호
     */
    private void createSinupAuthEmailKO(
            MimeMessage message,
            String emailCodeForVerify
    ) throws Exception {
        message.setSubject("[ACShop]가입을 환영합니다.");

        String msgText = "";
        msgText += "<div style='margin:20px;'>";
        msgText += "<h1> 안녕하세요 ACShop입니다. </h1>";
        msgText += "<br>";
        msgText += "<p>아래 코드를 복사해 입력해주세요.<p>";
        msgText += "<br>";
        msgText += "<p>감사합니다.<p>";
        msgText += "<br>";
        msgText +=
                "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgText += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgText += "<div style='font-size:130%'>";
        msgText += "CODE : <strong>";
        msgText += emailCodeForVerify + "</strong><div><br/> ";
        msgText += "</div>";
        message.setText(msgText, "utf-8", "html");
        message.setFrom(new InternetAddress("eunaa2934@gmail.com", "eunaa2934"));
    }
}
