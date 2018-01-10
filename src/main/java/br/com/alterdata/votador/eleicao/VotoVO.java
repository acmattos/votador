package br.com.alterdata.votador.eleicao;

import br.com.alterdata.votador.funcionario.FuncionarioVO;
import br.com.alterdata.votador.recurso.RecursoVO;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author acmattos
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VotoVO {
   static final transient DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
   
   private Long id;
   private String comentario;
   public String datahora;
   private FuncionarioVO funcionarioVO;
   private RecursoVO recursoVO;
   
   public boolean ehValido(){
      return null != this.comentario && this.comentario.length() > 0
         && null != this.datahora && this.datahora.length() > 0
         && null != this.funcionarioVO && null != this.funcionarioVO.getId()
         && null != this.recursoVO && null != this.recursoVO.getId();
   }
   
   public static List<VotoVO> converteParaListaDeVO(List<Voto> votos){
      List<VotoVO> vos = new ArrayList<>();
      if(null != votos) {
         votos.stream().forEach(voto -> {
            vos.add(converteParaVO(voto));
         });
      }
      return vos;
   }
   
   public static VotoVO converteParaVO(Voto voto){
      VotoVO vo = null;
      if(null != voto){
         vo = VotoVO.builder()
               .id(voto.getId())
               .comentario(voto.getComentario())
               .datahora(voto.getDatahora().toString())
               .funcionarioVO(FuncionarioVO.converteParaVO(voto.getFuncionario()))
               .recursoVO(RecursoVO.converteParaVO(voto.getRecurso()))
               .build();
      }
      return vo;
   }
   
   public static LocalDateTime converteDataHora(String datahota){
      if (null != datahota && !datahota.isEmpty()){
         return LocalDateTime.parse(datahota, FORMATTER);
      }
      throw new IllegalArgumentException("datahora nula ou vazia!");
   }
   
   public static Voto converteParaEntidade(VotoVO vo){
      Voto entidade = null;
      if(null != vo) {
         entidade = Voto.builder()
            .id(vo.getId())
            .comentario(vo.getComentario())
            .datahora(converteDataHora(vo.getDatahora()))
            .funcionario(FuncionarioVO.converteParaEntidade(vo.getFuncionarioVO()))
            .recurso(RecursoVO.converteParaEntidade(vo.getRecursoVO()))
            .build();
      }
      return entidade;
   }
}
