package uz.pdp.task1_lesson1_modul2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1_lesson1_modul2.entity.Address;
import uz.pdp.task1_lesson1_modul2.service.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    /**
     * method to add address by service.add
     *
     * @param address json input address
     * @return ResponseEntity<ApiResponse>
     */
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Address address) {
        return addressService.add(address);
    }


    /**
     * method to get list of all addresses(unsorted, unpaged)
     *
     * @return ResponseEntity<List < Address>>
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        return addressService.getAll();
    }


    /**
     * method to get Address by String street & Integer homeNumber
     *
     * @param street     String address street
     * @param homeNumber Integer number of home
     * @return ResponseEntity<Address>
     */
    @GetMapping("/{street}/{homeNumber}")
    public ResponseEntity<?> getByPars(@PathVariable String street, @PathVariable Integer homeNumber) {
        return addressService.getByPars(street, homeNumber);
    }


    /**
     * method to edit address
     *
     * @param id      database id of address
     * @param address covertion java class from json
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody Address address) {
        return addressService.edit(id, address);
    }


    /**
     * method to delete address
     *
     * @param id database id of address
     * @return ResponseEntity<ApiResponse>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return addressService.delete(id);
    }
}
