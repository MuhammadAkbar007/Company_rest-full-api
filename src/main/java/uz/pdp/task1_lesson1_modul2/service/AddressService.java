package uz.pdp.task1_lesson1_modul2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task1_lesson1_modul2.entity.Address;
import uz.pdp.task1_lesson1_modul2.payload.ApiResponse;
import uz.pdp.task1_lesson1_modul2.repository.AddressRepository;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    /**
     * method to add address by service.add
     *
     * @param address json input address
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> add(Address address) {
        if (addressRepository.existsByStreetAndHomeNumber(address.getStreet(), address.getHomeNumber()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Such address already exists !", false));
        addressRepository.save(new Address(address.getStreet(), address.getHomeNumber()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Address successfully saved !", true));
    }


    /**
     * method to get list of all addresses(unsorted, unpaged)
     *
     * @return ResponseEntity<List < Address>>
     */
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(addressRepository.findAll());
    }


    /**
     * method to get Address by String street & Integer homeNumber
     *
     * @param street     String address street
     * @param homeNumber Integer number of home
     * @return ResponseEntity<Address>
     */
    public ResponseEntity<?> getByPars(String street, Integer homeNumber) {
        Optional<Address> optionalAddress = addressRepository.findByStreetAndHomeNumber(street, homeNumber);
        if (!optionalAddress.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Such address not found !", false));
        return ResponseEntity.ok(optionalAddress.get());
    }


    /**
     * method to edit address
     *
     * @param id      database id of address
     * @param address covertion java class from json
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> edit(Integer id, Address address) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Address by this id not found !", false));
        Address edAddress = optionalAddress.get();
        edAddress.setStreet(address.getStreet());
        edAddress.setHomeNumber(address.getHomeNumber());
        addressRepository.save(edAddress);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Address successfuly updated !", true));
    }


    /**
     * method to delete address
     *
     * @param id database id of address
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> delete(Integer id) {
        try {
            addressRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Address successfully deleted !", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Error while deleting !", false));
        }
    }
}
