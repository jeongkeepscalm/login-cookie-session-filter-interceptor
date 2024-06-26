package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("log filter init");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    // ServletRequest → HttpServletRequest: 다운 캐스팅
    HttpServletRequest hsr = (HttpServletRequest) servletRequest;

    String requestURI = hsr.getRequestURI();
    String uuid = UUID.randomUUID().toString();

    try {
      log.info(":: request [{}] [{}]", uuid, requestURI);
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (Exception e) {
      throw e;
    } finally {
      log.info(":: response [{}] [{}]", uuid, requestURI);
    }

  }

  @Override
  public void destroy() {
    log.info("log filter destroy");
  }

}
