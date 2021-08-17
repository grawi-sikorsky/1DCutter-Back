package pl.printo3d.onedcutter.cutter1d;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import pl.printo3d.onedcutter.cutter1d.userlogin.repo.UserRepo;

@CrossOrigin(origins = "*")
@SpringBootApplication
public class Cutter1dApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cutter1dApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepo userRepo) {
			return args -> {
					userRepo.findAll().forEach(System.out::println);
			};
	}
}
