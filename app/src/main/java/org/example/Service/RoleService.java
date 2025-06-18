package org.example.Service;

import org.example.model.Role;
import java.util.Optional;

public interface RoleService {
    Role createRole(Role role);
    Optional<Role> getRoleByName(String roleName);
}
