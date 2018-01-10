package br.com.alterdata.votador.eleicao;

import br.com.alterdata.votador.funcionario.Funcionario;
import br.com.alterdata.votador.funcionario.FuncionarioRepository;
import br.com.alterdata.votador.funcionario.FuncionarioVO;
import br.com.alterdata.votador.recurso.Recurso;
import br.com.alterdata.votador.recurso.RecursoRepository;
import br.com.alterdata.votador.recurso.RecursoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author acmattos
 * @since 08/01/2018.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EleicaoControllerTest {
   
   @InjectMocks
   private EleicaoController controller;
   
   @Autowired
   private ObjectMapper objectMapper;

   @Mock
   private VotoRepository votoRepository;

   @Mock
   private FuncionarioRepository funcionarioRepository;

   @Mock
   private RecursoRepository recursoRepository;

   private MockMvc mvc;
   
   @Before
   public void setup() throws Exception {
      this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
   }
   
   @Test
   public void recursosNaoVotadosPorFuncionario_comRecursoParaVotar() throws Exception {
      Recurso recurso = getRecurso();
      List<Recurso> recursos = new ArrayList<Recurso>(){{ add(recurso);}};

      when(recursoRepository.findAllRecursosNaoVotadosPorFuncionario(anyLong())).thenReturn(recursos);
      this.mvc.perform(get("/eleicao/recursos/1"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is(1)))
         .andExpect(jsonPath("$[0].descricao", is("descricao")));
      verify(recursoRepository, times(1))
         .findAllRecursosNaoVotadosPorFuncionario(anyLong());
      verifyNoMoreInteractions(recursoRepository);
   }
   
   @Test
   public void recursosNaoVotadosPorFuncionario_semRecursoParaVotar() throws Exception {
      List<Recurso> recursos = new ArrayList<>();
      when(recursoRepository.findAllRecursosNaoVotadosPorFuncionario(anyLong())).thenReturn(recursos);
      this.mvc.perform(get("/eleicao/recursos/1"))
         .andExpect(status().isNotFound())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$", hasSize(0)));
      verify(recursoRepository, times(1))
         .findAllRecursosNaoVotadosPorFuncionario(anyLong());
      verifyNoMoreInteractions(recursoRepository);
   }
   
   @Test
   public void computarVoto() throws Exception {
      Funcionario funcionario = getFuncionario();
      Recurso recurso = getRecurso();
      String datahoraResposta = LocalDateTime.now().toString().substring(0, 19);
      String datahora = datahoraResposta.replace("T", " ").concat(" -0300");
      VotoVO voto = VotoVO.builder()
         .comentario("comentario")
         .datahora(datahora)
         .funcionarioVO(FuncionarioVO.converteParaVO(funcionario))
         .recursoVO(RecursoVO.converteParaVO(recurso))
         .build();
      
      when(funcionarioRepository.findOne(anyLong())).thenReturn(funcionario);
      when(recursoRepository.findOne(anyLong())).thenReturn(recurso);
      when(votoRepository.save(any(Voto.class))).thenReturn(any(Voto.class));
      this.mvc.perform(post("/eleicao/votos")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(voto)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$.comentario", is("comentario")))
         .andExpect(jsonPath("$.datahora", is(datahoraResposta)))
         .andExpect(jsonPath("$.funcionarioVO.id", is(2)))
         .andExpect(jsonPath("$.recursoVO.id", is(1)));
      verify(funcionarioRepository, times(1)).findOne(anyLong());
      verify(recursoRepository, times(1)).findOne(anyLong());
      verify(votoRepository, times(1)).save(any(Voto.class));
      verifyNoMoreInteractions(funcionarioRepository);
      verifyNoMoreInteractions(recursoRepository);
      verifyNoMoreInteractions(votoRepository);
   }
   
   @Test
   public void resultados() throws Exception {
      Recurso recurso = getRecurso();
      List<RecursoVO> recursos = new ArrayList<RecursoVO>(){{
         add(RecursoVO.converteParaVO(recurso));}};
   
      when(recursoRepository.findAllRecursosComTotalDeVotos()).thenReturn(recursos);
      this.mvc.perform(get("/eleicao/resultados"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is(1)))
         .andExpect(jsonPath("$[0].descricao", is("descricao")))
         .andExpect(jsonPath("$[0].totalDeVotos", is(0)));
      verify(recursoRepository, times(1))
         .findAllRecursosComTotalDeVotos();
      verifyNoMoreInteractions(recursoRepository);
   }
   
   @Test
   public void participantes() throws Exception {
      Funcionario funcionario = getFuncionario();
      Recurso recurso = getRecurso();
      LocalDateTime datahora = LocalDateTime.now();
      String datahoraResposta = datahora.toString().substring(0, 19).replace("T", " ").concat(" -0300");
      Voto voto = Voto.builder()
         .id(3L)
         .comentario("comentario")
         .datahora(datahora)
         .funcionario(funcionario)
         .recurso(recurso)
         .build();
      List<Voto> votos = new ArrayList<Voto>(){{
         add(voto);}};
   
      when(votoRepository.findAllVotosOrdenadosPorFuncionarioErecurso()).thenReturn(votos);
      this.mvc.perform(get("/eleicao/participantes"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is( 3)))
         .andExpect(jsonPath("$[0].comentario", is("comentario")))
         .andExpect(jsonPath("$[0].datahora", is(datahora.toString())))
         .andExpect(jsonPath("$[0].funcionarioVO.nome", is("nome")))
         .andExpect(jsonPath("$[0].recursoVO.descricao", is("descricao")));
      verify(votoRepository, times(1))
         .findAllVotosOrdenadosPorFuncionarioErecurso();
      verifyNoMoreInteractions(votoRepository);
   }
   
   private Funcionario getFuncionario() {
      Funcionario funcionario = Funcionario.builder()
         .id(2L)
         .nome("nome")
         .email("email")
         .senha("senha")
         .build();
      return funcionario;
   }
  
   private Recurso getRecurso() {
      Recurso recurso = Recurso.builder()
         .id(1L)
         .descricao("descricao")
         .build();
      return recurso;
   }
}