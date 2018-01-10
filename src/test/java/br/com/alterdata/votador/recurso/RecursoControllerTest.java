package br.com.alterdata.votador.recurso;

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
public class RecursoControllerTest {
   @Autowired
   private ObjectMapper objectMapper;
   
   @Autowired
   private RecursoRepository repository;
   
   @Autowired
   private WebApplicationContext context;
   
   private MockMvc mvc;
   
   @Before
   public void setUp() throws Exception {
      this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
   }
   
   @Test
   public void criar() throws Exception {
      Recurso recurso = Recurso.builder()
         .descricao("criar")
         .build();
      this.mvc.perform(post("/recursos")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(recurso)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$.descricao", is("criar")));
      
      assertEquals(6, repository.findAll().size());
   }
   
   @Test
   public void listar() throws Exception {
      this.mvc.perform(get("/recursos"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$", hasSize(5)))
         .andExpect(jsonPath("$[0].id", is(1)))
         .andExpect(jsonPath("$[0].descricao", is("Recurso 1")));
   }
   
   @Test
   public void listarUm() throws Exception {
      this.mvc.perform(get("/recursos/1"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$.id", is(1)))
         .andExpect(jsonPath("$.descricao", is("Recurso 1")));
   }
   
   @Test
   public void editar() throws Exception {
      Recurso recurso = repository.findOne(5L);
      assertEquals("Recurso 5", recurso.getDescricao());
      
      recurso.setDescricao("Editar");
      this.mvc.perform(put("/recursos")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(recurso)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$.descricao", is("Editar")));
   }
   
   @Test
   public void apagar() throws Exception {
      Recurso recurso = Recurso.builder()
         .descricao("deletar")
         .build();
      repository.save(recurso);
      
      assertEquals(6, repository.findAll().size());
      
      this.mvc.perform(delete("/recursos/" + recurso.getId()))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$.id", is(recurso.getId().intValue())))
         .andExpect(jsonPath("$.descricao", is("deletar")));
      
      assertEquals(5, repository.findAll().size());
   }
   
}