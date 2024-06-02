package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 스프링데이터jpa
// JpaRepository 확장하고 있으면 스프링에서 자동으로 구현체를 만들어서 빈에 등록해준다.
// 내가 스프링빈에 등록하는게 아님
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // 기본적인 쿼리들은 제공되기 때문에 굳이 오버라이드할 필요도 없음.
    @Override
    Optional<Member> findByName(String name);
}
