package br.com.alterdata.votador.autorizacao;

import br.com.alterdata.votador.funcionario.Funcionario;
import br.com.alterdata.votador.funcionario.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author acmattos
 */
@Service
public class VotadorUserDetailsService implements UserDetailsService {
   @Autowired
   private FuncionarioRepository funcionarioRepository;
   
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Funcionario funcionario = funcionarioRepository.findByEmail(username);
      UserDetails userDetails = VotadorUserDetails.builder()
         .id(funcionario.getId())
         .name(funcionario.getNome())
         .username(funcionario.getEmail())
         .password(funcionario.getSenha())
         .accountNonExpired(true)
         .accountNonLocked(true)
         .credentialsNonExpired(true)
         .enabled(true)
         .authorities(new ArrayList<>()).build();
      return userDetails;
   }
}
