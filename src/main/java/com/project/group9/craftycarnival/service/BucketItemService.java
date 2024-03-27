package com.project.group9.craftycarnival.service;

import com.project.group9.craftycarnival.model.BucketItem;
import com.project.group9.craftycarnival.model.BucketItemType;
import com.project.group9.craftycarnival.model.CarnivalProducts;
import com.project.group9.craftycarnival.repository.BucketItemRepository;
import com.project.group9.craftycarnival.repository.CarnivalProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BucketItemService {

    @Autowired
    private BucketItemRepository bucketItemRepository;

    @Autowired
    private CarnivalProductsRepository carnivalProductsRepository;

    public ResponseEntity<?> addItemtoCart(Long prId, String userEmail, Integer quantity) {
        try {
            if (quantity == 0) {
                Optional<CarnivalProducts> optionalProduct = carnivalProductsRepository.findById(prId);
                if (optionalProduct.isPresent()) {
                    Optional<BucketItem> optionalCartItem = bucketItemRepository.findByCarnivalProductsAndCartItemUserEmailAndBucketItemType(optionalProduct.get(), userEmail, BucketItemType.INCART);
                    optionalCartItem.ifPresent(bucketItemRepository::delete);
                    return new ResponseEntity<>("Item removed from cart successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
                }
            } else {
                Optional<CarnivalProducts> optionalProduct = carnivalProductsRepository.findById(prId);
                if (optionalProduct.isPresent()) {
                    Optional<BucketItem> optionalCartItem = bucketItemRepository.findByCarnivalProductsAndCartItemUserEmailAndBucketItemType(optionalProduct.get(), userEmail, BucketItemType.INCART);
                    if (optionalCartItem.isPresent()) {
                        BucketItem bucketItem = optionalCartItem.get();
                        bucketItem.setCartItemQuantity(quantity);
                        bucketItemRepository.save(bucketItem);
                    } else {
                        BucketItem newBucketItem = new BucketItem();
                        newBucketItem.setCarnivalProducts(optionalProduct.get());
                        newBucketItem.setCartItemQuantity(quantity);
                        newBucketItem.setCartItemUserEmail(userEmail);
                        newBucketItem.setBucketItemType(BucketItemType.INCART);
                        bucketItemRepository.save(newBucketItem);
                    }
                    return new ResponseEntity<>("Item added to cart successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add/remove item to/from cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<BucketItem>> getUserCartItems(String userEmail){
        try{
            List<BucketItem> userBucketItems = bucketItemRepository.findAll().stream()
                    .filter(bucketItem -> bucketItem.getCartItemUserEmail().equals(userEmail) && bucketItem.getBucketItemType() == BucketItemType.INCART)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(userBucketItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<BucketItem>> getAllCartItems(){
        try{
            List<BucketItem> allBucketItems = bucketItemRepository.findAll();
            return new ResponseEntity<>(allBucketItems, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> changeItemsCarttoOrder(Long[] cartItems) {
        try {
            for (Long cartItemId : cartItems) {
                Optional<BucketItem> optionalCartItem = bucketItemRepository.findById(cartItemId);
                if (optionalCartItem.isPresent()) {
                    BucketItem bucketItem = optionalCartItem.get();
                    bucketItem.setBucketItemType(BucketItemType.ORDERED);
                    bucketItemRepository.save(bucketItem);

                    CarnivalProducts carnivalProducts = bucketItem.getCarnivalProducts();
                    int currentQuantity = carnivalProducts.getProductQuantityAvailable();
                    int cartItemQuantity = bucketItem.getCartItemQuantity();
                    if (currentQuantity >= cartItemQuantity) {
                        carnivalProducts.setProductQuantityAvailable(currentQuantity - cartItemQuantity);
                        carnivalProductsRepository.save(carnivalProducts);
                    } else {
                        return new ResponseEntity<>("Insufficient quantity for product: " + carnivalProducts.getProductName(), HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("Cart item not found with ID: " + cartItemId, HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>("Cart items changed to ORDERED successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change cart items to ORDERED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<BucketItem>> getOrderCartItems(Long[] cartItemIds){
        try {
            List<BucketItem> orderBucketItems = bucketItemRepository.findAllById(Arrays.asList(cartItemIds));
            return new ResponseEntity<>(orderBucketItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
