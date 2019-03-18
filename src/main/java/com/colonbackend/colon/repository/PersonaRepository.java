package com.colonbackend.colon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.colonbackend.colon.model.User;

@Repository
public interface PersonaRepository  extends JpaRepository<User, Long> {

}