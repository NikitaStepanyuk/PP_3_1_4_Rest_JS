package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepository{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String roleName) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.roleName = :roleName", Role.class)
                .setParameter("roleName", roleName).getSingleResult();
    }

}
