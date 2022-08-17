package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.entity.Bill;
import com.oss.carbonadministrator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    public Optional<Bill> findByPid(Long pid);

    public List<Bill> findAllByUser(User user);

    public List<Bill> findAllByUserAndDate(User user, Date date);
}
