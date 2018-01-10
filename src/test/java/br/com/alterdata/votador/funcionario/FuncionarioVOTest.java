package br.com.alterdata.votador.funcionario;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author acmattos
 * @since 09/01/2018.
 */
public class FuncionarioVOTest {
   
   private FuncionarioVO vo;
   
   @Before
   public void setUp() throws Exception {
      this.vo = FuncionarioVO.builder()
         .id(1L)
         .nome("nome")
         .email("email")
         .senha("senha")
         .build();
   }
   
   @Test
   public void ehValido_retornaTrue() throws Exception {
      assertTrue(this.vo.ehValido());
   }
   
   @Test
   public void ehValido_retornaFalse() throws Exception {
      this.vo.setNome(null);
      assertFalse(this.vo.ehValido());
      this.vo.setNome("");
      assertFalse(this.vo.ehValido());
      this.vo.setNome("alterado");
      this.vo.setEmail(null);
      assertFalse(this.vo.ehValido());
      this.vo.setEmail("");
      assertFalse(this.vo.ehValido());
      this.vo.setEmail("alterado");
      this.vo.setSenha(null);
      assertFalse(this.vo.ehValido());
      this.vo.setSenha("");
      assertFalse(this.vo.ehValido());
      this.vo.setSenha("alterado");
      assertTrue(this.vo.ehValido());
   }
   
   @Test
   public void converteParaListaDeVO_listaComElemento() throws Exception {
      Funcionario funcionario = FuncionarioVO.converteParaEntidade(this.vo);
      List<Funcionario> funcionarios = new ArrayList<Funcionario>() {{ add(funcionario); }};
      assertEquals(1, funcionarios.size());
      List<FuncionarioVO> vos = FuncionarioVO.converteParaListaDeVO(funcionarios);
      assertEquals(1, vos.size());
      assertEquals("FuncionarioVO(id=1, nome=nome, email=email, senha=senha)",
         vos.get(0).toString());
   }
   
   @Test
   public void converteParaListaDeVO_listaVazia() throws Exception {
      List<Funcionario> funcionarios = new ArrayList<>();
      assertEquals(0, funcionarios.size());
      List<FuncionarioVO> vos = FuncionarioVO.converteParaListaDeVO(funcionarios);
      assertEquals(0, vos.size());
   }
   
   @Test
   public void converteParaListaDeVO_listaNula() throws Exception {
      List<Funcionario> funcionarios = null;
      assertNull(funcionarios);
      List<FuncionarioVO> vos = FuncionarioVO.converteParaListaDeVO(funcionarios);
      assertEquals(0, vos.size());
   }
   
   @Test
   public void converteParaVO_FuncionarioOk() throws Exception {
      Funcionario funcionario = FuncionarioVO.converteParaEntidade(this.vo);
      FuncionarioVO vo = FuncionarioVO.converteParaVO(funcionario);
      assertEquals(vo.toString(), this.vo.toString());
   }
   
   @Test
   public void converteParaVO_FuncionarioNulo() throws Exception {
      Funcionario funcionario = null;
      FuncionarioVO vo = FuncionarioVO.converteParaVO(funcionario);
      assertNull(vo);
   }
   
   @Test
   public void converteParaEntidade_Ok() throws Exception {
      Funcionario funcionario = FuncionarioVO.converteParaEntidade(this.vo);
      assertEquals("Funcionario(id=1, nome=nome, email=email, senha=senha)",
         funcionario.toString());
   }
   
   @Test
   public void converteParaEntidade_Nula() throws Exception {
      Funcionario funcionario = FuncionarioVO.converteParaEntidade(null);
      assertNull(funcionario);
   }
   
}