package com.project.group9.craftycarnival.service;

import com.project.group9.craftycarnival.model.CarnivalUserOrder;
import com.project.group9.craftycarnival.model.CarnivalUserOrderStatus;
import com.project.group9.craftycarnival.repository.BucketItemRepository;
import com.project.group9.craftycarnival.repository.CarnivalUserOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class CarnivalUserOrderService {
    @Autowired
    private CarnivalUserOrderRepository carnivalUserOrderRepository;
    @Autowired
    private BucketItemRepository bucketItemRepository;
    @Autowired
    private BucketItemService bucketItemService;
    public ResponseEntity<?> addNewOrder(Long[] cartItems, String paymentId,
                                         String orderAddress, String orderEmail, BigDecimal orderPrice){
        try{
            // Change Cart Item Status to ORDERED
            ResponseEntity<?> response = bucketItemService.changeItemsCarttoOrder(cartItems);
            if (response.getStatusCode() != HttpStatus.OK) {
                // If changing cart items to ORDERED fails, return the response
                return response;
            }

            CarnivalUserOrder carnivalUserOrder =new CarnivalUserOrder();
            carnivalUserOrder.setOrderAddress(orderAddress);
            carnivalUserOrder.setOrderTotalPrice(orderPrice);
            carnivalUserOrder.setStatus(CarnivalUserOrderStatus.PLACED);
            carnivalUserOrder.setOrderUserEmail(orderEmail);
            carnivalUserOrder.setOrderPaymentId(paymentId);
            carnivalUserOrder.setOrderedOn(LocalDateTime.now());
            carnivalUserOrder.setItems(cartItems);
            carnivalUserOrderRepository.save(carnivalUserOrder);

            return new ResponseEntity<>("Order Placed Successfully.", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Failed to Place Order.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> changeOrderStatus(Long orderId, String newStatus){
        try {
            Optional<CarnivalUserOrder> optionalOrder = carnivalUserOrderRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                CarnivalUserOrder carnivalUserOrder = optionalOrder.get();
                switch (newStatus){
                    case "PLACED":
                        carnivalUserOrder.setStatus(CarnivalUserOrderStatus.PLACED);
                        break;
                    case "INTRANSIT":
                        carnivalUserOrder.setStatus(CarnivalUserOrderStatus.INTRANSIT);
                        break;
                    case "DELIVERED":
                        carnivalUserOrder.setStatus(CarnivalUserOrderStatus.DELIVERED);
                        break;
                    case "CANCELLED":
                        carnivalUserOrder.setStatus(CarnivalUserOrderStatus.CANCELLED);
                        break;
                    default:
                        return new ResponseEntity<>("Invalid order status provided.", HttpStatus.BAD_REQUEST);
                }
                carnivalUserOrderRepository.save(carnivalUserOrder);
                return new ResponseEntity<>("Order status updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Order not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update order status.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> addDeliveryDetails(Long orderId, String deliveryPartner, String trackingNumber){
        try{
            Optional<CarnivalUserOrder> optionalOrder = carnivalUserOrderRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                CarnivalUserOrder carnivalUserOrder = optionalOrder.get();
                carnivalUserOrder.setOrderDeliveryPartner(deliveryPartner);
                carnivalUserOrder.setDeliveryTrackingNumber(trackingNumber);
                carnivalUserOrder.setStatus(CarnivalUserOrderStatus.INTRANSIT);
                carnivalUserOrderRepository.save(carnivalUserOrder);
                return new ResponseEntity<>("Delivery details added updated.", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Order not found.", HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception e){
            return new ResponseEntity<>("Failed to add delivery details.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<CarnivalUserOrder>> getAllOrders(){
        try {
            List<CarnivalUserOrder> allCarnivalUserOrders = carnivalUserOrderRepository.findAll();
            return new ResponseEntity<>(allCarnivalUserOrders,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<List<CarnivalUserOrder>> getUserOrders(String email){
        try {
            List<CarnivalUserOrder> userCarnivalUserOrders = carnivalUserOrderRepository.findByOrderUserEmail(email);
            return new ResponseEntity<>(userCarnivalUserOrders, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }
}
