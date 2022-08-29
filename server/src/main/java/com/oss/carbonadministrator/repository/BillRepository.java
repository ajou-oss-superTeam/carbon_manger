package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.entity.Bill;
import com.oss.carbonadministrator.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    Optional<Bill> findByPid(Long pid);

    List<Bill> findAllByUser(User user);

    List<Bill> findAllByUserAndDate(User user, Date date);
}
