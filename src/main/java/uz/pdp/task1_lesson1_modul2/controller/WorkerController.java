package uz.pdp.task1_lesson1_modul2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1_lesson1_modul2.payload.WorkerDto;
import uz.pdp.task1_lesson1_modul2.service.WorkerService;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;


    /**
     * method to create worker
     *
     * @param workerDto conversion class type from json
     * @return ResponseEntity<ApiResponse>
     */
    @PostMapping
    public ResponseEntity<?> add(@RequestBody WorkerDto workerDto) {
        return workerService.add(workerDto);
    }


    /**
     * method to get List of Workers (unsorted, unpaged)
     *
     * @return ResponseEntity<ApiResponse>
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        return workerService.getAll();
    }


    /**
     * method to get worker by phoneNumber
     *
     * @param phone number of worker
     * @return ResponseEntity<ApiResponse>
     */
    @GetMapping("/{phone}")
    public ResponseEntity<?> getByPhone(@PathVariable String phone) {
        return workerService.getByPhone(phone);
    }


    /**
     * method to update worker
     *
     * @param id        database id
     * @param workerDto conversion type from json
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody WorkerDto workerDto) {
        return workerService.edit(id, workerDto);
    }


    /**
     * method to delete worker
     *
     * @param id database id
     * @return ResponseEntity<ApiResponse>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return workerService.delete(id);
    }
}
