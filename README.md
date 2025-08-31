# Vollmed_api
![Vollmed API](https://github.com/MartnsDev/voll_med/blob/6f6e0b42ac81166926e78a99d81a652df698e87e/Vollmed_api.png?raw=true)
Projeto desenvolvido durante a formação de Spring Boot na Alura, onde praticamos e aprofundamos diversos recursos do framework no desenvolvimento de APIs Java. Este repositório reúne exercícios e implementações feitas ao longo do curso, incluindo funcionalidades, testes e documentação da API.

## Sobre o Projeto

A Vollmed_API é uma aplicação de gerenciamento de consultas médicas, que permite agendamento, cancelamento e listagem de consultas, além de demonstrar boas práticas de arquitetura e padrões de desenvolvimento em Java com Spring Boot.
Funcionalidades Praticadas
Controllers e Services: Implementação de regras de negócio separadas em classes Service, mantendo o código organizado e seguindo princípios do SOLID.
Validações: Criação de validadores customizados para evitar que a classe Service fique sobrecarregada, aplicando injeção de dependência e polimorfismo.
Documentação de API: Integração com SpringDoc e Swagger, permitindo simular requisições, adicionar cabeçalhos JWT e fornecer documentação acessível para equipes de front-end e mobile.

## Testes Automatizados:

- Testes de Repository usando banco de dados real (MySQL) em ambiente isolado com profiles específicos.
- Testes de Controller utilizando MockMvc e JacksonTester para validar JSONs de forma unitária.
- Deploy e Configuração:
- Geração de build .jar via Maven.
- Configuração de diferentes arquivos .properties para ambientes de desenvolvimento e produção.
- Uso de variáveis de ambiente para proteger informações sensíveis.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Flyway (migrations)
- Bean Validation (validações)
- Maven (build e dependências)
- MySQL (banco de dados)
- SpringDoc + Swagger (documentação)
- JUnit, MockMvc e JacksonTester (testes)

## Aprendizados

Durante o curso, praticamos:

- Criação de uma API do zero usando Spring Initializr.
- Implementação de CRUD completo.
- Controle do histórico do banco de dados com Flyway.
- Boas práticas de arquitetura e manutenção de código limpo.
- Aplicação de testes automatizados em diferentes camadas da aplicação.
- Preparação da aplicação para deploy em produção com perfis e variáveis de ambiente.

