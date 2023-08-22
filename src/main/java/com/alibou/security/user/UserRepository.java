package com.alibou.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT role FROM _user where email = ?1",nativeQuery = true)
    String findByRoleWithEmail(String username);

    @Query(value = "SELECT * FROM _user",nativeQuery = true)
    List<UserEntity> getUsersAll();
}
