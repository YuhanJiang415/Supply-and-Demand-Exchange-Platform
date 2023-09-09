package com.gzpclass.supdem.Repository;

import com.gzpclass.supdem.domain.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface userRepository extends JpaRepository<user,Integer>{
    @Query(value = "select * from user as u where u.username=?1 and u.password=?2",nativeQuery = true)
    List<user> findByuserNameAndPass(String name, String pass);
}
