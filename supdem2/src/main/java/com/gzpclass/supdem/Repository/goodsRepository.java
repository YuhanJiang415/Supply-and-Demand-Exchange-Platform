package com.gzpclass.supdem.Repository;

import com.gzpclass.supdem.domain.goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface goodsRepository extends JpaRepository<goods,Integer>{
//    public List<goods> findBySellerId(Integer sellerId);
//    public List<goods> findByStatus(String Status);
    public List<goods> findBySellerId(Integer sellerId);
    public List<goods> findByStatus(String Status);
    public goods findFirstByOrderByGoodsIdDesc();
}
