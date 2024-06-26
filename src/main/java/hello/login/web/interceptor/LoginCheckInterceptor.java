package hello.login.web.interceptor;

import hello.login.domain.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

  /**
   * 인증이라는 것은 컨트롤러 호출 전에만 호출되어야 하기에 preHandle 만 구현하면 된다. 
   */

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    String requestURI = request.getRequestURI();

    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
      log.info("미인증 사용자의 요청: {}", requestURI);
      response.sendRedirect("/login?redirectURL=" + requestURI);
      return false;
    }

    return true;

  }

}
