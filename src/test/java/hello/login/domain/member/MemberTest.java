package hello.login.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class MemberTest {

  private MemberRepository memberRepository;

  @BeforeEach
  void setUp() {
    memberRepository = new MemberRepository();
    Member member = Member.builder()
            .loginId("test")
            .password("test123")
            .name("tester")
            .build();
    memberRepository.save(member);
  }

  @AfterEach
  void tearDown() {
    memberRepository.clearStore();
  }

  @Test
  void optionalTest() {
    Optional<Member> member = memberRepository.findByLoginId("test");
    Optional<Member> member2 = memberRepository.findByLoginId("test2");
    System.out.println("member: " + member);
    System.out.println("member2: " + member2);

    Member result = member.filter(v -> v.getPassword().equals("test123")).orElse(null);
    Member result2 = member.filter(v -> v.getPassword().equals("test567")).orElse(null);
    System.out.println("result : "+ result);
    System.out.println("result2 : "+ result2);
  }

}
