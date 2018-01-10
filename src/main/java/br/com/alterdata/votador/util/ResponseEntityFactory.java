package br.com.alterdata.votador.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author acmattos
 */
public class ResponseEntityFactory {
   
   /** Para evitar instanciacao da fabrica */
   private ResponseEntityFactory(){}
   
   /**
    * Cria uma entidade HTTP de resposta para entidade <T> informada.
    * @param t Entidade recem-criada.
    * @param <T> Tipo da entidade recem-criada.
    * @return Entidade HTTP de resposta, cujo status pode ser 201(CREATED) ou
    *         400 (BAD REQUEST).
    */
   public static <T> ResponseEntity<T> criar(T t){
      HttpStatus httpStatus = HttpStatus.CREATED;
      if(null == t){
         httpStatus = HttpStatus.BAD_REQUEST;
      }
      return new ResponseEntity<>(t, httpStatus);
   }
   
   /**
    * Cria uma entidade HTTP de resposta para a lista de entidades <T> informada.
    * @param t Lista de entidades encontrada.
    * @param <T> Tipo da lista de entidades encontrada.
    * @return Entidade HTTP de resposta, cujo status pode ser 200(OK), 400
    *         (BAD REQUEST) ou 404 (NOT FOUND).
    */
   public static <T extends Iterable> ResponseEntity<T> listar(T t){
      HttpStatus httpStatus = HttpStatus.OK;
      if(null == t){
         httpStatus = HttpStatus.BAD_REQUEST;
      } else if(!t.iterator().hasNext()){
         httpStatus = HttpStatus.NOT_FOUND;
      }
      return new ResponseEntity<>(t, httpStatus);
   }
   
   /**
    * Cria uma entidade HTTP de resposta para a entidade <T> informada.
    * @param t Entidades encontrada.
    * @param <T> Tipo da entidade encontrada.
    * @return Entidade HTTP de resposta, cujo status pode ser 200(OK) ou
    *         404 (NOT FOUND).
    */
   public static <T> ResponseEntity<T> listarUm(T t){
      HttpStatus httpStatus = HttpStatus.OK;
      if(null == t){
         httpStatus = HttpStatus.NOT_FOUND;
      }
      return new ResponseEntity<>(t, httpStatus);
   }
}
