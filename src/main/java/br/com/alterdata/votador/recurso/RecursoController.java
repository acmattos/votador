package br.com.alterdata.votador.recurso;

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
class RecursoController {
   @Autowired
   private RecursoRepository repository;
   
   @PostMapping("/recursos")
   ResponseEntity<RecursoVO> criar(@RequestBody RecursoVO vo){
      Recurso recurso = null;
      
      if(vo.ehValido()){
         recurso = RecursoVO.converteParaEntidade(vo);
         try{
            repository.save(recurso);
         } catch(DataIntegrityViolationException dive){
            //TODO Tratamento adequado de descricao nao unica
            dive.printStackTrace();
            recurso = null;
         }
      }
      return ResponseEntityFactory.criar(
         RecursoVO.converteParaVO(recurso));
   }
   
   @GetMapping("/recursos")
   ResponseEntity<List<RecursoVO>> listar() {
      List<Recurso> recursos = repository.findAll();
      
      return ResponseEntityFactory.listar(
         RecursoVO.converteParaListaDeVO(recursos));
   }
   
   @GetMapping("/recursos/{id}")
   ResponseEntity<RecursoVO> listar(@PathVariable long id) {
      Recurso recurso = repository.findOne(id);
      
      return ResponseEntityFactory.listarUm(
         RecursoVO.converteParaVO(recurso));
   }
   
   @PutMapping("/recursos")
   ResponseEntity<RecursoVO> editar(@RequestBody RecursoVO vo){
      Recurso entidade = repository.findOne(vo.getId());
      if(null != entidade){
         try{
            entidade = RecursoVO.converteParaEntidade(vo);
            repository.save(entidade);
         } catch(DataIntegrityViolationException dive){
            //TODO Tratamento adequado de descricao nao unica
            dive.printStackTrace();
            entidade = null;
         }
      }
      return ResponseEntityFactory.listarUm(RecursoVO.converteParaVO(entidade));
   }
   
   @DeleteMapping("/recursos/{id}")
   ResponseEntity<RecursoVO> apagar(@PathVariable long id) {
      Recurso recurso = repository.findOne(id);
      RecursoVO vo = null;
      if(null != recurso) {
         vo = RecursoVO.converteParaVO(recurso);
         repository.delete(id);
      }
      return ResponseEntityFactory.listarUm(vo);
   }
}
