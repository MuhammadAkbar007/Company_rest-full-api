package uz.pdp.task1_lesson1_modul2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1_lesson1_modul2.entity.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    boolean existsByName(String name);

    List<Department> findAllByName(String name);
}
