package com.example.restapi.domain.matching;

import com.example.restapi.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant,Long> {

    Optional<Participant> findByUser(User user);
}
