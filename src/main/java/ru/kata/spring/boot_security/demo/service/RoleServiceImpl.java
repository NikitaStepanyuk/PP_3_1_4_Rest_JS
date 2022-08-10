package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.getRoleByName(roleName);
    }

    @Override
    public Set<Role> getPersistRolesByRoleSet(Set<Role> roles) {
        Set<Role> roleSet = new HashSet<>();
        for (Role role : roles){
            roleSet.add(roleRepository.getRoleByName(role.getRoleName()));
        }
        return roleSet;
    }

}