package com.example.restapi.domain.user;

import com.example.restapi.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

}
