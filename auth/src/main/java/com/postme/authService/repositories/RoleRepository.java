package com.postme.authService.repositories;

import java.util.Optional;

import com.postme.authService.models.Role;
import com.postme.authService.models.enums.UserRolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRolesEnum name);
}
