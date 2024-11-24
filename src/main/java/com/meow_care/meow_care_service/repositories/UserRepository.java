package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query("select count(u) from User u inner join u.roles roles where roles.roleName = ?1")
    long countByRolesRoleName(RoleName roleName);

    @Query("select u from User u inner join u.roles roles where roles.roleName = ?1")
    List<User> findByRoles_RoleName(RoleName roleName);


}