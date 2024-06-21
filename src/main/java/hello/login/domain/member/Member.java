package hello.login.domain.member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class Member {

  private Long id;

  /**
   * @NotEmpty
   *    허용 x: "", null
   *    허용 o: " "
   */

  @NotEmpty(message = "빈 값은 허용되지 않습니다.")
  private String loginId;

  @NotEmpty(message = "빈 값은 허용되지 않습니다.")
  private String name;

  @NotEmpty(message = "빈 값은 허용되지 않습니다.")
  private String password;

  @Builder
  public Member(String loginId, String name, String password) {
    this.loginId = loginId;
    this.name = name;
    this.password = password;
  }

}
