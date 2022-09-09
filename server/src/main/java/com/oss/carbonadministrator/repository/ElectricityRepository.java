package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.domain.Electricity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElectricityRepository extends JpaRepository<Electricity, Long> {
    Optional<Electricity> findById(Long id);
}
