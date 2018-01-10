package br.com.alterdata.votador.recurso;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author acmattos
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecursoVO {
   private Long id;
   private String descricao;
   private Long totalDeVotos;
   private String comentario;
   
   public RecursoVO(Long id, String descricao, Long totalDeVotos){
      this.id = id;
      this.descricao = descricao;
      this.totalDeVotos = totalDeVotos;
   }
   
   public boolean ehValido(){
      return null != this.descricao && this.descricao.length() > 0;
   }
   
   public static List<RecursoVO> converteParaListaDeVO(
      List<Recurso> recursos){
      List<RecursoVO> vos = new ArrayList<>();
      if(null != recursos) {
         recursos.stream().forEach(recurso -> {
            vos.add(converteParaVO(recurso));
         });
      }
      return vos;
   }
   
   public static RecursoVO converteParaVO(Recurso recurso){
      RecursoVO vo = null;
      if(null != recurso) {
         vo = RecursoVO.builder()
            .id(recurso.getId())
            .descricao(recurso.getDescricao())
            .totalDeVotos(recurso.getTotalDeVotos())
            .build();
      }
      return vo;
   }
   
   public static Recurso converteParaEntidade(RecursoVO vo){
      Recurso entidade = null;
      if(null != vo) {
         entidade = Recurso.builder()
            .id(vo.getId())
            .descricao(vo.getDescricao())
            .build();
      }
      return entidade;
   }
}
