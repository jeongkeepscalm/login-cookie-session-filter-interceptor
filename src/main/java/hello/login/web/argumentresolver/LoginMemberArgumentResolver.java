package hello.login.web.argumentresolver;

import hello.login.domain.login.web.SessionConst;
import hello.login.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {

    log.info("supportsParameter() 실행");

    // 파라미터에 @Login 어노테이션이 붙어 있는지 확인
    boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);

    // 파라미터 타입이 Member 또는 그 하위 타입인지 확인
    boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

    // @Login 어노테이션이 붙은 타입이 Member 이면 true
    return hasLoginAnnotation && hasMemberType;

  }

  /**
   * resolveArgument()
   *    컨트롤러 호출 직전에 호출되어 필요한 파라미터 정보를 생성해준다.
   *    로그인 회원 정보인 member 객체를 찾아 반환하고,
   *    스프링 mvc는 컨트롤러의 메소드를 호출하면서 여기서 반환된 member 객체를 파라미터에 전달해준다.
   *
   *    => 파라미터 내 @Login Member 를 찾아 세션에 회원 정보가 있다면 매핑시켜준다.
   */

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    log.info("resolverArgument() 실행");

    /*
      세션에 멤버 정보가 있다면 해당 멤버를 리턴
      그렇지 않다면 null 리턴
    */
    HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
    HttpSession session = httpServletRequest.getSession(false);
    if (session == null) {
      return null;
    }
    return session.getAttribute(SessionConst.LOGIN_MEMBER);

  }


}
