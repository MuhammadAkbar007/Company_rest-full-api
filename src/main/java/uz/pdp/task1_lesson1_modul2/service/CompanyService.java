package uz.pdp.task1_lesson1_modul2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task1_lesson1_modul2.entity.Address;
import uz.pdp.task1_lesson1_modul2.entity.Company;
import uz.pdp.task1_lesson1_modul2.payload.ApiResponse;
import uz.pdp.task1_lesson1_modul2.payload.CompanyDto;
import uz.pdp.task1_lesson1_modul2.repository.CompanyRepository;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    /**
     * method to add new company in controller.add(companyDto)
     *
     * @param companyDto is given (CompanyDto)
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> add(CompanyDto companyDto) {
        if (companyRepository.existsByCorpNameAndDirectorNameAndAddress_StreetAndAddress_HomeNumber(companyDto.getCorpName(), companyDto.getDirectorName(), companyDto.getStreet(), companyDto.getHomeNumber()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Such company already exists !", false));
        companyRepository.save(new Company(companyDto.getCorpName(), companyDto.getDirectorName(), new Address(companyDto.getStreet(), companyDto.getHomeNumber())));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Company successfully added !", true));
    }

    /**
     * method to get list of all companies(unsorted, unpaged) by service.getAll()
     *
     * @return responseEntity<List < Company>>
     */
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(companyRepository.findAll());
    }

    /**
     * method to get company by address by service.getByAddress
     *
     * @param street     string name of company
     * @param homeNumber integer home number of company
     * @return ResponseEntity<Company>
     */
    public ResponseEntity<?> getByAddress(String street, Integer homeNumber) {
        Optional<Company> optionalCompany = companyRepository.findByAddress_StreetAndAddress_HomeNumber(street, homeNumber);
        if (optionalCompany.isPresent())
            return ResponseEntity.ok(optionalCompany.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Such company not found !", false));
    }

    /**
     * method to update company by service.edit(id, companyDto)
     *
     * @param id         company id
     * @param companyDto String corpName, String directorName, String street, Integer homeNumber
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> edit(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Company with this id not found !", false));
        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(new Address(companyDto.getStreet(), companyDto.getHomeNumber()));
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Company successfully edited !", true));
    }

    /**
     * method to delete company with id by service.delete
     *
     * @param id company id
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> delete(Integer id) {
        try {
            companyRepository.deleteById(id);
            return ResponseEntity.status(200).body(new ApiResponse("Company successfully deleted !", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Error while deleting !", false));
        }
    }
}
