package com.example.Repository;

import com.example.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {


    @Query("select o from Order o where o.user.id=:userId")
    public List<Order> getUserOrder(@Param("userId")Long userId);
}
