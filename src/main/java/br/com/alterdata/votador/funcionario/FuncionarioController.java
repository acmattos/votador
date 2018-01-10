package br.com.alterdata.votador.funcionario;

import br.com.alterdata.votador.util.ResponseEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author acmattos
 */
@RestController
class FuncionarioController {
   
   @Autowired
   private FuncionarioRepository repository;
   
   @PostMapping("/funcionarios")
   ResponseEntity<FuncionarioVO> criar(@RequestBody FuncionarioVO vo){
      Funcionario funcionario = null;
      
      if(vo.ehValido()){
         funcionario = FuncionarioVO.converteParaEntidade(vo);
         try{
            repository.save(funcionario);
         } catch(DataIntegrityViolationException dive){
            //TODO Tratamento adequado de email nao unico
            dive.printStackTrace();
            funcionario = null;
         }
      }
      return ResponseEntityFactory.criar(
         FuncionarioVO.converteParaVO(funcionario));
   }
   
   @GetMapping("/funcionarios")
   ResponseEntity<List<FuncionarioVO>> listar() {
      List<Funcionario> funcionarios = repository.findAll();
            
      return ResponseEntityFactory.listar(
         FuncionarioVO.converteParaListaDeVO(funcionarios));
   }
   
   @GetMapping("/funcionarios/{id}")
   ResponseEntity<FuncionarioVO> listar(@PathVariable long id) {
      Funcionario funcionario = repository.findOne(id);
      
      return ResponseEntityFactory.listarUm(
         FuncionarioVO.converteParaVO(funcionario));
   }
   
   @PutMapping("/funcionarios")
   ResponseEntity<FuncionarioVO> editar(@RequestBody FuncionarioVO vo){
      Funcionario entidade = repository.findOne(vo.getId());
      if(null != entidade){
         try{
            entidade = FuncionarioVO.converteParaEntidade(vo);
            repository.save(entidade);
         } catch(DataIntegrityViolationException dive){
            //TODO Tratamento adequado de email nao unico
            dive.printStackTrace();
            entidade = null;
         }
      }
      return ResponseEntityFactory.listarUm(
         FuncionarioVO.converteParaVO(entidade));
   }
   
   @DeleteMapping("/funcionarios/{id}")
   ResponseEntity<FuncionarioVO> apagar(@PathVariable long id) {
      Funcionario funcionario = repository.findOne(id);
      FuncionarioVO vo = null;
      if(null != funcionario) {
         vo = FuncionarioVO.converteParaVO(funcionario);
         repository.delete(id);
      }
      return ResponseEntityFactory.listarUm(vo);
   }
}
