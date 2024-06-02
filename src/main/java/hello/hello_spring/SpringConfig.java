package hello.hello_spring;
// DI를 할 때 생성자어노테이션과 오토와이어드를 사용하지 않고 하는 방법
// 스프링 컨테이너에 등록 -> 빈 생성하는 방법임

import hello.hello_spring.repository.JdbcTemplateMemberRepository;
import hello.hello_spring.repository.JpaMemberRepository;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration // 스프링 설정 클래스(빈 정의 등)
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


//    private final DataSource dataSource; // jdbc 템플릿에서 쓰는것
//    private final EntityManager em; // jda에서 쓰는 것
//    @Autowired
//    public SpringConfig(DataSource dataSource, EntityManager em) {
//        this.dataSource = dataSource;
//        this.em = em;
//    }


    @Bean // 스프링 빈 등록하겠다는 선언
    public MemberService memberService (){
        // return new MemberService(memberRepository());
        // 스프링 빈에 등록된 memberRepository 빈은 클래스 객체 생성시점에 매개변수로 넣어줌
        // 반환된 객체가 스프링 컨테이너에 빈으로 등록
        // 이후에 필요한 곳에서 이 빈을 주입받아 사용할 수 있게 됩니다.
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//    }
}
