package com.project.group9.craftycarnival.repository;


import com.project.group9.craftycarnival.model.CarnivalManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarnivalManufacturerRepository extends JpaRepository<CarnivalManufacturer, Long> {
}
