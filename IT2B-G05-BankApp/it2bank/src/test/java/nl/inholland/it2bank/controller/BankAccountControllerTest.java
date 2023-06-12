package nl.inholland.it2bank.controller;

import nl.inholland.it2bank.config.ApiTestConfiguration;
import nl.inholland.it2bank.model.BankAccountModel;
import nl.inholland.it2bank.service.BankAccountService;
import nl.inholland.it2bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BankAccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ApiTestConfiguration.class)
public class BankAccountControllerTest {

    @MockBean
    BankAccountService bankAccountService;
    @Autowired
    MockMvc mockMvc;
}
