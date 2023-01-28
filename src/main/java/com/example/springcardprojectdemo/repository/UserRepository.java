package com.example.springcardprojectdemo.repository;

import com.example.springcardprojectdemo.entity.Role;
import com.example.springcardprojectdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("delete from users u where u.email = ?1")
    public void deleteByEmail(String email);

    @Transactional
    @Modifying
    @Query("update users u set u.verified=true, u.roles=?2 where u.email = ?1")
    public void updateVerifyAndRoleWithEmail(String email, Role role);
}
