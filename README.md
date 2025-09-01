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
- Html, Css e Java Script para o FrontEnd

## Aprendizados

Durante o curso, praticamos:

- Criação de uma API do zero usando Spring Initializr.
- Implementação de CRUD completo.
- Controle do histórico do banco de dados com Flyway.
- Boas práticas de arquitetura e manutenção de código limpo (Para Backend).
- Aplicação de testes automatizados em diferentes camadas da aplicação.
- Preparação da aplicação para deploy em produção com perfis e variáveis de ambiente.

- ### 🔄 Endpoints principais (Swagger)

A API possui documentação interativa disponível via Swagger:

> 🔗 Acesse: [http://localhost:8080/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

#### 🧾 Requisições:
- `POST`
- `GET`   
- `GET` 
- `PUT`
- `DELETE`
- 
---

### ▶️ Como executar o projeto

#### 1. Rodar a aplicação

No terminal (ou pela IDE):
```bash
Terminal da IDLE:
ls target/
java -jar ./target/api.jar

Terminal:
cd (Pasta do projeto)
ls target/
java -jar ./target/api.jar
```
---
### ✅ Status atual
- [x] Login e Cadastro de Usuarios

- [X] Cadastro de Médicos e Pacientes

- [X] Atualizar Dados de Médicos e Pacientes

- [X] Agendamento De Consultas

- [X] Validações completas e tratamento

- [X] Testes automatizados


## Arquitetura e Estrutura do Projeto

O projeto segue a arquitetura **MVC (Model-View-Controller)**, garantindo separação clara das responsabilidades e facilitando manutenção e escalabilidade.

### Swagger
![image]()

### Organização dos Pacotes



### Descrição dos Componentes




---
###### 👨‍💻 Autor

Feito por Matheus Martins durante o curso de Spring Boot Web Developer na Alura.


---

## Contato

Para dúvidas ou sugestões, entre em contato:  
- mtz,martinss03@gmail.com
- https://www.linkedin.com/in/martnsdeveloper/

---


  ## Como Instalar?
  Readmes Contém a Explicação.
  - BackEnd_VollMed
  - FrontEnd_VollMed
