package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfiguration_Filter {

  @Bean
  public FilterRegistrationBean logFilter() {
    FilterRegistrationBean<Filter> frb = new FilterRegistrationBean<>();
    frb.setFilter(new LogFilter());
    frb.setOrder(1);
    frb.addUrlPatterns("/*");
    return frb;

    /**
     * frb.addUrlPatterns("/*");
     *    모든 요청에 해당 필터 적용
     */
  }

  @Bean
  public FilterRegistrationBean loginCheckFilter() {
    FilterRegistrationBean<Filter> frb = new FilterRegistrationBean<>();
    frb.setFilter(new LoginCheckFilter());
    frb.setOrder(2);
    frb.addUrlPatterns("/*");
    return frb;
  }

}



