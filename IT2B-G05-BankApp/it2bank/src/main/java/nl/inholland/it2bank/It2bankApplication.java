package nl.inholland.it2bank;

import nl.inholland.it2bank.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class It2bankApplication {
	public static void main(String[] args) {
		SpringApplication.run(It2bankApplication.class, args);
	}

	//Tudor Nosca (678549)
	//Using a bean instead of a database until h2 database is set
	@Bean
	public List<User> generateUsers(){

	}
}
