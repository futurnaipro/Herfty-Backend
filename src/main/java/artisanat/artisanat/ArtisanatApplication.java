package artisanat.artisanat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import artisanat.artisanat.model.Services.AdminService;
import artisanat.artisanat.model.Services.UserService;

@SpringBootApplication
public class ArtisanatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtisanatApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AdminService adminService){
		return args -> {
		//	adminService.addAdmin("admin", "admin", "admin@gmail.com", "company", "admin", "admin");
		};
	}


}
