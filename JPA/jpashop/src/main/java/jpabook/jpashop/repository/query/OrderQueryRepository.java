package jpabook.jpashop.repository.query;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.nio.charset.IllegalCharsetNameException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos(){
        List<OrderQueryDto> result = findOrders();
        result.stream().forEach(
                o -> {
                    List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
                    o.setOrderItems(orderItems);
                });
        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.jpashop.repository.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();

    }



    public List<OrderQueryDto> findOrders(){
        return em.createQuery(
                "select new jpabook.jpashop.repository.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDto.class
        ).getResultList();
    }

    public List<OrderQueryDto> findAllByDto_Optimization() {
        List<OrderQueryDto> result = findOrders();
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());

        List<OrderItemQueryDto> orderItemQueryDtos = em.createQuery(
                        "select new jpabook.jpashop.repository.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)  //in 쿼리
                .setParameter("orderIds", orderIds)
                .getResultList();
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItemQueryDtos
                .stream().
                collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));    //orderid가 Long형으로 들어가 생성

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }
}
