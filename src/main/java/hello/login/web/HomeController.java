package hello.login.web;

import hello.login.domain.login.web.SessionConst;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.privilegedactions.GetMethodFromGetterNameCandidates;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

  private final MemberRepository memberRepository;
  private final SessionManager sessionManager;

  // 로그인이 이미 된 사용자는 재로그인할 필요 없으므로 LoginHome.html 으로 보낸다.
  // @GetMapping("/")
  public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

    // 쿠키가 없다면 로그인 해야되는 페이지로 보낸다.
    if (memberId == null) {
      return "home";
    }

    // 멤버가 아니라면 로그인 해야되는 페이지로 보낸다.
    Member loginMember = memberRepository.findById(memberId);
    if (loginMember == null) {
      return "home";
    }

    model.addAttribute("member", loginMember);
    return "loginHome";

  }

  // "/" url 접근시 세션 체크
  // @GetMapping("/")
  public String homeLoginV2(HttpServletRequest request, Model model) {
    Member loginMember = (Member) sessionManager.getSession(request);
    if (loginMember == null) {
      return "home";
    }
    model.addAttribute("member", loginMember);
    return "loginHome";
  }


  // @GetMapping("/")
  public String homeLoginV3(HttpServletRequest request, Model model) {

    // 세션이 없을 경우
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "home";
    }

    // 세션에 회원 정보가 없을 경우
    Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    if (loginMember == null) {
      return "home";
    }

    // 세션이 유지되면 로그인으로 이동
    model.addAttribute("member", loginMember);
    return "loginHome";

  }

  @GetMapping("/")
  public String homeLoginV4(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
          , Model model) {

    // 세션에 회원 정보가 없을 경우
    if (loginMember == null) {
      return "home";
    }

    // 세션이 유지되면 로그인 이동
    model.addAttribute("member", loginMember);
    return "loginHome";

  }

}