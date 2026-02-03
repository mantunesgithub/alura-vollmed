package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor  //construtor sem parametro, para jpa
@AllArgsConstructor // construtor todos atributos
@EqualsAndHashCode(of = "id")  //para gerar o hashcode em cima do id e não em todos campos
public class Endereco {

    private String logradouro;
    private String bairro;
    private String cep;
    private String complemento;
    private String numero;
    private String uf;
    private String cidade;

    public Endereco(DadosEndereco endereco) {
        this.logradouro = endereco.logradouro();
        this.numero = endereco.numero();
        this.bairro = endereco.bairro();
        this.cidade = endereco.cidade();
        this.cep = endereco.cep();
        this.complemento = endereco.complemento();
        this.uf = endereco.uf();
    }

    public void atualizarInformacoes(DadosEndereco dados)  {
        this.logradouro = dados.logradouro();
        this.numero = dados.numero();
        this.bairro = dados.bairro();
        this.cidade = dados.cidade();
        this.cep = dados.cep();
        this.complemento = dados.complemento();
        this.uf = dados.uf();

    }
}
