package com.project.group9.craftycarnival.repository;

import com.project.group9.craftycarnival.model.CarnivalProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarnivalProductsRepository extends JpaRepository<CarnivalProducts, Long> {

}
