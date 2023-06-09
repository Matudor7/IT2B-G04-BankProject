package nl.inholland.it2bank.controller;

import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void getShouldReturnAllUsers() throws Exception{
        List<UserModel> users;

        Mockito.when(userService.findUserByAttributes(null, null, null, null, null, null, null, null, null))
                .thenReturn(List.of(
                        new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.User, 50.0, 100.0)
                ));

        mockMvc.perform(MockMvcRequestBuilders.get("/users")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }
}
