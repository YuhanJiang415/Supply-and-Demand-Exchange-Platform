package com.gzpclass.supdem.Repository;

import com.gzpclass.supdem.domain.checklist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface checklistRepository extends JpaRepository<checklist,Integer> {
}
