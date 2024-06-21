package hello.login.domain.member;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class Member {

  private Long id;

  /**
   * @NotEmpty
   *    허용 x: "", null
   *    허용 o: " "
   */

  @NotEmpty
  private String loginId;

  @NotEmpty
  private String name;

  @NotEmpty
  private String password;

}
