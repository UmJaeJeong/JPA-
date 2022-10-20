package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequrey.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();//Lazy 강제초기화
            order.getDelivery().getAddress();//Lazy 강제초기화
        }

        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> orderV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderSimpleQueryDto> collect = orders.stream().map(o -> new OrderSimpleQueryDto(o)).collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> orderV3() {
        List<Order> orders = orderRepository.findAllMemberWithDelivery();
        List<OrderSimpleQueryDto> collect = orders.stream().map(o ->
                new OrderSimpleQueryDto(o)
        ).collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4(){
        return orderRepository.findOrderDtos();
    }


    //세부사항을 정확히 명시해야한다.
/*    @Data
    static class SimpleOrderDTO {
        private Long orderId;
        private String name;
        private LocalDateTime orderData;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDTO(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderData = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }*/
}
