package com.project.group9.craftycarnival.service;

import com.project.group9.craftycarnival.repository.ManufacturerCategoryRepository;
import com.project.group9.craftycarnival.model.ManufacturerCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerCategoryService {

    @Autowired
    private ManufacturerCategoryRepository manufacturerCategoryRepository;


    public ResponseEntity<?> addCategory(ManufacturerCategory manufacturerCategory){
        try{
            ManufacturerCategory c=new ManufacturerCategory();
            c.setCategoryName(manufacturerCategory.getCategoryName());
            manufacturerCategoryRepository.save(c);
            return new ResponseEntity<>("Category Added Successful.", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to add Category.", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> updateCategory(ManufacturerCategory updatedManufacturerCategory){
        try{
            Optional<ManufacturerCategory> optionalCategory = manufacturerCategoryRepository.findById(updatedManufacturerCategory.getId());
            if (optionalCategory.isPresent()) {
                ManufacturerCategory existingManufacturerCategory = optionalCategory.get();
                existingManufacturerCategory.setCategoryName(updatedManufacturerCategory.getCategoryName());
                manufacturerCategoryRepository.save(existingManufacturerCategory);
                return new ResponseEntity<>("Category Updated Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Category not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Failed to update Category.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> deleteCategory(Long id) {
        try {
            Optional<ManufacturerCategory> optionalCategory = manufacturerCategoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                manufacturerCategoryRepository.delete(optionalCategory.get());
                return new ResponseEntity<>("Category Deleted Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Category not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete Category.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllCategories() {
        try {
            List<ManufacturerCategory> categories = manufacturerCategoryRepository.findAll();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve Categories.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
