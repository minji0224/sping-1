package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 모든 test메서드가 실행되고 끝나기 전에 실행되는 콜백함수(데이터클리어해줌)
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("이름1");

        repository.save(member);

        // findById메서드는 리턴값이 옵셔널인데 그 옵셔널에 값을 꺼내려면 .get()으로 한번 더 꺼내와야된다.
        // 뒤에 get()으로 한번 더 꺼내면 옵셔널을 까서 꺼내는거기때문에 기존 타입으로 꺼내짐 get()안하면 옵셔널타입으로 해야됨
        Member result = repository.findById(member.getId()).get();

        System.out.println("result: " + (result == member));
        /*
            Assertions.assertEquals(member,result);
            Assertions.assertThat(member).isEqualTo(result);
         */
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("이름1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("이름2");
        repository.save(member2);

        Member result = repository.findByName("이름1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("이름1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("이름2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);

    }








}
