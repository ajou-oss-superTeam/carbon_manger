package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.domain.water.WaterInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterRepository extends JpaRepository<WaterInfo, Long> {

    Optional<WaterInfo> findById(Long id);
}
