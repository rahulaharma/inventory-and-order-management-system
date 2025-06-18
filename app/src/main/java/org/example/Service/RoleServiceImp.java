package org.example.Service;

import org.example.model.Role;
import org.example.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public Role createRole(Role role) {
        return roleRepo.save(role);
    }
    @Override
    public Optional<Role> getRoleByName(String roleName) {
        return roleRepo.findByName(roleName);
    }
}
