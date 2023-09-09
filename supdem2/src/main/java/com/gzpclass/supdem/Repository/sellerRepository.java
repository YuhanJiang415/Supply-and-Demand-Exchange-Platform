package com.gzpclass.supdem.Repository;

import com.gzpclass.supdem.domain.seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface sellerRepository extends JpaRepository<seller,Integer> {
    @Query(value = "select * from seller as u where u.username=?1 and u.password=?2",nativeQuery = true)
    List<seller> findBysellerNameAndPass(String name, String pass);
}
