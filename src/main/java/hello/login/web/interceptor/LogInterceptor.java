package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

  public static final String LOG_ID = "logID";

  /**
   * 컨트롤러 호출 전에 호출
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    String requestURI = request.getRequestURI();

    String uuid = UUID.randomUUID().toString();
    request.setAttribute(LOG_ID, uuid);

    if (handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler;
      /**
       * HandlerMethod: 호출할 컨트롤러 메소드의 모든 정보가 포함되어 있음
       *
       * @RequestMapping: HandlerMethod
       * 정적 리소스: ResourceHttpRequestHandler
       */
    }

    log.info("request [{}], [{}], [{}]", uuid, requestURI, handler);
    return true;

  }


  /**
   * 컨트롤러 호출 이 후에 호출
   */
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    log.info("postHandle modelAndView [{}]", modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    String requestURI = request.getRequestURI();
    String logId = (String) request.getAttribute(LOG_ID); // logId == uuid

    log.info("response [{}], [{}]", logId, requestURI);

    if (ex != null) {
      log.error("after completion error !!", ex);
    }

  }

}
