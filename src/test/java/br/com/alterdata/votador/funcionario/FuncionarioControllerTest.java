package br.com.alterdata.votador.funcionario;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author acmattos
 * @since 03/01/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FuncionarioControllerTest {
   
   @Autowired
   private ObjectMapper objectMapper;
   
   @Autowired
   private FuncionarioRepository repository;
   
   @Autowired
   private WebApplicationContext context;
   
   private MockMvc mvc;
   
   @Before
   public void setUp() throws Exception {
      this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
   }
     
   @Test
   public void criar() throws Exception {
      FuncionarioVO funcionario = FuncionarioVO.builder()
         .nome("criar")
         .email("criar")
         .senha("criar")
         .build();
      this.mvc.perform(post("/funcionarios")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(funcionario)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$.nome", is("criar")))
         .andExpect(jsonPath("$.email", is("criar")));
      
      assertEquals(11, repository.findAll().size());
   }
   
   @Test
   public void listar() throws Exception {
      this.mvc.perform(get("/funcionarios"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$", hasSize(10)))
         .andExpect(jsonPath("$[0].id", is(1)))
         .andExpect(jsonPath("$[0].nome", is("Jorge Marques")));
   }
   
   @Test
   public void listarUm() throws Exception {
      this.mvc.perform(get("/funcionarios/1"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$.id", is(1)))
         .andExpect(jsonPath("$.nome", is("Jorge Marques")));
   }
   
   @Test
   public void editar() throws Exception {
      FuncionarioVO funcionario = FuncionarioVO.converteParaVO(
         repository.findOne(5L));
      assertEquals("Carlos Maia", funcionario.getNome());
      
      funcionario.setNome("Editar");
      this.mvc.perform(put("/funcionarios")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(funcionario)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$.nome", is("Editar")))
         .andExpect(jsonPath("$.email", is("carlos.financeiro@alterdata.com.br")));
   }
   
   @Test
   public void apagar() throws Exception {
      Funcionario funcionario = Funcionario.builder()
         .nome("deletar")
         .email("deletar")
         .senha("deletar")
         .build();
      repository.save(funcionario);
      
      assertEquals(11, repository.findAll().size());
   
      this.mvc.perform(delete("/funcionarios/" + funcionario.getId()))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$.id", is(funcionario.getId().intValue())))
         .andExpect(jsonPath("$.nome", is("deletar")));
   
      assertEquals(10, repository.findAll().size());
   }
   
}