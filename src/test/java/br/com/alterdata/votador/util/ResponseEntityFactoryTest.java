package br.com.alterdata.votador.util;

import br.com.alterdata.votador.funcionario.Funcionario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author acmattos
 * @since 03/01/2018.
 */
public class ResponseEntityFactoryTest {
    
   @Test
   public void criar_entradaNula(){
      ResponseEntity responseEntity = ResponseEntityFactory.criar(null);
      assertNotNull(responseEntity);
      assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
      assertNull(responseEntity.getBody());
   }
   
   @Test
   public void criar_entradaNaoNula(){
      Funcionario funcionario = getFuncionario();
      ResponseEntity<Funcionario> responseEntity =
         ResponseEntityFactory.criar(funcionario);
      assertNotNull(responseEntity);
      assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
      assertEquals(funcionario, responseEntity.getBody());
   }
   
   @Test
   public void listar_entradaNula(){
      ResponseEntity responseEntity = ResponseEntityFactory.listar(null);
      assertNotNull(responseEntity);
      assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
      assertNull(responseEntity.getBody());
   }
   
   @Test
   public void criar_entradaVazia(){
      List<Funcionario> funcionarios = new ArrayList<>();
      ResponseEntity<List<Funcionario>> responseEntity =
         ResponseEntityFactory.listar(funcionarios);
      assertNotNull(responseEntity);
      assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
      assertEquals(funcionarios, responseEntity.getBody());
   }
   
   @Test
   public void listar_entradaNaoNula(){
      List<Funcionario> funcionarios = new ArrayList<>();
      Funcionario funcionario = getFuncionario();
      funcionarios.add(funcionario);
      ResponseEntity<List<Funcionario>> responseEntity =
         ResponseEntityFactory.listar(funcionarios);
      assertNotNull(responseEntity);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertEquals(funcionarios, responseEntity.getBody());
   }
   
   @Test
   public void listarUm_entradaNula(){
      ResponseEntity responseEntity = ResponseEntityFactory.listarUm(null);
      assertNotNull(responseEntity);
      assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
      assertNull(responseEntity.getBody());
   }
   
   @Test
   public void listarUm_entradaNaoNula(){
      Funcionario funcionario = getFuncionario();
      ResponseEntity<Funcionario> responseEntity =
         ResponseEntityFactory.listarUm(funcionario);
      assertNotNull(responseEntity);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
   }
   
   private Funcionario getFuncionario() {
      return Funcionario.builder()
            .id(1L)
            .nome("Catarina Santos")
            .email("catarina.qa@alterdata.com.br")
            .senha("$2a$10$UGgLXAAqcjuxKKRdSMT8gO7xTvO.1OKd1KyFM.LIa6h5TxkelpGeW")
            .build();
   }
}