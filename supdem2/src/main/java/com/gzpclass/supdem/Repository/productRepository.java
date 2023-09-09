package com.gzpclass.supdem.Repository;

import com.gzpclass.supdem.domain.product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface productRepository extends JpaRepository<product,Integer> {
    public List<product> findByType(String type);
    public List<product> findByAvailable(String available);
    public product findFirstByOrderByProductIdDesc();
}
