package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;

public record DadosListagemPaciente (Long id, String nome, String email, String cpf, String logradouro) {

    public DadosListagemPaciente (Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(),
        paciente.getEndereco().getLogradouro());
    }
}
