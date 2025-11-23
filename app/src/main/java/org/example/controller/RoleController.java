package org.example.controller;

import org.example.Service.RoleService;
import org.example.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")

public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService){
        this.roleService=roleService;
    }
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role saved = roleService.createRole(role);
        return ResponseEntity.status(201).body(saved);
    }
    @GetMapping("/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String name) {
        return roleService.getRoleByName(name).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
