package nl.inholland.it2bank.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import nl.inholland.it2bank.model.TransactionModel;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Log
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Get transactions", notes = "Retrieve users based on filters")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved users", response = UserModel.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<List<UserModel>> getUsersByAttributes(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "bsn", required = false) Long bsn,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "roleId", required = false) Integer roleId
    ) {
        List<UserModel> users = userService.findUserByAttributes(firstName, lastName, bsn, phoneNumber, email, roleId);
        return ResponseEntity.ok(users);
    }


    @GetMapping("{id}")
    @ApiOperation(value = "Get user by id", notes = "Returns user of given ID from path")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user with given ID", response = UserModel.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 400, message = "Malformed request syntax"),
            @ApiResponse(code = 404, message = "Could not find user")
    })
    public ResponseEntity<Object> getUserById(@PathVariable long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping
    @ApiOperation(value = "Register User", notes = "Successfully registered user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user", response = UserModel.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<Object> addUser(@RequestBody UserDTO userDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.addUser(userDto));
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete user by id", notes = "Deletes user of given ID from path")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted user with given ID"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 400, message = "Malformed request syntax"),
            @ApiResponse(code = 404, message = "Could not find user")
    })
    public ResponseEntity<Object> removeUserById(@PathVariable long id){
            UserModel user = userService.getUserById(id);
            if(user.getRoleId() == 3){
                userService.deleteUser(user.getId());
                return ResponseEntity.ok().body(null);
            } else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update user by id", notes = "Updates user of given ID from path")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user with given ID", response = UserModel.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 400, message = "Malformed request syntax"),
            @ApiResponse(code = 404, message = "Could not find user")
    })
    public ResponseEntity<Object> updateUserById(@PathVariable long id, @RequestBody UserModel newUser){
            UserModel existingUser = userService.getUserById(id);

            existingUser.setFirstName(newUser.getFirstName());
            existingUser.setLastName(newUser.getLastName());
            existingUser.setBsn(newUser.getBsn());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setPhoneNumber(newUser.getPhoneNumber());
            existingUser.setPassword(newUser.getPassword());
            existingUser.setRoleId(newUser.getRoleId());

            return ResponseEntity.ok().body(userService.saveUser(existingUser));
    }
}
