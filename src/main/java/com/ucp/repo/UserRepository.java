package com.ucp.repo;

import com.ucp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query(value = "SELECT * FROM user WHERE role LIKE '%ADMIN%'", nativeQuery = true)
    List<User> findAdminUsers();
}
