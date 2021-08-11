package pl.printo3d.onedcutter.cutter1d;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.printo3d.onedcutter.cutter1d.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.repo.UserRepo;
import java.util.stream.*;

@SpringBootApplication
public class Cutter1dApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cutter1dApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepo userRepo) {
			return args -> {
					Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
							UserModel user = new UserModel(name, name + " pass", name.toLowerCase() + "@klops.com", "www." + name.toLowerCase() + ".pl");
							userRepo.save(user);
					});
					userRepo.findAll().forEach(System.out::println);
			};
	}
}
