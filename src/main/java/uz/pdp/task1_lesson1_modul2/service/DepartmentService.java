package uz.pdp.task1_lesson1_modul2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task1_lesson1_modul2.entity.Company;
import uz.pdp.task1_lesson1_modul2.entity.Department;
import uz.pdp.task1_lesson1_modul2.payload.ApiResponse;
import uz.pdp.task1_lesson1_modul2.payload.DepartmentDto;
import uz.pdp.task1_lesson1_modul2.repository.CompanyRepository;
import uz.pdp.task1_lesson1_modul2.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;


    /**
     * method to create department
     *
     * @param departmentDto java class convertion type from json
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> add(DepartmentDto departmentDto) {
        if (departmentRepository.existsByName(departmentDto.getName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Such department already exists !", false));
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Company with this id not found !", false));
        departmentRepository.save(new Department(departmentDto.getName(), optionalCompany.get()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Department successfully created !", true));
    }


    /**
     * method to get List of departments
     *
     * @return ResponseEntity<List < Department>>
     */
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(departmentRepository.findAll());
    }


    /**
     * method to get departments in given street
     *
     * @param name in which departments situated
     * @return ResponseEntity<List < Department>>
     */
    public ResponseEntity<?> getByName(String name) {
        List<Department> allByName = departmentRepository.findAllByName(name);
        if (allByName.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Department with this name not found !", false));
        return ResponseEntity.ok(allByName);
    }


    /**
     * method to update department
     *
     * @param id            database id of department
     * @param departmentDto convertion class type from json
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> edit(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Department with this id not found !", false));
        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Company with this id not found !", false));
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Department successfully updated !", true));
    }


    /**
     * method to delete department
     *
     * @param id database id of department
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> delete(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Department successfully deleted !", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Error while deleting !", false));
        }
    }
}
