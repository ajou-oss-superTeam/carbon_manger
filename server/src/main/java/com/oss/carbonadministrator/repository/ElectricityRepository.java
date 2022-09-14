package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.domain.Electricity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricityRepository extends JpaRepository<Electricity, Long> {

    Optional<Electricity> findById(Long id);

}
