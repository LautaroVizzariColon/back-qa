package com.colonbackend.colon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.colonbackend.colon.model.User;
import java.lang.*;

import java.util.List;

@Repository
public interface PersonaRepository  extends JpaRepository<User, Long> {


    List<User> findAllByNombre(java.lang.String nombre);

    List<User> findAllByApellido(String apellido);

    List<User> findAllByNombreAndApellido(String  nombre, String apellido);

}