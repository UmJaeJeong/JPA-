package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional  //@Transactional은 Test케이스에 있을 경우 자동으로 Rollback을 해버린다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

//    @Autowired
//    EntityManager em;

    @Test
//    @Rollback(value = false)
    public void join() {
        //given
        Member member = new Member();
        member.setName("엄재정");

        //when
        Long saveId = memberService.join(member);

        //then
        //em.flush();
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void duplicationMemberException() {
        //given
        Member member1 = new Member();
        member1.setName("엄재정");

        Member member2 = new Member();
        member2.setName("엄재정");

        //when
        Long saveId1 = memberService.join(member1);
        Long saveId2 = memberService.join(member2);

       /* em.flush();
        try{
            Long saveId2 = memberService.join(member2);
        }catch(IllegalStateException ise){
            return;
        }
*/
        //then
        Assert.fail("예외가 떠야한다.");
    }

}