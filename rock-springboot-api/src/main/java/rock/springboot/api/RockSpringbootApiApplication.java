package rock.springboot.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "rock.springboot")
@EnableAutoConfiguration
public class RockSpringbootApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RockSpringbootApiApplication.class, args);
	}
}
