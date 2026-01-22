package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
@Transactional
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

//        System.out.println("Corpo da requisicao Post Cadastrar");
//        System.out.println(dados);
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        medicoRepository.save(new Medico(dados));
    }
    /*Repository findAll devolve uma list de Medico mas temos q retornar o record, então precisa converter
      de Medicos para record e Para isso precisamos de um construtor no record DadosListagemMedicos
     */
    @GetMapping
    /* -> Pageable interface do Spring para paginação. Passa no findAll que tem uma sobrecarga para receber esse parm
       e com isso o spring monta a query com paginação. Temos trocar o retorno de List para Page, ele devolve alem da lista
       os dados de paginação
       -> Não precisa do stream e nem do tolist
       -> http://localhost:8080/medicos => Padrão é trazer conforme esta no BD
       -> PODEMOS SELECIONAR QUANTIDADE DE PAGINAS E REGISTROS e CLASSIFICAR NA URL (POSTMAN)
       http://localhost:8080/medicos?sort=especialidade&sort=crm,desc
       http://localhost:8080/medicos?size=10&page=0&sort=especialidade&sort=crm,desc
       -> Os parametros page,size,sort podem ser trocados para portugues no application.properties
       -> Se na url esta sem parametro ele traz o padrão [public Page<DadosListagemMedico> listar(Pageable paginacao) {]
       que é lista = BD. Mas se quiser podemos colocar a seleção tambem no método colocando a anotação @Pageable default
       public Page<DadosListagemMedico> listar(@PageableDefault(size=10, page=0, sort={"nome"}) { => Se não passar na URL
       o size,page,sort-> ele seguira @PageableDefault(size=10, page=0, sort={"nome"})
       -> Para exibir o comando do spring no BD, incluir no application.properties
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.format_sql=true
     */

    //public List<DadosListagemMedico> listar() {
    //  return  medicoRepository.findAll().stream().map(DadosListagemMedico::new).toList();
    public Page<DadosListagemMedico> listar(@PageableDefault(size=10, page=0, sort={"especialidade","crm"}) Pageable paginacao) {
        //return  medicoRepository.findAll(paginacao).map(DadosListagemMedico::new);
        return  medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
         Medico medico = medicoRepository.getReferenceById(dados.id());
         medico.atualizarInformacoes(dados);
            //Nao precisa metodo save pois quando no JPA em um transaction, vc carrega um entidade e muda algun campo
            // o spring atualiza a entidade
    }
    @DeleteMapping ("/{id}")
    @Transactional
    //retornar http 204 - req foi processada mas não retorna um corpo com o ResponseEntity e seus metodos estaticos
    //public void excluir (@PathVariable Long id) {
    public ResponseEntity excluir (@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();   //como esta com @transaction a jpa vai atualizar automaticamente
       return ResponseEntity.noContent().build();      //com build, o no content, cria um obj response entity
    }

}
