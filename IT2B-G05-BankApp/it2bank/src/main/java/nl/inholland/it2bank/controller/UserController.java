package nl.inholland.it2bank.controller;

import lombok.extern.java.Log;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.model.dto.LoginDTO;
import nl.inholland.it2bank.model.dto.TokenDTO;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@Log
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody UserDTO userDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.addUser(userDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeUserById(@PathVariable long id){
        try{
            UserModel user = userService.getUserById(id);
            if(user.getRole() == UserRoles.User){
                userService.deleteUser(user.getId());
                return ResponseEntity.ok().body(null);
            } else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }catch(Exception e){
            return ResponseEntity.status(400).body(null);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateUserById(@PathVariable long id, @RequestBody UserModel newUser){
        try{
            UserModel existingUser = userService.getUserById(id);

            existingUser.setFirstName(newUser.getFirstName());
            existingUser.setLastName(newUser.getLastName());
            existingUser.setBsn(newUser.getBsn());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setPhoneNumber(newUser.getPhoneNumber());
            existingUser.setPassword(newUser.getPassword());
            existingUser.setRole(newUser.getRole());

            return ResponseEntity.status(200).body(userService.saveUser(existingUser));
        }catch(Exception e){
            return ResponseEntity.status(400).body(null);
        }
    }
    @PostMapping
    public Object login(@RequestBody LoginDTO dto) throws Exception {
        return new TokenDTO(
                userService.login(dto.email(), dto.password())
        );
    }
}
