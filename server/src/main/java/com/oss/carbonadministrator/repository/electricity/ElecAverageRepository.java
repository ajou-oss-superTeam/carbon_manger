package com.oss.carbonadministrator.repository.electricity;

import com.oss.carbonadministrator.domain.electricity.ElecAverage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElecAverageRepository extends JpaRepository<ElecAverage, Long> {

    Optional<ElecAverage> findById(Long id);

    List<ElecAverage> findAllByCityAndProvince(String city, String province);

}
