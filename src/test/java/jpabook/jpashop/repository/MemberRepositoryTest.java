package jpabook.jpashop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(value = false)
class MemberRepositoryTest {

  @Autowired MemberRepository 회원저장소;

  @Test
  @DisplayName("Member 저장 후 검색 테스트")
  @Transactional
  void memberSaveTest() throws Exception {
    String 회원이름 = "brothersoo";
    Member 회원 = new Member();
    회원.setName(회원이름);

    Long 회원Id = 회원저장소.save(회원);

    assertThat(회원저장소.find(회원Id).getId()).isEqualTo(회원Id);
    assertThat(회원저장소.find(회원Id).getName()).isEqualTo(회원이름);
  }
}
