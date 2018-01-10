package br.com.alterdata.votador.autorizacao;

import br.com.alterdata.votador.funcionario.Funcionario;
import br.com.alterdata.votador.funcionario.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author acmattos
 */
@Component
public class VotadorAuthenticationProvider implements AuthenticationProvider {
   
   @Autowired
   private FuncionarioRepository repository;
   
   @Autowired
   private PasswordEncoder encoder;
   
   @Override
   public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      String email = authentication.getName();
      String senha = null != authentication.getCredentials()
         ? authentication.getCredentials().toString()
         : "";
      Funcionario funcionario = repository.findByEmail(email);
      if (null != funcionario && encoder.matches(senha, funcionario.getSenha())) {
         return new UsernamePasswordAuthenticationToken(
            email, senha, new ArrayList<>());
      } else {
         return null;
      }
   }
   
   @Override
   public boolean supports(Class<?> authentication) {
      return UsernamePasswordAuthenticationToken.class.isAssignableFrom(
         authentication);
   }
}
