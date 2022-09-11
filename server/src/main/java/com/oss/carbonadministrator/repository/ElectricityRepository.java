package com.oss.carbonadministrator.repository;

import com.oss.carbonadministrator.domain.Electricity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ElectricityRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Electricity electricity) {
        if (electricity.getId() == null) {
            em.persist(electricity);
        } else {
            em.merge(electricity);
        }
    }

    public Electricity findById(Long id) {
        return em.find(Electricity.class, id);
    }
}
