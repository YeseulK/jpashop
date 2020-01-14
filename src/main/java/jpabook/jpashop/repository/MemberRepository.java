package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext //직접 엔티티 매니저를 팩토링할 필요가없게 해준다.
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
        /*
        em.persist한 순간에 영속성 context에 member를 올림
        영속성 context의 key는 Member class의 id값
        */
    }

    public Member findOne(Long id){
        return em.find(Member.class, id); // (타입, PK)
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        //jpql은 sql가 약간 다르다. 테이블이 아닌 객체를 대상으로 쿼리를 한다.
    }

    public List<Member> findByName(String name){ // 파라미터 바인딩. 특정 이름에 대한 회원 검색
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
