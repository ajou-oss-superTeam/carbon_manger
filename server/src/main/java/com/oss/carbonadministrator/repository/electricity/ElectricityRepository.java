package com.oss.carbonadministrator.repository.electricity;

import com.oss.carbonadministrator.domain.electricity.Electricity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricityRepository extends JpaRepository<Electricity, Long> {

    Optional<Electricity> findById(Long id);

}
