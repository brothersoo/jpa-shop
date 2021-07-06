package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  /**
   * 회원가입
   */
  @Transactional
  public Long join(Member member) {
    validateDuplicatedMemberName(member);
    memberRepository.save(member);
    return member.getId();
  }

  private void validateDuplicatedMemberName(Member member) {
    if (!memberRepository.findByName(member.getName()).isEmpty()) {
      throw new IllegalStateException("이미 존재하는 이름입니다.");
    }
  }

  /**
   * 회원 전체 조회
   */
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  /**
   * 회원 단일 조회
   */
  public Member findOne(Long memberId) {
    return memberRepository.findOne(memberId);
  }
}
