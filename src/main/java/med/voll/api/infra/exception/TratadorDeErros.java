package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
    -Será desviado os erros retornados pelo spring
    -Para Spring carregar essa classe precisa ter uma anotação, sem isso é só uma classe Java
    -Colocar em cima do método o tipo de exception
    -Esse tipo de classe evita colocar try/cath em cima dos controllers
 */
@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(EntityNotFoundException.class)                //Spring retorna codigo=500
    public ResponseEntity TratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)        //Spring retorna codigo=500
    public ResponseEntity TratarErro409() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Registro já existe ou viola uma restrição do banco");
    }

    /*
        -Spring retorna codigo=400 do bean validation quando tem algum campo invalido
        -Vamos retornar uma lista de campo e mensagem de erro, pois o spring traz uma lista de erro mas
         contem muita informação, é poluido
        -Captura a exception q foi lançada e depois devolve no corpo da mensagem. Para formatar as mensagem
         vamos criar um DTO para converter a lista do bean validation para dto aqui na própria classe
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity TratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }
    private record DadosErroValidacao(String campo, String mensagem) {

        private DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
