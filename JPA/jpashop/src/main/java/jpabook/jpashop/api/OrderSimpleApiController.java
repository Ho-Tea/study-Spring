package jpabook.jpashop.api;



import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.SimpleOrderQueryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all){
            order.getMember().getName();    //Lazy 강제 초기화
            order.getDelivery().getAddress();   //Lazy 강제 초기화
        }
        return  all;
    }

    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2(){
        // Lazy
        //Order 2개 -> 총 5개의 쿼리가 나갔다
        // 1 + N
        // N + 1 같은말
        // 1 + (Member)N + (Delivery)N -> 5번
        // Eager을 쓰면 같이 갖고올려고 시도는 하지만 연관관계가 걸려있는 모든것들을 가져오므로 쿼리 예측이 불가능하다
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        List<SimpleOrderDto> collect = all.stream()
                .map(m -> new SimpleOrderDto(m))
                .collect(Collectors.toList());

        return new Result(collect);
    }// -> 5번의 쿼리

    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3(){
        List<Order> all = orderRepository.findAllWithMemberDelivery();
        return new Result(all.stream()
                .map(m -> new SimpleOrderDto(m))
                .collect(Collectors.toList()));
    }// -> 1번의 쿼리


    @GetMapping("/api/v4/simple-orders")
    public Result orderV4(){
        return new Result(orderRepository.findOrderDtos());
    }



    @Data
    static class SimpleOrderDto {
        public SimpleOrderDto(Order o) {
            this.orderId = o.getId();
            this.name = o.getMember().getName();
            this.orderDate = o.getOrderDate();
            this.orderStatus = o.getStatus();
            this.address = o.getMember().getAddress();
        }

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
    }


    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
