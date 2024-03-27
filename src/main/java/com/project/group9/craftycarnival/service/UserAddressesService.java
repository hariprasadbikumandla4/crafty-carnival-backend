package com.project.group9.craftycarnival.service;

import com.project.group9.craftycarnival.repository.UserAddressesRepository;
import com.project.group9.craftycarnival.model.UserAddresses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAddressesService {

    @Autowired
    private UserAddressesRepository userAddressesRepository;

    public ResponseEntity<?> addUserAddress(UserAddresses userAddresses) {
        try {
            UserAddresses ua = new UserAddresses();
            ua.setEmail(userAddresses.getEmail());
            ua.setName(userAddresses.getName());
            ua.setAddress1(userAddresses.getAddress1());
            ua.setAddress2(userAddresses.getAddress2());
            ua.setCity(userAddresses.getCity());
            ua.setState(userAddresses.getState());
            ua.setPhone(userAddresses.getPhone());
            ua.setZip(userAddresses.getZip());

            userAddressesRepository.save(ua);
            return new ResponseEntity<>("Address Added Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Add Address", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> updateAddress(UserAddresses userAddresses) {
        try {
            Optional<UserAddresses> optionalUserAddresses = userAddressesRepository.findById(userAddresses.getId());
            if (optionalUserAddresses.isPresent()) {
                UserAddresses ua = optionalUserAddresses.get();
                ua.setEmail(userAddresses.getEmail());
                ua.setName(userAddresses.getName());
                ua.setAddress1(userAddresses.getAddress1());
                ua.setAddress2(userAddresses.getAddress2());
                ua.setCity(userAddresses.getCity());
                ua.setState(userAddresses.getState());
                ua.setPhone(userAddresses.getPhone());
                ua.setZip(userAddresses.getZip());

                userAddressesRepository.save(ua);
                return new ResponseEntity<>("Address Updated Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Update Address", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<List<UserAddresses>> getAllAddresses() {
        try {
            List<UserAddresses> allAddresses = userAddressesRepository.findAll();
            return new ResponseEntity<>(allAddresses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<UserAddresses>> getAllAddressesByEmail(String email) {
        try {
            List<UserAddresses> allAddresses = userAddressesRepository.findAll();
            List<UserAddresses> addressesByEmail = allAddresses.stream()
                    .filter(address -> address.getEmail().equals(email))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(addressesByEmail, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("From Catch"+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteUserAddress(Long id){
        try {
            userAddressesRepository.deleteById(id);
            return new ResponseEntity<>("Address deleted Successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to Delete Address", HttpStatus.CONFLICT);
        }
    }
}
