package org.example.securityorder.model.repository;

import org.example.securityorder.vo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    public List<Order> findByUserIdForOrders(String userId);
    public Order findByUserId(String userId);
}
