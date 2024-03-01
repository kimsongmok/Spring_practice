package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
//@Service
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  //@Autowired
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  /**
   * 회원가입
   */

  public Long join(Member member){

    long start = System.currentTimeMillis();
    try {
      validateDuplicateMember(member); //중복회원 검증
      memberRepository.save(member);
      return member.getId();
    }finally {
      long finish = System.currentTimeMillis();
      long timeMS = finish - start;
      System.out.println("join = " + timeMS + "ms");

    }
  }

  private void validateDuplicateMember(Member member) {
    memberRepository.findByName(member.getName())
            .ifPresent(m -> {
              throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
  }

  /**
   * 전체 회원 조회
   */
  public List<Member> findMembers(){
    long start = System.currentTimeMillis();
    try {
      return memberRepository.findAll();
    } finally {
      long finish = System.currentTimeMillis();
      long timeMS = finish - start;
      System.out.println("findMembers = " + timeMS + "ms");
    }
  }

  public Optional<Member> findOne(Long memberId) {
    return memberRepository.findById(memberId);
  }
}
