package com.oss.carbonadministrator.repository.bill;

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.domain.user.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query(value = "select b from Bill b join b.user u where b.year = ?2 and b.month = ?3 and u.email = ?1")
    Optional<Bill> findBillByEmailAndYearAndMonth(String email, int year, int month);

    List<Bill> findAllByUser(User user);
}
