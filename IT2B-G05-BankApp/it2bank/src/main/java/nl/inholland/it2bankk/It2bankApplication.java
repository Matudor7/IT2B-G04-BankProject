package nl.inholland.it2bankk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class It2bankApplication {
	public static void main(String[] args) {
		SpringApplication.run(It2bankApplication.class, args);
	}
}
