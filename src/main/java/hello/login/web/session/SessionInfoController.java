package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.DateTimeException;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {


  @GetMapping("/session-info")
  public String sessionInfo(HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "세션이 없습니다. ";
    }

    session.getAttributeNames()
            .asIterator()
            .forEachRemaining(name -> log.info("session name = {}, value = {}", name, session.getAttribute(name)));
    // session name = loginMember, value = Member(id=1, loginId=test, name=tester, password=test123)

    log.info("sessionId = {}", session.getId());
    log.info("maxInactiveInterval = {}", session.getMaxInactiveInterval());
    log.info("creation time = {}", new Date(session.getCreationTime()));
    log.info("lastAccessedTime = {}", new Date(session.getLastAccessedTime()));
    log.info("isNew = {}", session.isNew());

    /*
      sessionId = B98D33180535717ACE4525C5756F84A3
      maxInactiveInterval = 1800 (1800초 == 30분)
      creation time = Tue Jun 25 13:57:51 KST 2024
      lastAccessedTime = Tue Jun 25 13:57:52 KST 2024
      isNew = false
    */

    return "print session";

  }

}
