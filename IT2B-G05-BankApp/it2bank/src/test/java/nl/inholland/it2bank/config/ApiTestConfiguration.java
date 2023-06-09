package nl.inholland.it2bank.config;

import nl.inholland.it2bank.util.JwtTokenProvider;
import org.springframework.boot.test.mock.mockito.MockBean;

@org.springframework.boot.test.context.TestConfiguration
public class ApiTestConfiguration {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;
}
