package jpabook.jpashop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberRepositoryTest {

  @Autowired MemberRepository 회원저장소;

  @Test
  @DisplayName("Member 저장 후 검색 테스트")
  void memberSaveTest() throws Exception {
    String 회원이름 = "brothersoo";
    Member 회원 = new Member();
    회원.setName(회원이름);

    회원저장소.save(회원);

    assertThat(회원저장소.findOne(회원.getId()).getId()).isEqualTo(회원.getId());
    assertThat(회원저장소.findOne(회원.getId()).getName()).isEqualTo(회원이름);
  }

  @Test
  @DisplayName("회원 전체 검색")
  void memberFindAllTest() throws Exception {
    Member 회원1 = new Member();
    Member 회원2 = new Member();
    회원1.setName("brothersoo");
    회원2.setName("moistybro");

    회원저장소.save(회원1);
    회원저장소.save(회원2);

    List<Member> 전체회원리스트 = 회원저장소.findAll();
    assertThat(전체회원리스트.size()).isEqualTo(2);
    assertThat(전체회원리스트).contains(회원1);
    assertThat(전체회원리스트).contains(회원2);
  }
}
