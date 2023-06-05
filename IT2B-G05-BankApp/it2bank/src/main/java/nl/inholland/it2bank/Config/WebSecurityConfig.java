package nl.inholland.it2bank.Config;

import io.swagger.models.HttpMethod;
import nl.inholland.it2bank.filter.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig{
    private JwtTokenFilter jwtTokenFilter;

    public WebSecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    // To create our own custom security configuration, we create a SecurityFilterChain bean
    // Read more here: https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().
                csrf((csrf ->
                        csrf
                                .ignoringRequestMatchers("/*")));
        httpSecurity.sessionManagement(
                sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.
                authorizeHttpRequests((
                        authz -> authz
                                .requestMatchers("/users/*", HttpMethod.DELETE.toString())
                                .hasRole("Employee")
                                .requestMatchers("/login", HttpMethod.POST.toString()).permitAll()
                                .anyRequest().authenticated()));

        // We ensure our own filter is executed before the framework runs its own authentication filter code
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
