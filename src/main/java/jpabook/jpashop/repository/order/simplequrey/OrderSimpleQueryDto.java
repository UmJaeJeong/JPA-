package jpabook.jpashop.repository.order.simplequrey;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderData;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderData = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
    }

    public OrderSimpleQueryDto(Long orderId, String name,LocalDateTime orderData, OrderStatus orderStatus, Address address ) {
        this.orderId = orderId;
        this.name = name;
        this.orderData = orderData;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
