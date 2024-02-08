package com.springprofessional.dscommerce.repositories;

import com.springprofessional.dscommerce.entities.OrderItem;
import com.springprofessional.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
