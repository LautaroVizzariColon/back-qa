package com.colonbackend.colon.controllers;

import com.colonbackend.colon.model.User;
import com.colonbackend.colon.model.UserDTO;
import com.colonbackend.colon.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
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
    public ResponseEntity<List<User>> get200Eze(){

        return getUserResponseByFullName(new UserDTO("ezequiel","hermoso"));
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
        return customResponse("Error acceso no autorizado", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        return customUserResponseIns(userDTO,HttpStatus.OK);
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

   
    @PutMapping("/{nombre}")
        ResponseEntity<User> status200(@PathVariable ("nombre") String nombre, @RequestBody UserDTO userDTO){


        return customUpdate(nombre, userDTO,HttpStatus.OK);
        }

    @Transactional
    private ResponseEntity<User> customUpdate(String nombre, UserDTO userDTO, HttpStatus httpStatus){

        List<User> users = personaRepository.findAllByNombre(nombre);
        Boolean nombreParam = false;
        Boolean apellidoParam = false;

        if(userDTO.getNombre() != null && !userDTO.getNombre().isEmpty())
            nombreParam = true;

        if(userDTO.getApellido() != null && !userDTO.getApellido().isEmpty())
            apellidoParam = true;

        for(User user: users){
            if(nombreParam)
                user.setNombre(userDTO.getNombre());
            if(apellidoParam)
                user.setApellido(userDTO.getApellido());
        }

        return new ResponseEntity(users,httpStatus);
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

    private ResponseEntity<User> customUserResponseIns(UserDTO userDTO, HttpStatus status ){
        User user = new User(userDTO.getNombre(),userDTO.getApellido());
        personaRepository.save(user);
        return new ResponseEntity<>(user, status);
    }

    private ResponseEntity<List> getUserResponseByNombre(@RequestBody UserDTO userDTO){

        return new ResponseEntity(personaRepository.findAllByApellido(userDTO.getApellido()),HttpStatus.OK);
    }



    private ResponseEntity<List<User>> getUserResponseByFullName(@RequestBody UserDTO userDTO){
        return new ResponseEntity(personaRepository.findAllByNombreAndApellido(userDTO.getNombre(),userDTO.getApellido()),HttpStatus.OK);
    }

}


