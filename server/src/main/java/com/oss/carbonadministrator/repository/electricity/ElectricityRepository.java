package com.oss.carbonadministrator.repository.electricity;

import com.oss.carbonadministrator.domain.electricity.ElectricityInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricityRepository extends JpaRepository<ElectricityInfo, Long> {

    Optional<ElectricityInfo> findById(Long id);

}
