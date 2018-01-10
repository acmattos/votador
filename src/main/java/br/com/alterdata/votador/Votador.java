package br.com.alterdata.votador;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author acmattos
 */
@SpringBootApplication
public class Votador extends SpringBootServletInitializer {
	
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(Votador.class).run(args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
	}
}
