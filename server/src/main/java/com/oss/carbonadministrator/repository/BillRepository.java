package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    public Optional<Bill> findByPid(Long pid);

    public List<Bill> findByUid(Long uid);

    public List<Bill> findByUidAndDate(Long uid, Date date);
}
