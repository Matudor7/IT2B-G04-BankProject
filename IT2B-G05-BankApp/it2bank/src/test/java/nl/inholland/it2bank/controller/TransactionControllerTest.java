package nl.inholland.it2bank.controller;

import nl.inholland.it2bank.config.ApiTestConfiguration;
import nl.inholland.it2bank.model.TransactionModel;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.model.dto.TransactionDTO;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.service.TransactionService;
import nl.inholland.it2bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ApiTestConfiguration.class)
class TransactionControllerTest {
    @MockBean
    TransactionService transactionService;
    @Autowired
    MockMvc mockMvc;
    @Test
    void getShouldReturnOkStatus() throws Exception{

        Mockito.when(transactionService.findTransactionByAttributes(null, null, null, null, null, null))
                .thenReturn(List.of(
                        new TransactionModel(1,"NL01INHO0000000001", "NL01INHO0000000022",200D, LocalDateTime.now(), "Test")
                ));

        mockMvc.perform(MockMvcRequestBuilders.get("/transactions").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    void postShouldReturnCreatedStatus() throws Exception{
        TransactionDTO transactionDto = new TransactionDTO(1,"NL01INHO0000000001", "NL01INHO0000000022",200D, "Test");
        Mockito.when(transactionService.addTransaction(transactionDto))
                .thenReturn(new TransactionModel(transactionDto.userPerforming(), transactionDto.accountFrom(), transactionDto.accountTo(), transactionDto.amount(), LocalDateTime.now(), transactionDto.comment()));

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"userPerforming\": 1,\"accountFrom\": \"NL01INHO0000000001\",\"accountTo\":  \"NL01INHO0000000022\",\"amount\": 200,\"comment\": \"Test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
