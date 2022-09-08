package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.domain.Bill;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    Optional<Bill> findById(Long id);

}
