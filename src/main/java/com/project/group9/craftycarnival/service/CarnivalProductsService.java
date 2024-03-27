package com.project.group9.craftycarnival.service;

import com.project.group9.craftycarnival.model.CarnivalProducts;
import com.project.group9.craftycarnival.repository.CarnivalProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarnivalProductsService {

    @Autowired
    private CarnivalProductsRepository carnivalProductsRepository;

    public CarnivalProductsRepository getProductRepository() {
        return carnivalProductsRepository;
    }

    public void setProductRepository(CarnivalProductsRepository carnivalProductsRepository) {
        this.carnivalProductsRepository = carnivalProductsRepository;
    }

    public CarnivalProductsService(CarnivalProductsRepository carnivalProductsRepository) {
        this.carnivalProductsRepository = carnivalProductsRepository;
    }

    public ResponseEntity<?> addProduct(CarnivalProducts carnivalProducts){
        System.out.println("Product details before setting: " + carnivalProducts.toString());
        try{
            CarnivalProducts p = new CarnivalProducts();
            p.setProductName(carnivalProducts.getProductName());
            p.setProductDescription(carnivalProducts.getProductDescription());
            p.setProductCategory(carnivalProducts.getProductCategory());
            p.setProductPrice(carnivalProducts.getProductPrice());
            p.setProductModel(carnivalProducts.getProductModel());
            p.setProductManufacturer(carnivalProducts.getProductManufacturer());
            p.setProductQuantityAvailable(carnivalProducts.getProductQuantityAvailable());
            p.setImageUrls(carnivalProducts.getImageUrls());
            carnivalProductsRepository.save(p);
            return new ResponseEntity<>("Product Added Successfully.", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Failed to add Product", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> updateProduct(CarnivalProducts carnivalProducts){
        try{
            Optional<CarnivalProducts> optionalProduct = carnivalProductsRepository.findById(carnivalProducts.getId());
            if(optionalProduct.isPresent()){
                CarnivalProducts p = optionalProduct.get();
                if(carnivalProducts.getProductName() != null && !carnivalProducts.getProductName().isEmpty()) {
                    p.setProductName(carnivalProducts.getProductName());
                }
                if(carnivalProducts.getProductDescription() != null) {
                    p.setProductDescription(carnivalProducts.getProductDescription());
                }
                if(carnivalProducts.getProductPrice() != null) {
                    p.setProductPrice(carnivalProducts.getProductPrice());
                }
                p.setProductQuantityAvailable(carnivalProducts.getProductQuantityAvailable());
                if(carnivalProducts.getProductManufacturer() != null && !carnivalProducts.getProductManufacturer().isEmpty()) {
                    p.setProductManufacturer(carnivalProducts.getProductManufacturer());
                }
                if(carnivalProducts.getProductCategory() != null && !carnivalProducts.getProductCategory().isEmpty()) {
                    p.setProductCategory(carnivalProducts.getProductCategory());
                }
                if(carnivalProducts.getProductModel() != null && !carnivalProducts.getProductModel().isEmpty()) {
                    p.setProductModel(carnivalProducts.getProductModel());
                }
                if(carnivalProducts.getImageUrls() != null) {
                    p.setImageUrls(carnivalProducts.getImageUrls());
                }
                carnivalProductsRepository.save(p);
                return new ResponseEntity<>("Product Updated Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Failed to update Product", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> deleteProduct(long productId){
        try{
            Optional<CarnivalProducts> optionalProduct = carnivalProductsRepository.findById(productId);
            if(optionalProduct.isPresent()){
                carnivalProductsRepository.delete(optionalProduct.get());
                return new ResponseEntity<>("Product Deleted Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Failed to delete Product", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<List<CarnivalProducts>> getAllProducts(){
        try{
            List<CarnivalProducts> carnivalProducts = carnivalProductsRepository.findAll();
            return new ResponseEntity<>(carnivalProducts,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}
