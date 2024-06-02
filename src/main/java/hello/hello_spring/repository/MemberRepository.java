package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.List;
import java.util.Optional;

// 개발 중 아직 정해진 데이터베이스가 없을 때 우선 인터페이스로 만든다.
// Optional: java 8에 들어간 기능으로써 반환값이 null일 때 Optional로 한번 감싸서 반환해 주는 것이다.
public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);

    List<Member> findAll();
}
