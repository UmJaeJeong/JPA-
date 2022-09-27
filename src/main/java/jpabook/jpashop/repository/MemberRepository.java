package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //스프링이EntityManager을  만들어서em에 주입해줌
//    @PersistenceContext  -> RequiredArgsConstructor가 알아서 해주기에 PersistenceContext를사용하지 않아도 된다.
    private final EntityManager em;

    //아래와 같이 Factory를 주입받고 싶다면 이렇게 활용 Factory를쓸일은 잘 없음
//   @PersistenceUnit
//    private EntityManagerFactory emf;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        //class Type 다음과 PK넣어주기
        return em.find(Member.class, id);
    }

    //SQL와 JPQL의 차이점은 SQL 같은 경우 TABLE을 대상으로 조회를 하지만,
    //JPQL같은 경우에는 Entity 객체를 대상으로 한다.
    public List<Member> findAll(){
        return em.createQuery("select  m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
