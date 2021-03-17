package uz.pdp.task1_lesson1_modul2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1_lesson1_modul2.payload.CompanyDto;
import uz.pdp.task1_lesson1_modul2.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    /**
     * method to add new company by service.add(companyDto)
     *
     * @param companyDto is given (CompanyDto)
     * @return ResponseEntity<?>
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CompanyDto companyDto) {
        return companyService.add(companyDto);
    }

    /**
     * method to get list of all companies(unsorted, unpaged) by service.getAll()
     *
     * @return responseEntity<List < Company>>
     */
    @GetMapping
    ResponseEntity<?> getAll() {
        return companyService.getAll();
    }

    /**
     * method to get company by address by service.getByAddress
     *
     * @param street     string name of company
     * @param homeNumber integer home number of company
     * @return ResponseEntity<Company>
     */
    @GetMapping("/{street}/{homeNumber}")
    public ResponseEntity<?> getByAddress(@PathVariable String street, @PathVariable Integer homeNumber) {
        return companyService.getByAddress(street, homeNumber);
    }

    /**
     * method to update company by service.edit(id, companyDto)
     *
     * @param id         company id
     * @param companyDto String corpName, String directorName, String street, Integer homeNumber
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody CompanyDto companyDto) {
        return companyService.edit(id, companyDto);
    }

    /**
     * method to delete company with id by service.delete
     *
     * @param id company id
     * @return ResponseEntity<ApiResponse>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return companyService.delete(id);
    }

    /**
     * in order to get and handle bad requests
     *
     * @param ex exception in method
     * @return Map
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
