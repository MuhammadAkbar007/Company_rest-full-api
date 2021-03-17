package uz.pdp.task1_lesson1_modul2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1_lesson1_modul2.entity.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    boolean existsByStreetAndHomeNumber(String street, Integer homeNumber);

    Optional<Address> findByStreetAndHomeNumber(String street, Integer homeNumber);
}
