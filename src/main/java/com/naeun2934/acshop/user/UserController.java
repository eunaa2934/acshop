package com.naeun2934.acshop.user;

import com.naeun2934.acshop.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 화면으로 이동
     *
     * @return signupForm.html 표시
     */
    @GetMapping("/signupForm")
    public String signupForm(Model model) {

        model.addAttribute("user", new User());

        return "/user/signupForm";
    }

    /**
     * 회원가입 처리
     * 이메일 인증 재확인
     * - 실패시, 회원가입 화면으로 다시 이동
     * DB에 유저 정보 저장
     * - 유저가 중복일시, 회원가입 화면으로 다시 이동
     * 성공, 실패 유무 상관없이 이메일 인증관련 정보는 삭제
     * - 실패시, 이메일 재인증
     *
     * @param user               회원가입 하는 유저 정보
     * @param emailCodeForVerify 이메일 인증시 기입한 인증코드
     * @param emailForVerify     이메일 인증시 기입한 이메일
     * @return signup성공 유무에 따른 페이지 표시
     */
    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute User user, BindingResult bindingResult, @SessionAttribute(name = Constant.EMAIL_CODE_FOR_VERIFY, required = false) String emailCodeForVerify, @SessionAttribute(name = Constant.EMAIL_FOR_VERIFY, required = false) String emailForVerify, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            return "/user/signupForm";
        }

        // 이메일 인증 재확인
        //  - 실패시, 회원가입 화면으로 다시 이동
        if (!userService.verifyEmail(emailForVerify, emailCodeForVerify, user)) {

            bindingResult.reject("signupEmailVerify", null, null);
            return "/user/signupForm";

        } else {

            try {
                // DB에 유저 정보 저장
                userService.signup(user);
                return "redirect:/";

            } catch (IllegalStateException e) {
                // - 유저가 중복일시, 회원가입 화면으로 다시 이동
                bindingResult.reject(e.getMessage(), null, null);
                return "/user/signupForm";

            } finally {
                // 성공, 실패 유무 상관없이 이메일 인증관련 정보는 삭제
                // - 실패시, 이메일 재인증
                httpSession.removeAttribute(Constant.EMAIL_FOR_VERIFY);
                httpSession.removeAttribute(Constant.EMAIL_CODE_FOR_VERIFY);
            }

        }

    }


    /**
     * 이메일 인증 처리
     *
     * @param user               회원가입 하는 유저 정보
     * @param emailCodeForVerify 이메일 인증시 기입한 인증코드
     * @param emailForVerify     이메일 인증시 기입한 이메일
     * @return 인증확인 결과
     */
    @ResponseBody
    @PostMapping("/checkEmailCode")
    public boolean checkEmailCode(@RequestBody User user, @SessionAttribute(name = Constant.EMAIL_CODE_FOR_VERIFY, required = false) String emailCodeForVerify, @SessionAttribute(name = Constant.EMAIL_FOR_VERIFY, required = false) String emailForVerify) {

        return userService.verifyEmail(emailForVerify, emailCodeForVerify, user);

    }

    /**
     * 로그인 화면으로 이동
     * 처음 로그인 화면에 접속한 경우, error가 false인 경우
     * - 이전 Url정보(previousUrl) 저장
     *
     * @param error 로그인 실패해서 다시 로그인 화면으로 돌아온건지 유무 확인
     * @return loginForm.html 표시
     */
    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(name = "error", required = false) boolean error, Model model, HttpServletRequest request) {

        // 처음 로그인 화면에 접속한 경우, error가 false인 경우
        if (!error) {
            // - 이전 Url정보(previousUrl) 저장
            String previousUrl = request.getHeader("referer");
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute(Constant.PREVIOUS_URL, previousUrl);
        }

        model.addAttribute("user", new User());

        return "/user/loginForm";

    }

    /**
     * 로그인 성공시 처리
     * String previousUrl에 previousUrl정보 저장한뒤 session의 정보는 삭제
     * 이전 Url정보(previousUrl)이 있으면 이전 Url로 이동
     * - 그외의 경우 메인화면으로 이동
     *
     * @return previousUrl 유무에 따른 페이지 표시
     */
    @PostMapping("/loginSuccess")
    public String loginSuccess(HttpSession httpSession) {

        // String previousUrl에 정보 저장한뒤 session의 정보는 삭제
        String previousUrl = (String) httpSession.getAttribute(Constant.PREVIOUS_URL);
        httpSession.removeAttribute(Constant.PREVIOUS_URL);

        // 이전 Url정보(previousUrl)이 있으면 이전 Url로 이동
        if (previousUrl != null) {
            return "redirect:" + previousUrl;

            // - 그외의 경우 메인화면으로 이동
        } else {
            return "redirect:/";
        }

    }

    /**
     * 마이페이지 화면으로 이동
     * <p>
     * 세션의 userId로부터 유저 정보 취득
     *
     * @return userUpdateForm.html 표시
     */
    @GetMapping("/myPage")
    public String updateUserForm(Model model, Principal principal, @SessionAttribute(name = Constant.LOGIN_USERID, required = false) Long userId, HttpSession httpSession) {

        User user;

        // 세션에 정보가 없으면 Principal로 부터 조회
        if (userId == null) {
            String userEmail = principal.getName();
            user = userService.findByUserEmail(userEmail);
            httpSession.setAttribute(Constant.LOGIN_USEREMAIL, user.getUserEmail());
            httpSession.setAttribute(Constant.LOGIN_USERID, user.getId());
            userId = user.getId();
        }

        // 세션의 userId로부터 유저 정보 취득
        user = userService.makeUserUpdateForm(userId);

        model.addAttribute("user", user);

        return "/user/userUpdateForm";

    }

    /**
     * 유저 정보 수정 처리
     */
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user, BindingResult bindingResult, @SessionAttribute(name = Constant.LOGIN_USERID) Long userId, Model model) {
        try {
            userService.save(user, userId);
            model.addAttribute("updateUserSuccess", true);
        } catch (IllegalStateException e) {
            // - 유저가 중복일시, 회원가입 화면으로 다시 이동
            bindingResult.reject(e.getMessage(), null, null);
        }
        return "user/userUpdateForm";
    }

}
