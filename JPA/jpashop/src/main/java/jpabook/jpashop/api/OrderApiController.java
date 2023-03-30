package jpabook.jpashop.api;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.query.OrderQueryDto;
import jpabook.jpashop.repository.query.OrderQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItem.getItem().getName();
            }
        }
        return all; //(lazy로딩) 초기화 해서 빈 프록시를 반환하는 일을 사전 방지
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2(){
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        return orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3(){// 페이징 기능 불가 데이터가 뻥튀기 되므로(디비상에서)
        List<Order> orders = orderRepository.findAllWithItem();
        for(Order order : orders){
            System.out.println("order ref =" + order + " id = " + order.getId());
        }
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_page(@RequestParam(name = "offset" ,defaultValue = "0") int offset,
                                       @RequestParam(name = "limit" , defaultValue = "100") int limit){// 페이징 기능 불가 데이터가 뻥튀기 되므로(디비상에서)
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        for(Order order : orders){
            System.out.println("order ref =" + order + " id = " + order.getId());
        }
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        return orderQueryRepository.findAllByDto_Optimization();
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
    @Data
    static class OrderDto{  //Dto안에 Entity가 있으면 안된다
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order o) {
            this.orderId = o.getId();
            this.name = o.getMember().getName();
            this.orderDate = o.getOrderDate();
            this.orderStatus = o.getStatus();
            this.address = o.getDelivery().getAddress();
            this.orderItems = o.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)).collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto{
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem){
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getTotalPrice();
            this.count = orderItem.getCount();
        }
    }

}
