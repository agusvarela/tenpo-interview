package com.tenpo.interview.repository;

import com.tenpo.interview.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository is used for user information.
 *
 * @author Agustin-Varela
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);
}