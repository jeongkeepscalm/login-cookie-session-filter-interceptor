package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.*;

@Repository
@Slf4j
public class MemberRepository {

  private static Map<Long, Member> store =  new HashMap<>();
  private static long sequence = 0L;

  public Member save(Member member) {
    member.setId(++sequence);
    store.put(member.getId(), member);
    return member;
  }

  public Member findById(Long id) {
    return store.get(id);
  }

  public List<Member> findAll() {
    return new ArrayList<>(store.values());
    // store.values();: return type == Collection<Member>
    // return store.values().stream().collect(Collectors.toList());
  }

  public Optional<Member> findByLoginId(String loginId) {
    return findAll().stream().filter(v -> v.getLoginId().equals(loginId)).findFirst();
  }

  public void clearStore() {
    store.clear();
  }



}
