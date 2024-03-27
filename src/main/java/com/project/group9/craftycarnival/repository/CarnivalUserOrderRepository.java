package com.project.group9.craftycarnival.repository;

import com.project.group9.craftycarnival.model.CarnivalUserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarnivalUserOrderRepository extends JpaRepository<CarnivalUserOrder, Long> {
    List<CarnivalUserOrder> findByOrderUserEmail(String email);

}
