package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdataTest {

    //@Autiwured 일종의 인텔리J의 버그
    @PersistenceContext
    EntityManager em;

    @Test
    public void updateTest(){
        Book book = em.find(Book.class, 1L);

        //TX
        book.setName("blaablaa");

        //변경감지== dirty checking
        //TX commit

    }
}
