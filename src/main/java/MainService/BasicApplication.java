package MainService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("Entities")
@ComponentScan("Controllers")
@ComponentScan("secure")
@EnableJpaRepositories("Repositories")
public class BasicApplication extends SpringBootServletInitializer{
    
	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}
      
}