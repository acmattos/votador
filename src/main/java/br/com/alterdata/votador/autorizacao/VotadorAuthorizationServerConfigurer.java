package br.com.alterdata.votador.autorizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @author acmattos
 */
@Configuration
@EnableAuthorizationServer
public class VotadorAuthorizationServerConfigurer
   extends AuthorizationServerConfigurerAdapter {
   @Autowired
   private AuthenticationManager authenticationManager;
   
   @Autowired
   private VotadorUserDetailsService votadorUserDetailsService;
   
   @Override
   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients
         .inMemory()
         .withClient("votador-fe")
         .secret("secret")
         .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
         .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
         .scopes("read", "write", "trust")
         .accessTokenValiditySeconds(600)// 10 min
         .refreshTokenValiditySeconds(7200);//120 min
   }
   
   @Override
   public void configure(AuthorizationServerEndpointsConfigurer endpoints)
      throws Exception {
      endpoints
         .authenticationManager(authenticationManager)
         .accessTokenConverter(jwtAccessTokenConverter())
         .tokenStore(tokenStore())
         .userDetailsService(votadorUserDetailsService)
         .reuseRefreshTokens(false);
   }
   
   @Bean
   public TokenStore tokenStore() {
      return new InMemoryTokenStore();
   }
   
   @Bean
   @Primary
   public DefaultTokenServices tokenServices() {
      final DefaultTokenServices tokenServices = new DefaultTokenServices();
      tokenServices.setTokenStore(tokenStore());
      tokenServices.setSupportRefreshToken(true);
      return tokenServices;
   }
   
   @Bean
   public JwtAccessTokenConverter jwtAccessTokenConverter() {
      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
      return converter;
   }
}
