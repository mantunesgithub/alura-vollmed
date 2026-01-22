package med.voll.api.medico;

    // Construtor recebe entidade Medico para Spring converter os dados Medicos para dto DLMedicos.
    // Vamos chamar o proprio construtor do record
public record DadosListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public DadosListagemMedico(Medico medico) {
        this(medico.getId(),medico.getNome(), medico.getEmail(), medico.getCrm(),medico.getEspecialidade());
    }
}
