package ru.t1.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.t1.bank.exceptions.IncorrectDataException;

import ru.t1.bank.models.Role;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.RoleRepository;
import ru.t1.bank.repository.UserRepository;
import ru.t1.bank.service.UserService;

import java.time.LocalDate;


@SpringBootApplication
public class BankApplication {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void testJpaMethods() throws IncorrectDataException {
		/*Role roleUser = new Role(1L, "ROLE_CLIENT");
		Role roleAdmin = new Role(2L, "ROLE_ADMIN");
		roleRepository.save(roleUser);
		roleRepository.save(roleAdmin);
		User userAdmin = new User();
		userAdmin.setPassword(passwordEncoder.encode("admin"));
		userAdmin.setUsername("admin");
		userAdmin.setFullName("Alex");
		userAdmin.setDateOfBirth(LocalDate.of(1992, 10, 20));
		userAdmin.getRoles().add(new Role(2l, "ROLE_ADMIN"));
		userRepository.save(userAdmin);
		User userClient = new User();
		userClient.setPassword(passwordEncoder.encode("user"));
		userClient.setUsername("user");
		userClient.setFullName("Ivan");
		userClient.setDateOfBirth(LocalDate.of(1992, 10, 20));
		userClient.getRoles().add(new Role(1l, "ROLE_CLIENT"));
		userRepository.save(userClient);*/
	}

}
