package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    // 함수가 실행되기 전에 실행되는 함수
    @BeforeEach
    public void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        // 이렇게 되면 기존의 멤버서비스와 동일한 리파지토리를 사용할 수 있다.
        // 서비스 입장에서는 자신이 직접 new로 객체를 생성하지 않고 외부에서 넣어주는 것을 DI라고 한다
        memberService = new MemberService(memoryMemberRepository);
    }
    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given (어떤 상황이 주어졌을 때(이 데이터가 주어졌을 때))
        Member member = new Member();
        member.setName("이름1");

        // when (이거를 실행했을 때)
        Long saveId = memberService.join(member);

        // then (이런 결과가 나와야 한다)
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("이름1");

        Member member2 = new Member();
        member2.setName("이름1");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 이름입니다.");
//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 이름입니다.");
//        }
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}