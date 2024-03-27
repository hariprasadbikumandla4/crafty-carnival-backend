package com.project.group9.craftycarnival.controller;

import com.project.group9.craftycarnival.model.*;
import com.project.group9.craftycarnival.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
public class CraftyCarnivalController {

    @Autowired
    private ManufacturerCategoryService manufacturerCategoryService;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private UserAddressesService userAddressesService;
    @Autowired
    private CarnivalProductsService carnivalProductsService;
    @Autowired
    private BucketItemService bucketItemService;
    @Autowired
    private CarnivalUserOrderService carnivalUserOrderService;

    @PostMapping("/addcategory")
    public ResponseEntity<?> carnivalAddCategory(@RequestBody ManufacturerCategory manufacturerCategory){
        return manufacturerCategoryService.addCategory(manufacturerCategory);
    }
    @PutMapping("/updatecategory")
    public ResponseEntity<?> carnivalUpdateCategory(@RequestBody ManufacturerCategory manufacturerCategory){
        return manufacturerCategoryService.updateCategory(manufacturerCategory);
    }
    @DeleteMapping("/deletecategory/{id}")
    public ResponseEntity<?> carnivalDeleteCategory(@PathVariable Long id) {
        return manufacturerCategoryService.deleteCategory(id);
    }
    @GetMapping("/allcategories")
    public ResponseEntity<?> carnivalFetchAllCategories() {
        return manufacturerCategoryService.getAllCategories();
    }

    @PostMapping("/addmanufacturer")
    public ResponseEntity<?> carnivalAddManufacturer(@RequestBody CarnivalManufacturer carnivalManufacturer){
        return manufacturerService.addManufacturer(carnivalManufacturer);
    }
    @PutMapping("/updatemanufacturer")
    public ResponseEntity<?> carnivalUpdateManufacturer(@RequestBody CarnivalManufacturer carnivalManufacturer) {
        return manufacturerService.updateManufacturer(carnivalManufacturer);
    }
    @DeleteMapping("/deletemanufacturer/{id}")
    public ResponseEntity<?> carnivalDeleteManufacturer(@PathVariable("id") long manufacturerId) {
        return manufacturerService.deleteManufacturer(manufacturerId);
    }
    @GetMapping("/allmanufacturers")
    public ResponseEntity<List<CarnivalManufacturer>> carnivalGetAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }

    @PostMapping("/addaddress")
    public ResponseEntity<?> carnivalAddUserAddress(@RequestBody UserAddresses userAddresses){
        return userAddressesService.addUserAddress(userAddresses);
    }
    @PutMapping("/updateaddress")
    public ResponseEntity<?> carnivalUpdateUserAddress(@RequestBody UserAddresses userAddresses){
        return userAddressesService.updateAddress(userAddresses);
    }
    @GetMapping("/alladdresses")
    public ResponseEntity<List<UserAddresses>> carnivalFetchAllAddresses(){
        return userAddressesService.getAllAddresses();
    }
    @GetMapping("/alluseraddresses")
    public ResponseEntity<List<UserAddresses>> carnivalFetchUserAddressesByEmail(
            @RequestParam(name = "email", required = false) String email){
        return userAddressesService.getAllAddressesByEmail(email);
    }
    @DeleteMapping("/deleteaddress/{id}")
    public ResponseEntity<?> carnivalDeleteAddress(@PathVariable("id") long addressId){
        return userAddressesService.deleteUserAddress(addressId);
    }

    @PostMapping("/addproduct")
    public ResponseEntity<?> carnivalAddProduct(@RequestBody CarnivalProducts carnivalProducts){
        return carnivalProductsService.addProduct(carnivalProducts);
    }

    @PutMapping("/updateproduct")
    public ResponseEntity<?> carnivalUpdateProduct(@RequestBody CarnivalProducts carnivalProducts) {
        return carnivalProductsService.updateProduct(carnivalProducts);
    }

    @DeleteMapping("/deleteproduct/{id}")
    public ResponseEntity<?> carnivalDeleteProduct(@PathVariable("id") long productId) {
        return carnivalProductsService.deleteProduct(productId);
    }

    @GetMapping("/allproducts")
    public ResponseEntity<List<CarnivalProducts>> carnivalFetchAllProducts(){
        return carnivalProductsService.getAllProducts();
    }

    @GetMapping("/additemtocart")
    public ResponseEntity<?> carnivalAddItemToCart(
            @RequestParam(name = "id", required = false) Long productId,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "quantity", required = false) Integer quantity){
        return bucketItemService.addItemtoCart(productId, email, quantity);
    }

    @GetMapping("/getallcartitems")
    public ResponseEntity<List<BucketItem>> carnivalFetchAllCartItems(){
        return bucketItemService.getAllCartItems();
    }

    @GetMapping("/getusercartitems")
    public ResponseEntity<List<BucketItem>> carnivalFetchUserCartItems(
            @RequestParam(name = "email", required = true) String email){
        return bucketItemService.getUserCartItems(email);
    }

    @GetMapping("/placeorder")
    public ResponseEntity<?> carnivalPlaceOrder(
            @RequestParam(name = "cartItems", required = false) Long[] cartItems,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "paymentId", required = false) String paymentId,
            @RequestParam(name = "orderPrice", required = false) BigDecimal orderPrice,
            @RequestParam(name = "orderAddress", required = false) String orderAddress){
        return carnivalUserOrderService.addNewOrder(cartItems, paymentId, orderAddress, email, orderPrice);
    }

    @GetMapping("/updateorderstatus")
    public ResponseEntity<?> carnivalUpdateOrderStatus(
            @RequestParam(name = "orderId", required = true) long orderId,
            @RequestParam(name = "status", required = true) String newStatus){
        return carnivalUserOrderService.changeOrderStatus(orderId,newStatus);
    }

    @GetMapping("/getallorders")
    public ResponseEntity<List<CarnivalUserOrder>> carnivalFetchAllOrders(){
        return carnivalUserOrderService.getAllOrders();
    }

    @GetMapping("/getuserorders")
    public ResponseEntity<List<CarnivalUserOrder>> carnivalFetchUserOrders(
            @RequestParam(name = "userEmail", required = true) String email) {
        return carnivalUserOrderService.getUserOrders(email);
    }

    @GetMapping("/getordercartitems")
    public ResponseEntity<List<BucketItem>> getOrderCartItems(
            @RequestParam(name = "cartItemsIds", required = true) Long[] cartItemsIds){
        return bucketItemService.getOrderCartItems(cartItemsIds);
    }

}
