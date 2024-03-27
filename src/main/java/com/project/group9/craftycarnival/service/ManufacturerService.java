package com.project.group9.craftycarnival.service;

import com.project.group9.craftycarnival.model.CarnivalManufacturer;
import com.project.group9.craftycarnival.repository.CarnivalManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {

    @Autowired
    private CarnivalManufacturerRepository carnivalManufacturerRepository;

    public ResponseEntity<?> addManufacturer(CarnivalManufacturer carnivalManufacturer){
        try{
            carnivalManufacturerRepository.save(carnivalManufacturer);
            return new ResponseEntity<>("Manufacturer added Successfully.", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to add Manufacturer",HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> updateManufacturer(CarnivalManufacturer carnivalManufacturer){
        try{
            Optional<CarnivalManufacturer> optionalManufacturer = carnivalManufacturerRepository.findById(carnivalManufacturer.getId());
            if(optionalManufacturer.isPresent()){
                CarnivalManufacturer m = optionalManufacturer.get();
                m.setManufacturerName(carnivalManufacturer.getManufacturerName());
                m.setManufacturerAddress(carnivalManufacturer.getManufacturerAddress());
                m.setManufacturerPhone(carnivalManufacturer.getManufacturerPhone()); // Update phone
                m.setManufacturerEmail(carnivalManufacturer.getManufacturerEmail()); // Update email
                m.setManufacturerWebsite(carnivalManufacturer.getManufacturerWebsite()); // Update website
                carnivalManufacturerRepository.save(m);
                return new ResponseEntity<>("Manufacturer updated Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Manufacturer Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Failed to update Manufacturer", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> deleteManufacturer(long manufacturerId){
        try{
            Optional<CarnivalManufacturer> optionalManufacturer = carnivalManufacturerRepository.findById(manufacturerId);
            if(optionalManufacturer.isPresent()){
                carnivalManufacturerRepository.delete(optionalManufacturer.get());
                return new ResponseEntity<>("Manufacturer Deleted Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Manufacturer Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Failed to delete Manufacturer", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<List<CarnivalManufacturer>> getAllManufacturers(){
        try{
            List<CarnivalManufacturer> carnivalManufacturers = carnivalManufacturerRepository.findAll();
            return new ResponseEntity<>(carnivalManufacturers, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
