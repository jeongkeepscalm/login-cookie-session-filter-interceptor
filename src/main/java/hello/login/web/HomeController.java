package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

  private final MemberRepository memberRepository;

  // 로그인이 이미 된 사용자는 재로그인할 필요 없으므로 LoginHome.html 으로 보낸다.
  @GetMapping("/")
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

}