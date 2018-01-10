package br.com.alterdata.votador.autorizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author acmattos
 */
@Configuration
@EnableWebSecurity
public class VotadorWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
   
   @Autowired
   private VotadorAuthenticationProvider votadorAuthenticationProvider;
      
   @Override
   protected void configure(
      AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(votadorAuthenticationProvider);
   }
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
         .csrf().disable()
         .anonymous().disable()
         .authorizeRequests()
         .antMatchers("/oauth/token").permitAll();
   }
   
   @Override
   @Bean
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }
}
