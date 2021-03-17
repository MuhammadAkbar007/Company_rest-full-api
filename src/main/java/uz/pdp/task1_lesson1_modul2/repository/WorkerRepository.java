package uz.pdp.task1_lesson1_modul2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1_lesson1_modul2.entity.Worker;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Worker> findByPhoneNumber(String phoneNumber);
}
