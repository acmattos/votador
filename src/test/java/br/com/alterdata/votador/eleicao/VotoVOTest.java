package br.com.alterdata.votador.eleicao;

import br.com.alterdata.votador.funcionario.FuncionarioVO;
import br.com.alterdata.votador.recurso.RecursoVO;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author acmattos
 * @since 09/01/2018.
 */
public class VotoVOTest {
   
   private VotoVO vo;
   
   @Before
   public void setUp() throws Exception {
      FuncionarioVO funcionarioVO = FuncionarioVO.builder()
         .id(1L)
         .nome("nome")
         .email("email")
         .senha("senha")
         .build();
      RecursoVO recursoVO = RecursoVO.builder()
         .id(2L)
         .descricao("descricao")
         .totalDeVotos(0L)
         .build();
      this.vo = VotoVO.builder()
         .id(3L)
         .comentario("comentario")
         .datahora("2018-01-01 01:23:45 -0300")//"yyyy-MM-dd HH:mm:ss Z"
         .funcionarioVO(funcionarioVO)
         .recursoVO(recursoVO)
         .build();
   }
   
   @Test
   public void ehValido_retornaTrue() throws Exception {
      assertTrue(this.vo.ehValido());
   }
   
   @Test
   public void ehValido_retornaFalse() throws Exception {
      this.vo.setComentario(null);
      assertFalse(this.vo.ehValido());
      this.vo.setComentario("");
      assertFalse(this.vo.ehValido());
      this.vo.setComentario("alterado");
      this.vo.setDatahora(null);
      assertFalse(this.vo.ehValido());
      this.vo.setDatahora("");
      assertFalse(this.vo.ehValido());
      this.vo.setDatahora("2018-01-01 01:23:45");
      FuncionarioVO funcionarioVO = this.vo.getFuncionarioVO();
      this.vo.setFuncionarioVO(null);
      assertFalse(this.vo.ehValido());
      funcionarioVO.setId(null);
      this.vo.setFuncionarioVO(funcionarioVO);
      assertFalse(this.vo.ehValido());
      funcionarioVO.setId(1000L);
      RecursoVO recursoVO = this.vo.getRecursoVO();
      this.vo.setRecursoVO(null);
      assertFalse(this.vo.ehValido());
      recursoVO.setId(null);
      this.vo.setRecursoVO(recursoVO);
      assertFalse(this.vo.ehValido());
      recursoVO.setId(2000L);
      assertTrue(this.vo.ehValido());
   }
   
   @Test
   public void converteParaListaDeVO_listaComElemento() throws Exception {
      Voto voto = VotoVO.converteParaEntidade(this.vo);
      List<Voto> votos = new ArrayList<Voto>() {{ add(voto); }};
      assertEquals(1, votos.size());
      List<VotoVO> vos = VotoVO.converteParaListaDeVO(votos);
      assertEquals(1, vos.size());
      assertEquals("VotoVO(id=3, comentario=comentario, " +
         "datahora=2018-01-01T01:23:45, funcionarioVO=FuncionarioVO(id=1, " +
         "nome=nome, email=email, senha=senha), recursoVO=RecursoVO(id=2, " +
         "descricao=descricao, totalDeVotos=0, comentario=null))",
         vos.get(0).toString());
   }
   
   @Test
   public void converteParaListaDeVO_listaVazia() throws Exception {
      List<Voto> votos = new ArrayList<>();
      assertEquals(0, votos.size());
      List<VotoVO> vos = VotoVO.converteParaListaDeVO(votos);
      assertEquals(0, vos.size());
   }
   
   @Test
   public void converteParaListaDeVO_listaNula() throws Exception {
      List<Voto> votos = null;
      assertNull(votos);
      List<VotoVO> vos = VotoVO.converteParaListaDeVO(votos);
      assertEquals(0, vos.size());
   }
   
   @Test
   public void converteParaVO_VotoOk() throws Exception {
      Voto voto = VotoVO.converteParaEntidade(this.vo);
      VotoVO vo = VotoVO.converteParaVO(voto);
      this.vo.setDatahora(this.vo.getDatahora().substring(0, 19).replace(" ", "T"));
      assertEquals(vo.toString(), this.vo.toString());
      this.vo.setDatahora(this.vo.getDatahora() + " -0300");
   }
   
   @Test
   public void converteParaVO_VotoNulo() throws Exception {
      Voto voto = null;
      VotoVO vo = VotoVO.converteParaVO(voto);
      assertNull(vo);
   }
   
   @Test
   public void converteParaEntidade_Ok() throws Exception {
      Voto voto = VotoVO.converteParaEntidade(this.vo);
      assertEquals("Voto(id=3, comentario=comentario, " +
         "datahora=2018-01-01T01:23:45, funcionario=Funcionario(id=1, " +
         "nome=nome, email=email, senha=senha), recurso=Recurso(id=2, " +
         "descricao=descricao, totalDeVotos=0))", voto.toString());
   }
   
   @Test
   public void converteParaEntidade_Nula() throws Exception {
      Voto voto = VotoVO.converteParaEntidade(null);
      assertNull(voto);
   }
   
   @Test
   public void converteDataHora_Ok(){
      String datahota = "2018-01-01 01:23:45 -0300";
      LocalDateTime localDateTime = VotoVO.converteDataHora(datahota);
      assertEquals("2018-01-01T01:23:45", localDateTime.toString());
   }
   
   @Test(expected = IllegalArgumentException.class)
   public void converteDataHora_datahoraNula(){
      String datahota = null;
      LocalDateTime localDateTime = null;
      try {
         localDateTime = VotoVO.converteDataHora(datahota);
      }catch (IllegalArgumentException iae){
         assertEquals("datahora nula ou vazia!", iae.getMessage());
         assertNull(localDateTime);
         throw iae;
      }
   }
   
   @Test(expected = IllegalArgumentException.class)
   public void converteDataHora_datahoraVazia(){
      String datahota = "";
      LocalDateTime localDateTime = null;
      try {
         localDateTime = VotoVO.converteDataHora(datahota);
      }catch (IllegalArgumentException iae){
         assertEquals("datahora nula ou vazia!", iae.getMessage());
         assertNull(localDateTime);
         throw iae;
      }
   }
   @Test(expected = DateTimeParseException.class)
   public void converteDataHora_datahoraForaDoPadrao(){
      String datahota = "2018-01-01T01:23:45Z";
      LocalDateTime localDateTime = null;
      try {
         localDateTime = VotoVO.converteDataHora(datahota);
      }catch (DateTimeParseException dtpe){
         assertEquals("Text '2018-01-01T01:23:45Z' could not be parsed at index 10", dtpe.getMessage());
         assertNull(localDateTime);
         throw dtpe;
      }
   }
  
}