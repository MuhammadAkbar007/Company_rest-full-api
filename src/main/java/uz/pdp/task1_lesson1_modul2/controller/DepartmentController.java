package uz.pdp.task1_lesson1_modul2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1_lesson1_modul2.payload.DepartmentDto;
import uz.pdp.task1_lesson1_modul2.service.DepartmentService;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


    /**
     * method to create department
     *
     * @param departmentDto java class conversion type from json
     * @return ResponseEntity<ApiResponse>
     */
    @PostMapping
    public ResponseEntity<?> add(@RequestBody DepartmentDto departmentDto) {
        return departmentService.add(departmentDto);
    }


    /**
     * method to get List of departments
     *
     * @return ResponseEntity<List < Department>>
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        return departmentService.getAll();
    }


    /**
     * method to get departments in given street
     *
     * @param name in which departments situated
     * @return ResponseEntity<List < Department>>
     */
    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        return departmentService.getByName(name);
    }


    /**
     * method to update department
     *
     * @param id            database id of department
     * @param departmentDto conversion class type from json
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody DepartmentDto departmentDto) {
        return departmentService.edit(id, departmentDto);
    }


    /**
     * method to delete department
     *
     * @param id database id of department
     * @return ResponseEntity<ApiResponse>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return departmentService.delete(id);
    }
}
