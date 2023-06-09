package nl.inholland.it2bankk.controller;

import lombok.extern.java.Log;
import nl.inholland.it2bankk.model.dto.LoginDTO;
import nl.inholland.it2bankk.model.dto.TokenDTO;
import nl.inholland.it2bankk.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
@Log
public class LoginController {

    private final UserService userService;

    public LoginController(@Lazy UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public Object login(@RequestBody LoginDTO dto) throws Exception {
        return new TokenDTO(
                userService.login(dto.email(), dto.password())
        );
    }
}
