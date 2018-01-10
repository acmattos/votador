package br.com.alterdata.votador.funcionario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author acmattos
 * @since 03/01/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FuncionarioRepositoryTest {
   @Autowired
   private FuncionarioRepository repository;
   
   @Test
   public void save_criarOfuscaSenha(){
      String senha = "senhanaoofuscada";
      Funcionario funcionario = Funcionario.builder()
         .nome("Catarina Santos")
         .email("catarina.qa@alterdata.com.br")
         .senha(senha)
         .build();
      assertEquals(senha, funcionario.getSenha());
      
      repository.save(funcionario);
      assertNotEquals(senha, funcionario.getSenha());
      assertTrue(new BCryptPasswordEncoder().matches(senha, funcionario.getSenha()));
   }
   
   @Test
   public void save_editarNaoOfuscaSenha(){
      String senha = "senhanaoofuscada";
      Funcionario funcionario = Funcionario.builder()
         .nome("Marta Santos")
         .email("marta.qa@alterdata.com.br")
         .senha(senha)
         .build();
      assertEquals(senha, funcionario.getSenha());
      
      repository.save(funcionario);
      assertNotEquals(senha, funcionario.getSenha());
      assertTrue(new BCryptPasswordEncoder().matches(senha, funcionario.getSenha()));
   
      String senhaOfuscada = funcionario.getSenha();
      funcionario.setNome(funcionario.getNome() + " Souza");
      repository.save(funcionario);
      assertNotEquals(senha, funcionario.getSenha());
      assertEquals(senhaOfuscada, funcionario.getSenha());
      assertFalse(new BCryptPasswordEncoder().matches(senhaOfuscada, funcionario.getSenha()));
      assertTrue(new BCryptPasswordEncoder().matches(senha, funcionario.getSenha()));
   }
}