CREATE TABLE pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(255),
    cpf VARCHAR(255),
    logradouro VARCHAR(255),
    bairro VARCHAR(255),
    cep VARCHAR(20),
    numero VARCHAR(20),
    complemento VARCHAR(255),
    cidade VARCHAR(255),
    uf VARCHAR(2)
);
