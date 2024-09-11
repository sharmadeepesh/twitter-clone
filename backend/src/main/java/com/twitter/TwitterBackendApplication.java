package com.twitter;

import com.twitter.models.Role;
import com.twitter.repositories.RoleRepository;
import com.twitter.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TwitterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepo, UserService userService) {
		return args -> {
			roleRepo.save(Role.builder().authority("USER").build());
		};
	}
	//
}
