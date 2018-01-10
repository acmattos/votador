package br.com.alterdata.votador.eleicao;

import br.com.alterdata.votador.funcionario.Funcionario;
import br.com.alterdata.votador.funcionario.FuncionarioRepository;
import br.com.alterdata.votador.recurso.Recurso;
import br.com.alterdata.votador.recurso.RecursoRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author acmattos
 * @since 07/01/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VotoRepositoryTest {
   
   @Autowired
   private FuncionarioRepository funcionarioRepository;
   
   @Autowired
   private RecursoRepository recursoRepository;
   
   @Autowired
   private VotoRepository repository;
   
   @Test
   public void save_votoSemRepeticao(){
   
      List<Voto> votos = repository.findAll();
      assertEquals(0, votos.size());
      
      ZoneId zoneId1 = ZoneId.of("Brazil/Acre");// (UTC-05:00)
      Funcionario funcionario = getFuncionario();
      Recurso recurso = getRecurso("Recurso de Testes");
      Voto voto = Voto.builder()
         .comentario("Recurso muito importante!")
         .datahora(LocalDateTime.now(zoneId1))
         .funcionario(funcionario)
         .recurso(recurso)
         .build();
      repository.save(voto);
   
      votos = repository.findAll();
      assertEquals(1, votos.size());
      repository.delete(voto.getId());
      apagaFuncionario(funcionario);
      apagaRecurso(recurso);
   }
   
   @Test
   public void save_votoSemRepeticao2recursos(){
      
      List<Voto> votos = repository.findAll();
      assertEquals(0, votos.size());
      
      ZoneId zoneId1 = ZoneId.of("Brazil/Acre");// (UTC-05:00)
      Funcionario funcionario = getFuncionario();
      Recurso recurso = getRecurso("Recurso de Testes");
      Voto voto = Voto.builder()
         .comentario("Recurso muito importante!")
         .datahora(LocalDateTime.now(zoneId1))
         .funcionario(funcionario)
         .recurso(recurso)
         .build();
      repository.save(voto);
      Long votoId = voto.getId();
      
      votos = repository.findAll();
      assertEquals(1, votos.size());
   
      Recurso recurso2 = getRecurso("Recurso de Testes 2");
      voto = Voto.builder()
         .comentario("Recurso muito necessário!")
         .datahora(LocalDateTime.now(zoneId1))
         .funcionario(funcionario)
         .recurso(recurso2)
         .build();
      repository.save(voto);
      votos = repository.findAll();
      assertEquals(2, votos.size());
      assertEquals("Recurso muito importante!", votos.get(0).getComentario());
      assertEquals("Recurso muito necessário!", votos.get(1).getComentario());
      repository.delete(votoId);
      repository.delete(voto.getId());
      apagaFuncionario(funcionario);
      apagaRecurso(recurso);
      apagaRecurso(recurso2);
   }
   
   @Test(expected = DataIntegrityViolationException.class)
   public void save_votoComRepeticao(){
      
      List<Voto> votos = repository.findAll();
      assertEquals(0, votos.size());
      
      ZoneId zoneId1 = ZoneId.of("Brazil/Acre");// (UTC-05:00)
      Funcionario funcionario = getFuncionario();
      Recurso recurso = getRecurso("Recurso de Testes");
      Voto voto = Voto.builder()
         .comentario("Recurso muito importante!")
         .datahora(LocalDateTime.now(zoneId1))
         .funcionario(funcionario)
         .recurso(recurso)
         .build();
      repository.save(voto);
      Long votoId = voto.getId();
   
      voto = Voto.builder()
         .comentario("Recurso muito importante, por este motivo mais um voto!")
         .datahora(LocalDateTime.now(zoneId1))
         .funcionario(funcionario)
         .recurso(recurso)
         .build();
      try {
         repository.save(voto);
      }catch (DataIntegrityViolationException dive){
         votos = repository.findAll();
         assertEquals("could not execute statement; SQL [n/a]; constraint [funcionario_recurso_ukey]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement", dive.getMessage());
         assertEquals(1, votos.size());
         repository.delete(votoId);
         apagaFuncionario(funcionario);
         apagaRecurso(recurso);
         throw dive;
      }
   }
   
   @Test
   public void save_votoComTimezoneDiferente(){
      ZoneId zoneId1 = ZoneId.of("America/Bogota");//"Brazil/Acre");// (UTC-05:00)
      ZoneId zoneId2 = ZoneId.of("Brazil/West");//(UTC-04:00)
      ZoneId zoneId3 = ZoneId.of("Brazil/East");// (UTC-03:00)
      ZoneId zoneId4 = ZoneId.of("Brazil/DeNoronha");// (UTC-02:00)
      System.out.println("zoneId1 = " + zoneId1+" - " +zoneId1.getRules());
      System.out.println("zoneId2 = " + zoneId2 +" - " +zoneId2.getRules());
      System.out.println("zoneId3 = " + zoneId3 +" - " +zoneId3.getRules());
      System.out.println("zoneId4 = " + zoneId4 +" - " +zoneId4.getRules());
//      Funcionario funcionario = getFuncionario();
//      Recurso recurso = getRecurso();
//      Voto voto = Voto.builder()
//         .comentario("Recurso muito importante!")
//         .datahora(LocalDateTime.now())
//         .funcionario(funcionario)
//         .recurso(recurso)
//         .build();
      //repository.save(voto);
//      String senha = "senhanaoofuscada";
//      Funcionario funcionario = Funcionario.builder()
//         .nome("Catarina Santos")
//         .email("catarina.qa@alterdata.com.br")
//         .senha(senha)
//         .build();
//      assertEquals(senha, funcionario.getSenha());
//
//      repository.save(funcionario);
//      assertNotEquals(senha, funcionario.getSenha());
//      assertTrue(new BCryptPasswordEncoder().matches(senha, funcionario.getSenha()));
   }
   
   private Funcionario getFuncionario() {
      Funcionario funcionario = Funcionario.builder()
         .nome("Vitor Santos")
         .email("vitor.qa@alterdata.com.br")
         .senha("vitor")
         .build();
      funcionarioRepository.save(funcionario);
      return funcionario;
   }
   
   private void apagaFuncionario(Funcionario funcionario) {
      funcionarioRepository.delete(funcionario.getId());
   }
   
   private Recurso getRecurso(String descricao) {
      Recurso recurso = Recurso.builder().descricao(descricao).build();
      recursoRepository.save(recurso);
      return recurso;
   }
   
   private void apagaRecurso(Recurso recurso) {
      recursoRepository.delete(recurso.getId());
   }
}