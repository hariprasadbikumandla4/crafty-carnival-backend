package com.project.group9.craftycarnival.repository;

import com.project.group9.craftycarnival.model.BucketItem;
import com.project.group9.craftycarnival.model.BucketItemType;
import com.project.group9.craftycarnival.model.CarnivalProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BucketItemRepository extends JpaRepository<BucketItem, Long> {

    Optional<BucketItem> findByCarnivalProductsAndCartItemUserEmailAndBucketItemType(CarnivalProducts carnivalProducts, String userEmail, BucketItemType bucketItemType);

}
