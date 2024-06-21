package hello.login.validation;

import hello.login.domain.item.Item;
import hello.login.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


public class BeanValidationTest {

  private Validator validator;


  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  void beanValidation_item() {

    Item item = new Item();
    item.setItemName("  ");
    item.setPrice(0);
    item.setQuantity(10000);

    Set<ConstraintViolation<Item>> violations = validator.validate(item);
    violations.forEach(v -> System.out.println(v));

  }


  @Test
  void beanValidation_member() {

    Member member = Member.builder()
            .loginId("")
            .password("test123")
            .name("tester")
            .build();

    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(v -> System.out.println(v));

  }

}