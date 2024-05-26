package com.example.Ecommerce;

import com.example.Ecommerce.common.enums.Gender;
import com.example.Ecommerce.common.enums.Role;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	public void run(String... args){
		User adminAccount = userRepository.findByRole(Role.ADMIN);

		if(null == adminAccount){
			User user = new User();

			user.setUsername("admin");
			user.setEmail("admin");
			user.setFirstName("admin");
			user.setGender(Gender.MALE);
			user.setLastName("admin");
			user.setRole(Role.ADMIN);
			user.setVerified(true);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));

			userRepository.save(user);
		}
	}
}
