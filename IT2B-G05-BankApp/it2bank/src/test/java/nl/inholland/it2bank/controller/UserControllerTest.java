package nl.inholland.it2bank.controller;

import nl.inholland.it2bank.config.ApiTestConfiguration;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ApiTestConfiguration.class)
public class UserControllerTest {

    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void getShouldReturnOkStatus() throws Exception{

        Mockito.when(userService.findUserByAttributes(null, null, null, null, null, null, null, null, null))
                .thenReturn(List.of(
                        new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.User, 50.0, 100.0)
                ));

        mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    void postShouldReturnCreatedStatus() throws Exception{
        UserDTO userDto = new UserDTO("FirstName", "LastName", 12345678L, "06123456789", "email@address.com", "Password", "Employee", 50.0, 100.0);

        Mockito.when(userService.addUser(userDto))
                .thenReturn(new UserModel(userDto.firstName(), userDto.lastName(), userDto.bsn(), userDto.phoneNumber(), userDto.email(), userDto.password(), UserRoles.valueOf(userDto.role()), userDto.transactionLimit(), userDto.dailyLimit()));

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"firstName\": \"FirstName\",\"lastName\": \"LastName\",\"bsn\": 12345678,\"phoneNumber\": \"06123456789\",\"email\": \"email@address.com\",\"password\": \"Password\",\"role\": \"User\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void deleteShouldReturnNoContentStatus() throws Exception{
        Integer userId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void putShouldReturnOkStatusWithBody() throws Exception{
        UserModel user = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.User, 50.0, 100.0);
        user.setId(1);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"firstName\": \"FirstName\",\"lastName\": \"LastName\",\"bsn\": 12345678,\"phoneNumber\": \"06123456789\",\"email\": \"email@address.com\",\"password\": \"Password\",\"role\": \"User\", \"transactionLimit\": 50.0, \"dailyLimit\": 100.0}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"firstName\": \"FirstName\",\"lastName\": \"LastName\",\"bsn\": 12345678,\"phoneNumber\": \"06123456789\",\"email\": \"email@address.com\",\"password\": \"Password\",\"role\": \"User\", \"transactionLimit\": 50.0, \"dailyLimit\": 100.0}"));
    }

}
