package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordesV1(){

        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        for(Order o : orders){
            o.getMember().getName();
            o.getDelivery().getAddress();

            List<OrderItem> orderItems = o.getOrderItems();
            /*for(OrderItem orderItem : orderItems){
                orderItem.getItem().getName();
            }*/
            orderItems.stream().map(item -> item.getItem().getName());
        }
        return orders;

    }

}
