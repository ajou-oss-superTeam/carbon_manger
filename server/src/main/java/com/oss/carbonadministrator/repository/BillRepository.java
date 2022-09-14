package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.domain.Bill;
import java.util.List;

import com.oss.carbonadministrator.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query(value = "select b from Bill b join b.user u where b.year = ?2 and b.month = ?3 and u.email = ?1")
    List<Bill> findBillByEmailAndYearAndMonth(String email, int year, int month);

    List<Bill> findAllByUser(User user);
}
