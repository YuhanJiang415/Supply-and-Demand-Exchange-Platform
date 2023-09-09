package com.gzpclass.supdem.Repository;

import com.gzpclass.supdem.domain.merchantOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface merchantOrderRepository extends JpaRepository<merchantOrder,Integer> {
    public List<merchantOrder> findByBuyerId(Integer buyerId);
}
