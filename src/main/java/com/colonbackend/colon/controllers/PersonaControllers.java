package com.colonbackend.colon.controllers;

import com.colonbackend.colon.model.UserDTO;
import com.colonbackend.colon.model.User;
import com.colonbackend.colon.model.status.*;
import com.colonbackend.colon.repository.PersonaRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/persona")
public class PersonaControllers {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    public List<User> getuser(){

    return personaRepository.findAll();
    }


    @ExceptionHandler({org.springframework.http.converter.HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleJson(){
        return customResponse("Error interno del servidor",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("ezequiel")
    public ResponseEntity<User> get200Eze(){
        UserDTO u = new UserDTO();
        u.setNombre("ezequiel");
        u.setApellido("hermoso");
        return customUserResponse(u,HttpStatus.OK);
    }


    @GetMapping("error/500/ezequiel")

    public ResponseEntity<String> status500(){
        return customResponse("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("error/404/ezequiel")

    public ResponseEntity<String> status404(){
        return customResponse("Error endpoint no encontrado", HttpStatus.NOT_FOUND);

    }

    @GetMapping("error/401/ezequiel")

    public ResponseEntity<String> status401(){
        return customResponse("Error acceso noreturn new ResponseEntity<e404>(u,HttpStatus.NOT_FOUND); autorizado", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        return customUserResponse(userDTO,HttpStatus.OK);
    }

    @PostMapping(value = "error/500")
    public ResponseEntity<String> e500(@RequestBody UserDTO userDTO){
        return customResponse("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping(value = "error/404")
    public ResponseEntity<String> e404(@RequestBody UserDTO userDTO){
        return customResponse("Error endpoint no encontrado", HttpStatus.NOT_FOUND);
    }
    @PostMapping(value = "error/401")
    public ResponseEntity<String> e401(@RequestBody UserDTO userDTO){
        return customResponse("Error acceso no autorizado", HttpStatus.UNAUTHORIZED);
    }

   
    @PutMapping("/ezequiel")
        ResponseEntity<String> status200(@RequestBody UserDTO userDTO){
        userDTO.setNombre("ezequiel");
        return customUserResponse(userDTO,HttpStatus.OK);
        }

    @PutMapping("/error/500/ezequiel")
        ResponseEntity<String> error500Eze(@RequestBody UserDTO userDTO){
            return customResponse("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    @PutMapping("/error/404/ezequiel")
    ResponseEntity<String> error404Eze(@RequestBody UserDTO userDTO){
        return customResponse("Error endpoint no encontrado", HttpStatus.NOT_FOUND);
    }

    @PutMapping( "error/401/ezequiel")
    ResponseEntity<String> error401Eze(@RequestBody UserDTO userDTO){
        return customResponse("Error acceso no autorizado", HttpStatus.UNAUTHORIZED);
    }







        private ResponseEntity customResponse(String content, HttpStatus status){
            Map mapa = new HashMap<String, String>();
            mapa.put("content", content);
            return new ResponseEntity(mapa, status);
        }


        private ResponseEntity customUserResponse(UserDTO userDTO, HttpStatus status ){
        try {
            User usuario = new User();
            usuario.setNombre(userDTO.getNombre());
            usuario.setApellido(userDTO.getApellido());
            personaRepository.save(usuario);
            return new ResponseEntity<>(usuario, status);
        }catch(Exception e){
            return new ResponseEntity<String>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }
}


