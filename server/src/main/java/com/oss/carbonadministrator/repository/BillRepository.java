package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.domain.Bill;
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

    public Bill findById(Long id) {
        return em.find(Bill.class, id);
    }
}
