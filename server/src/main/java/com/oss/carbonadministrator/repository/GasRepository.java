package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.domain.gas.GasInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasRepository extends JpaRepository<GasInfo, Long> {

    Optional<GasInfo> findById(Long id);

}
