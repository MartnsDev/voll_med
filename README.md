# Voll_med - API de Gestão Médica

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot para gerenciamento de um sistema médico. 

## Funcionalidades principais
- Cadastro, atualização e listagem de médicos e pacientes.
- Controle de agendamentos e consultas.
- Estrutura modular com controllers, serviços e modelos.
- Validações e tratamento de exceções integrados.
- Suporte para operações CRUD completas.

## Tecnologias usadas
- Java 17
- Spring Boot
- JPA/Hibernate
- Banco de dados relacional (ex: PostgreSQL/MySQL)
- Maven para gerenciamento de dependências

## Estrutura do projeto
- `controller/`: classes que recebem as requisições HTTP.
- `pacientes/` e outras pastas de modelo: representações das entidades.
- Configurações de segurança e persistência integradas.

## Como executar
1. Configure seu banco de dados no arquivo `application.properties`.
2. Compile e execute a aplicação com Maven:  
   ```bash
   mvn spring-boot:run
