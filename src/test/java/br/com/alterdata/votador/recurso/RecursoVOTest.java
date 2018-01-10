package br.com.alterdata.votador.recurso;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author acmattos
 * @since 09/01/2018.
 */
public class RecursoVOTest {
   
   private RecursoVO vo;
   
   @Before
   public void setUp() throws Exception {
      this.vo = RecursoVO.builder()
         .id(1L)
         .descricao("descricao")
         .totalDeVotos(0L)
         .comentario("comentario")
         .build();
   }
   
   @Test
   public void ehValido_retornaTrue() throws Exception {
      assertTrue(this.vo.ehValido());
   }
   
   @Test
   public void ehValido_retornaFalse() throws Exception {
      this.vo.setDescricao(null);
      assertFalse(this.vo.ehValido());
      this.vo.setDescricao("");
      assertFalse(this.vo.ehValido());
      this.vo.setDescricao("alterado");
      this.vo.setTotalDeVotos(null);
      assertTrue(this.vo.ehValido());
      this.vo.setTotalDeVotos(1L);
      this.vo.setComentario(null);
      assertTrue(this.vo.ehValido());
      this.vo.setComentario("");
      assertTrue(this.vo.ehValido());
      this.vo.setComentario("alterado");
      assertTrue(this.vo.ehValido());
   }
   
   @Test
   public void converteParaListaDeVO_listaComElemento() throws Exception {
      Recurso recurso = RecursoVO.converteParaEntidade(this.vo);
      List<Recurso> recursos = new ArrayList<Recurso>() {{ add(recurso); }};
      assertEquals(1, recursos.size());
      List<RecursoVO> vos = RecursoVO.converteParaListaDeVO(recursos);
      assertEquals(1, vos.size());
      assertEquals("RecursoVO(id=1, descricao=descricao, totalDeVotos=0, comentario=null)",
         vos.get(0).toString());
   }
   
   @Test
   public void converteParaListaDeVO_listaVazia() throws Exception {
      List<Recurso> recursos = new ArrayList<>();
      assertEquals(0, recursos.size());
      List<RecursoVO> vos = RecursoVO.converteParaListaDeVO(recursos);
      assertEquals(0, vos.size());
   }
   
   @Test
   public void converteParaListaDeVO_listaNula() throws Exception {
      List<Recurso> recursos = null;
      assertNull(recursos);
      List<RecursoVO> vos = RecursoVO.converteParaListaDeVO(recursos);
      assertEquals(0, vos.size());
   }
   
   @Test
   public void converteParaVO_RecursoOk() throws Exception {
      Recurso recurso = RecursoVO.converteParaEntidade(this.vo);
      RecursoVO vo = RecursoVO.converteParaVO(recurso);
      vo.setComentario("comentario");
      assertEquals(vo.toString(), this.vo.toString());
   }
   
   @Test
   public void converteParaVO_RecursoNulo() throws Exception {
      Recurso recurso = null;
      RecursoVO vo = RecursoVO.converteParaVO(recurso);
      assertNull(vo);
   }
   
   @Test
   public void converteParaEntidade_Ok() throws Exception {
      Recurso recurso = RecursoVO.converteParaEntidade(this.vo);
      assertEquals("Recurso(id=1, descricao=descricao, totalDeVotos=0)",
         recurso.toString());
   }
   
   @Test
   public void converteParaEntidade_Nula() throws Exception {
      Recurso recurso = RecursoVO.converteParaEntidade(null);
      assertNull(recurso);
   }
   
}