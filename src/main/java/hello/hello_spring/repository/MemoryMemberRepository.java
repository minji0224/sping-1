package hello.hello_spring.repository;

import com.sun.source.tree.UsesTree;
import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

// MemberRepository 인터페이스의 구현체인 클래드다.
// 클래스명에 옵션엔터치면 자동 오버라이드됨
public class MemoryMemberRepository implements MemberRepository{

    // 실무에서는 이렇게 공유되는 데이터(stort)같은 경우는 동시성 문제때문에 컨커런트해쉬맵을 사용
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // id값 키값 증가 변수


    @Override
    public Member save(Member member) {
        // 우선 들어온 멤버의 id를 만들어주고 저장한 다음에
        member.setId(++sequence);
        // 만든 id값을 map의 키값으로도 동일하게 넣어준다
        // 이유는 이래야 고유한 id값으로 member를 찾기 편함.
        // 뒤에 member는 id와 name값 다 있음.
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // store.get(id)가 null일 수 있기 때문에 옵셔널로 감쌈
        // 감싸서 반환해 주면 그 값을 클라이언트에서 조정함.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // findAny는 옵셔널로 반환해줌
        return store.values().stream().filter(mem -> mem.getName().equals(name)).findAny();
        // 1. store.values(): store의 모든 "값"들, 즉 member객체를 컬렌션으로 반환해줌.//멤버필드 탐색이 가능한 이유
        // 2. .stream(): 위 컬렉션을 스트림으로 변환. 스트림은 데이터를 일렬로 처리할 수 있는 유연한 도구입니다.
        // 3. .filter: 조건을 만족하는 객체들만 스트림에 남깁니다.
        // 4. .findAny(): 필터링된 스트림에서 임의의 하나의 Member 객체를 찾습니다. 이 메서드는 Optional<Member>를 반환.
         /* 맵에서는 키 값으로 검색하는게 일반적이지만
           store에 "값"들은 멤버객체이며 객체들의 특정 필드(name/id)값으로 필터링해야되기 때문에
           스트림을 사용하여* 필터링 작업을 했다. */

    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
