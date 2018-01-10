package br.com.alterdata.votador.funcionario;

import lombok.*;

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
public class FuncionarioVO {
   private Long id;
   private String nome;
   private String email;
   private String senha;
   
   public boolean ehValido(){
      return null != this.nome && this.nome.length() > 0
         && null != this.email && this.email.length() > 0
         && null != this.senha && this.senha.length() > 0;
   }
   
   public static List<FuncionarioVO> converteParaListaDeVO(
      List<Funcionario> funcionarios){
      List<FuncionarioVO> vos = new ArrayList<>();
      if(null != funcionarios) {
         funcionarios.stream().forEach(funcionario -> {
            vos.add(converteParaVO(funcionario));
         });
      }
      return vos;
   }
   
   public static FuncionarioVO converteParaVO(Funcionario funcionario){
      FuncionarioVO vo = null;
      if(null != funcionario) {
         vo = FuncionarioVO.builder()
            .id(funcionario.getId())
            .nome(funcionario.getNome())
            .email(funcionario.getEmail())
            .senha(funcionario.getSenha())
            .build();
      }
      return vo;
   }
   
   public static Funcionario converteParaEntidade(FuncionarioVO vo){
      Funcionario entidade = null;
      if(null != vo) {
         entidade = Funcionario.builder()
            .id(vo.getId())
            .nome(vo.getNome())
            .email(vo.getEmail())
            .senha(vo.getSenha())
            .build();
      }
      return entidade;
   }
}
