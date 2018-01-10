package br.com.alterdata.votador.autorizacao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author acmattos
 */
@Builder
@Getter
@Setter
public class VotadorUserDetails implements UserDetails {
   private long id;
   private String password;
   private String username;
   private String name;
   private Collection<? extends GrantedAuthority> authorities;
   private boolean accountNonExpired;
   private boolean accountNonLocked;
   private boolean credentialsNonExpired;
   private boolean enabled;
}
