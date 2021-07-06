package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  MemberService 회원서비스;
  @Autowired
  MemberRepository 회원저장소;

  @Test
  @DisplayName("회원가입 후 아이디 확인")
  void 회원가입() throws Exception {
    Member 회원 = new Member();
    String 회원이름 = "brothersoo";
    회원.setName(회원이름);

    Long 저장회원아이디 = 회원서비스.join(회원);
    assertThat(회원저장소.findOne(저장회원아이디)).isEqualTo(회원);
  }

  @Test
  @DisplayName("중복 아이디로 회원가입시 실패")
  void 중복아이디오류() throws Exception {
    Member 회원1 = new Member();
    Member 회원2 = new Member();
    String 회원이름 = "brothersoo";

    회원1.setName(회원이름);
    회원2.setName(회원이름);

    회원서비스.join(회원1);

    assertThrows(
        IllegalStateException.class,
        () -> 회원서비스.join(회원2)
    );
  }

  @Test
  @DisplayName("전체 회원 검색")
  void 전체회원검색() throws Exception {
    Member 회원1 = new Member();
    Member 회원2 = new Member();
    회원1.setName("brohersoo");
    회원2.setName("moistybro");

    회원서비스.join(회원1);
    회원서비스.join(회원2);

    List<Member> membersList = 회원서비스.findMembers();
    assertThat(membersList.size()).isEqualTo(2);
    assertThat(membersList.contains(회원1)).isTrue();
    assertThat(membersList.contains(회원2)).isTrue();
  }

  @Test
  @DisplayName("아이디로 회원 검색")
  void 아이디회원검색() throws Exception {
    Member 회원1 = new Member();
    Member 회원2 = new Member();
    회원1.setName("brohersoo");
    회원2.setName("moistybro");

    회원서비스.join(회원1);
    회원서비스.join(회원2);

    assertThat(회원서비스.findOne(회원1.getId())).isEqualTo(회원1);
  }
}