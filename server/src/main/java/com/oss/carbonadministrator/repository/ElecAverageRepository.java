package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.domain.ElecAverage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElecAverageRepository extends JpaRepository<ElecAverage, Long> {

    Optional<ElecAverage> findById(Long id);

}
