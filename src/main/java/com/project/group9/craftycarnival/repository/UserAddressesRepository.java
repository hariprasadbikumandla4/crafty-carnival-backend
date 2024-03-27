package com.project.group9.craftycarnival.repository;

import com.project.group9.craftycarnival.model.UserAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressesRepository extends JpaRepository<UserAddresses, Long> {
}
