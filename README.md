# Vollmed_api
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


# Como Rodar o Projeto

1. Clonar o Repositório
git clone https://github.com/MartnsDev/voll_med.git
cd Vollmed_API

## 2. Configurar Variáveis de Ambiente

Crie um arquivo .env (ou configure diretamente no terminal) com as variáveis do banco de dados:

DATASOURCE_URL=jdbc:mysql://localhost:3306/vollmed_api
DATASOURCE_USERNAME=root
DATASOURCE_PASSWORD=sua_senha

## 3. Executar o Projeto

Para rodar a aplicação em modo produção com profile e variáveis de ambiente:

java -Dspring.profiles.active=prod \
     -DDATASOURCE_URL=jdbc:mysql://localhost:3306/vollmed_api \
     -DDATASOURCE_USERNAME=root \
     -DDATASOURCE_PASSWORD=sua_senha \
     -jar target/api-0.0.1-SNAPSHOT.jar

## 4. Acessar a API

Swagger UI: http://localhost:8080/swagger-ui.html

Endpoints REST: http://localhost:8080/api/...

## Certifique-se de que o MySQL esteja rodando e que o banco vollmed_api exista antes de iniciar a aplicação.
