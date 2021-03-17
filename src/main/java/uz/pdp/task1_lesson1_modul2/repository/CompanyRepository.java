package uz.pdp.task1_lesson1_modul2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1_lesson1_modul2.entity.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByCorpNameAndDirectorNameAndAddress_StreetAndAddress_HomeNumber(String corpName, String directorName, String address_street, Integer address_homeNumber);

    Optional<Company> findByAddress_StreetAndAddress_HomeNumber(String address_street, Integer address_homeNumber);
}
