package br.com.alterdata.votador.eleicao;

import br.com.alterdata.votador.funcionario.Funcionario;
import br.com.alterdata.votador.funcionario.FuncionarioRepository;
import br.com.alterdata.votador.recurso.Recurso;
import br.com.alterdata.votador.recurso.RecursoRepository;
import br.com.alterdata.votador.recurso.RecursoVO;
import br.com.alterdata.votador.util.ResponseEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author acmattos
 */
@RestController
class EleicaoController {
  
   @Autowired
   private VotoRepository votoRepository;
   
   @Autowired
   private FuncionarioRepository funcionarioRepository;
   
   @Autowired
   private RecursoRepository recursoRepository;
   
   @GetMapping("/eleicao/recursos/{funcionarioId}")
   ResponseEntity<List<RecursoVO>> recursosNaoVotadosPorFuncionario(
      @PathVariable long funcionarioId){
      List<Recurso> recursos =
         recursoRepository.findAllRecursosNaoVotadosPorFuncionario(funcionarioId);
      return ResponseEntityFactory.listar(
         RecursoVO.converteParaListaDeVO(recursos));
   }
   
   @PostMapping("/eleicao/votos")
   ResponseEntity<VotoVO> computarVoto(@RequestBody VotoVO votoVO){
      Voto voto = null;
      if(votoVO.ehValido()) {
         Funcionario funcionario =
            funcionarioRepository.findOne(votoVO.getFuncionarioVO().getId());
         Recurso recurso = recursoRepository.findOne(votoVO.getRecursoVO().getId());
         voto = Voto.builder()
            .comentario(votoVO.getComentario())
            .datahora(VotoVO.converteDataHora(votoVO.getDatahora()))
            .funcionario(funcionario)
            .recurso(recurso).build();
         votoRepository.save(voto);
      }
      return ResponseEntityFactory.criar(VotoVO.converteParaVO(voto));
   }
   
   @GetMapping("/eleicao/resultados")
   ResponseEntity<List<RecursoVO>> resultados() {
      List<RecursoVO> recursos = recursoRepository.findAllRecursosComTotalDeVotos();
      return ResponseEntityFactory.listar(recursos);
   }
   
   @GetMapping("/eleicao/participantes")
   ResponseEntity<List<VotoVO>> participantes() {
      List<Voto> votos = votoRepository.findAllVotosOrdenadosPorFuncionarioErecurso();
      return ResponseEntityFactory.listar(
         VotoVO.converteParaListaDeVO(votos));
   }
}
