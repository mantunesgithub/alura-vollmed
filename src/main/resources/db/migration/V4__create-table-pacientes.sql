CREATE TABLE pacientes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    cpf VARCHAR(14) NOT NULL,
    telefone VARCHAR(20),
    logradouro VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(100),
    numero VARCHAR(20),
    uf char(2) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (cpf)
);
