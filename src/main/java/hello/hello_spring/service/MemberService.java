package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional // JPA를 통한 데이터 변경은 꼭 트랜잭션 안에서 실행해야 된다.
public class MemberService {

    private final MemberRepository memberRepository;


    // 사용하는 외부에서 매개변수로 객체를 넣어주기 위해 생성자 함수로 뺌 (test케이스랑 같은 객체를 사용하려는 의도)
    // 오토와이어드로 스프링 컨테이너에 있는 스프링빈을 주입시킨다. DI 의존관계 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // 회원가입
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);   // 검증된 뒤에 객체 저장
        return member.getId();
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);

    }

    // 중복회원 검증 메서드
    private void validateDuplicateMember(Member member) {
        /*
            Optional<Member> result = memberRepository.findByName(member.getName());
            result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
            });
            이프프리전트(옵셔널메서드인듯)는 그 값이 널이 아니면 (값이 존재하면) 어떤 동작을 한다
            옵셔널이기 때문에 가능하다, 기존에는 값이 없으면으로 조건문을 썼겠지만
        */
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        });
    }
}
