package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderrQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    private final OrderrQueryRepository orderrQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){

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

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        return all.stream().map(o->new OrderDto(o))
        .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        return orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_page(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderrQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        return orderrQueryRepository.findAllByDto_optimization();
    }

    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderData;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;


        public OrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderData = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(o -> new OrderItemDto(o))
                    .collect(Collectors.toList());

        }
    }


    @Data
    static class OrderItemDto{
        private String itemName; //상품명
        private int orderPrice; //상품가격
        private  int count; //상품갯수

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }

}
