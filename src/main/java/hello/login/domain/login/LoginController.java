package hello.login.domain.login;

import hello.login.domain.login.web.SessionConst;
import hello.login.domain.member.Member;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final SessionManager sessionManager;

  @GetMapping("/login")
  public String loginForm(@ModelAttribute("loginForm") LoginRequest form) {
    return "login/loginForm";
  }

//  @PostMapping("/login")
  public String login(@Validated @ModelAttribute(name = "loginForm") LoginRequest form
          , BindingResult bindingResult
          , HttpServletResponse response) {

    if (bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
    log.info("login? {}", loginMember);

    if (loginMember == null) {
      // ObjectError
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    // login success logic

    // add Cookie
    Cookie loginIdCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
    response.addCookie(loginIdCookie);

    return "redirect:/";

  }

  // @PostMapping("/login")
  public String loginV2(@Validated @ModelAttribute(name = "loginForm") LoginRequest request
          , BindingResult bindingResult
          , HttpServletResponse response) {

    // 오류 확인
    if (bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    // 유저 조회
    Member loginMember = loginService.login(request.getLoginId(), request.getPassword());
    log.info("login member = {}", loginMember);

    // 실제 유저 검증
    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    // 세션저장소에 유저 추가 및 쿠키 추가
    sessionManager.createSession(loginMember, response);
    return "redirect:/";

  }

//  @PostMapping("/login")
  public String LoginV3(@Validated @ModelAttribute(name = "loginForm") LoginRequest loginRequest
          , BindingResult bindingResult
          , HttpServletRequest request) {

    // 오류 확인
    if (bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    // 유저 조회
    Member loginMember = loginService.login(loginRequest.getLoginId(), loginRequest.getPassword());
    log.info("login member = {}", loginMember);

    // 실제 유저 검증
    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    // 세션이 있으면 세션 반환, 없으면 세션 생성
    HttpSession session = request.getSession();
    
    /**
     * request.getSession(true) == request.getSession()
     *    세션이 존재하면 기존 세션 반환
     *    없다면, 새로운 세션 생성 후 반환
     * 
     * request.getSession(false)
     *    세션이 존재하면 기존 세션 반환
     *    없다면, 새로운 세션 생성 x. null 반환
     */

    // 세션에 로그인 회원 정보 보관
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    return "redirect:/";

  }

  @PostMapping("/login")
  public String LoginV4(
          @Validated @ModelAttribute(name = "loginForm") LoginRequest loginRequest
          , BindingResult bindingResult
          , @RequestParam(defaultValue = "/") String redirectURL
          , HttpServletRequest request) {

    if (bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(loginRequest.getLoginId(), loginRequest.getPassword());
    log.info("login: {}", loginMember);

    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    // 세션에 로그인 회원 정보 보관
    request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    return "redirect:" + redirectURL;
  }

//  @PostMapping("/logout")
  public String logout(HttpServletResponse response) {
    expireCookie(response, "memberId");
    return "redirect:/";
  }

//  @PostMapping("/logout")
  public String logoutV2(HttpServletRequest request) {
    sessionManager.expire(request);
    return "redirect:/";
  }

  @PostMapping("/logout")
  public String logoutV3(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      // 세션 제거
      session.invalidate();
      
      // 클라이언트 쿠키 삭제
      expireCookie(response, "JSESSIONID");

    }
    return "redirect:/";
  }

  private void expireCookie(HttpServletResponse response, String cookieName) {
    Cookie cookie = new Cookie(cookieName, null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }

}
