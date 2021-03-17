package uz.pdp.task1_lesson1_modul2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task1_lesson1_modul2.entity.Address;
import uz.pdp.task1_lesson1_modul2.entity.Department;
import uz.pdp.task1_lesson1_modul2.entity.Worker;
import uz.pdp.task1_lesson1_modul2.payload.ApiResponse;
import uz.pdp.task1_lesson1_modul2.payload.WorkerDto;
import uz.pdp.task1_lesson1_modul2.repository.AddressRepository;
import uz.pdp.task1_lesson1_modul2.repository.DepartmentRepository;
import uz.pdp.task1_lesson1_modul2.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    /**
     * method to create worker
     *
     * @param workerDto conversion class type from json
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> add(WorkerDto workerDto) {
        if (workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Such worker already exists !", false));
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Such worker address not found !", false));
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Such worker department not found !", false));
        workerRepository.save(new Worker(workerDto.getName(), workerDto.getPhoneNumber(), optionalAddress.get(), optionalDepartment.get()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Worker successfully created !", true));
    }


    /**
     * method to get List of Workers (unsorted, unpaged)
     *
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> getAll() {
        List<Worker> all = workerRepository.findAll();
        if (all.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Workers not found !", false));
        return ResponseEntity.ok(all);
    }


    /**
     * method to get worker by phoneNumber
     *
     * @param phone number of worker
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> getByPhone(String phone) {
        Optional<Worker> optionalWorker = workerRepository.findByPhoneNumber(phone);
        if (!optionalWorker.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Worker with this phoneNumber not found !", false));
        return ResponseEntity.ok(optionalWorker.get());
    }


    /**
     * method to update
     *
     * @param id        database id
     * @param workerDto conversion type from json
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> edit(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Such worker id not found !", false));
        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Such worker address not found !", false));
        worker.setAddress(optionalAddress.get());
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Such department not found !", false));
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Worker successfully edited !", true));
    }


    /**
     * method to delete worker
     *
     * @param id database id
     * @return ResponseEntity<ApiResponse>
     */
    public ResponseEntity<?> delete(Integer id) {
        try {
            workerRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Worker successfully deleted !", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Worker id not found !", false));
        }
    }
}
