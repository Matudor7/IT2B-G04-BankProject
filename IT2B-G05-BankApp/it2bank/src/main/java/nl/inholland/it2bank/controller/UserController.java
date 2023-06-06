package nl.inholland.it2bank.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import nl.inholland.it2bank.model.TransactionModel;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.model.dto.ExceptionDTO;
import nl.inholland.it2bank.model.dto.LoginDTO;
import nl.inholland.it2bank.model.dto.TokenDTO;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Log
public class UserController {

    private final UserService userService;

    public UserController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Get all users", notes = "Retrieve users based on filters")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved users", response = UserModel.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<List<UserModel>> getUsersByAttributes(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "bsn", required = false) Long bsn,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "role", required = false) UserRoles role,
            @RequestParam(value = "transactionLimit", required = false) Double transactionLimit,
            @RequestParam(value = "dailyLimit", required = false) Double dailyLimit
    ) {
        List<UserModel> users = userService.findUserByAttributes(id, firstName, lastName, bsn, phoneNumber, email, role, transactionLimit, dailyLimit);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @ApiOperation(value = "Register User", notes = "Successfully registered user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user", response = UserModel.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserDTO userDto){
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
    //@PreAuthorize("hasRole('Employee')")
    public ResponseEntity<Object> removeUserById(@PathVariable long id){
        try{
            userService.deleteUser(id);
            return ResponseEntity.status(204).body(null);
        }catch(Exception e){
            return handleException(e);
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
    public ResponseEntity<Object> updateUserById(@PathVariable long id, @RequestBody UserDTO newUser){
        try{
         userService.updateUser(id, newUser);
         return ResponseEntity.status(200).body(newUser);
        }catch(Exception e){
            return handleException(e);
        }
    }

    private ResponseEntity handleException(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getClass().getName(), e.getMessage());
        return ResponseEntity.status(400).body(exceptionDTO);
    }
}
