package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.domain.Bill;
import com.oss.carbonadministrator.domain.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BillRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Bill bill) {
        if (bill.getId() == null) {
            em.persist(bill);
        } else {
            em.merge(bill);
        }
    }

    public List<Bill> findByUserAndDate(Optional<User> user, int year, int month) {
        return em.createQuery(
                "select b from Bill b join b.user u where b.year = :year and b.month = :month and u.email = :email",
                Bill.class)
            .setParameter("year", year)
            .setParameter("month", month)
            .setParameter("email", user.get().getEmail())
            .getResultList();
    }
}
