package com.colonbackend.colon.controllers;

import com.colonbackend.colon.model.UserDTO;
import com.colonbackend.colon.model.User;
import com.colonbackend.colon.model.status.*;
import com.colonbackend.colon.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persona")
public class PersonaControllers {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    public List<User> getuser(){

    return personaRepository.findAll();
    }

    @GetMapping("error/500/ezequiel")

    public ResponseEntity<e500> status500(){
        e500 u = new e500();
        u.setError("Error interno del servidor");
        return new ResponseEntity<e500>(u,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("error/404/ezequiel")

    public ResponseEntity<e404> status404(){
        e404 u = new e404();
        u.setError("Error endpoint no encontrado");
        return new ResponseEntity<e404>(u,HttpStatus.NOT_FOUND);
    }
    @GetMapping("error/401/ezequiel")

    public ResponseEntity<e401> status401(){
        e401 u = new e401();
        u.setError("Error acceso no autorizado");
        return new ResponseEntity<e401>(u,HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User usuario = new User();
        usuario.setNombre(userDTO.getNombre());
        usuario.setApellido(userDTO.getApellido());
        personaRepository.save(usuario);
        return new ResponseEntity<User>(usuario,HttpStatus.OK);
    }
    @PostMapping(path = "error/500", consumes = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<e500> e500(@RequestBody UserDTO UserDTO){
        e500 usuario = new e500();
        usuario.setError(UserDTO.getNombre());
        usuario.setError("Error interno del servidor");
        return new ResponseEntity<e500>(usuario,HttpStatus.OK);
    }
    @PostMapping(path = "error/404", consumes = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<e404> e404(@RequestBody UserDTO UserDTO){
        e404 usuario = new e404();
        usuario.setError(UserDTO.getNombre());
        usuario.setError("Error endpoint no encontrado");
        return new ResponseEntity<e404>(usuario,HttpStatus.OK);
    }
    @PostMapping(path = "error/401", consumes = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<e401> e401(@RequestBody UserDTO UserDTO){
        e401 usuario = new e401();
        usuario.setError(UserDTO.getNombre());
        usuario.setError("Error acceso no autorizado");
        return new ResponseEntity<e401>(usuario,HttpStatus.OK);
    }



}


