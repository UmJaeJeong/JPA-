package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    //상품주문 테스트
    @Test
    public void productOrderTest() {
        //given
        Member member = createMember();

        Book book = createBook("지연이괴롭히기",10000,10);

//        em.persist(book);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상태는 ORDER",OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품의 종류수가 정확해야한다.",1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 *수량이다.",1, getOrder.getOrderItems().size());
        assertEquals("주문 수량만큼 재고가 줄어야한다.",8,book.getStockQuantity());
    }

    //재고수량 초과  테스트
    @Test(expected = NotEnoughStockException.class)
    public void overStockTest(){
        //given
        Member member = createMember();
        Book book = createBook("JPA", 15000, 20);

        int orderCount = 21;
        //when
        Long getOrder = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        fail("재고수량 예외가 발생해야한다.");
    }

    //주문취소 테스트
    @Test
    public void orderCancelTest(){
        //given
        Member member = createMember();
        Book book = createBook("ORM", 5000, 10);

        int orderCount = 5;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when

        orderService.cancelOrder(orderId);

        //then
        Order cancelOrder = orderRepository.findOne(orderId);
        Assert.assertEquals("주문 취소시 형태는 CANCEL이다.",OrderStatus.CANCEL,cancelOrder.getStatus());
        Assert.assertEquals("주문 취소시 형태는 CANCEL이다.",10,book.getStockQuantity());


        //Assertions.assertThat(10).isEqualTo(book.getStockQuantity());
    }


    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }
}