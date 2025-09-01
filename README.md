# 🏥 Vollmed API  

![Vollmed API](https://github.com/MartnsDev/voll_med/blob/6f6e0b42ac81166926e78a99d81a652df698e87e/Vollmed_api.png?raw=true)  

Aplicação desenvolvida durante a **formação Spring Boot da Alura**, com foco no desenvolvimento de **APIs REST em Java**.  
O projeto simula o gerenciamento de uma clínica médica (**cadastro de médicos, pacientes e agendamento de consultas**), aplicando **boas práticas de arquitetura, validações, autenticação e testes automatizados**.  

---

## 📌 Sobre o Projeto  

A **Vollmed API** permite:  
✅ Cadastro e atualização de médicos e pacientes  
✅ Agendamento e cancelamento de consultas  
✅ Autenticação com **JWT**  
✅ Documentação interativa com **Swagger**  
✅ Testes automatizados em múltiplas camadas  

---

## 🚀 Funcionalidades  

- **Controllers e Services** → organização das regras de negócio (princípios SOLID)  
- **Validações customizadas** → separação em validadores dedicados  
- **Segurança** → autenticação com Spring Security + JWT  
- **Documentação** → integração com SpringDoc e Swagger  
- **Testes** → Repository, Controller e JSON com MockMvc + JacksonTester  

---

## 🛠️ Tecnologias Utilizadas  

- ☕ **Java 17**  
- 🍃 **Spring Boot**  
- 🗄️ **Spring Data JPA + MySQL**  
- 🔄 **Flyway** (migrations)  
- ✅ **Bean Validation**  
- 📦 **Maven** (build e dependências)  
- 📚 **SpringDoc + Swagger** (documentação)  
- 🧪 **JUnit, MockMvc e JacksonTester** (testes)  
- 🎨 **HTML, CSS e JavaScript** (frontend simples)  

---

## 📖 Aprendizados  

Durante o desenvolvimento foram praticados:  
- Criação de uma API REST do zero com Spring Initializr  
- CRUD completo (Create, Read, Update, Delete)  
- Controle de versão do banco com Flyway  
- Boas práticas de **Clean Code e SOLID**  
- Escrita de **testes automatizados** em diferentes camadas  
- Configuração para **deploy em produção** com perfis separados  

---

## 🔄 Endpoints principais  

📌 A documentação interativa está disponível em:  
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
![Documentação Swagger](https://github.com/MartnsDev/voll_med/raw/2e8bb322edc0df4218b248bf424fcbb58672952b/documenta%C3%A7%C3%A3o-swagger.png)

Principais métodos suportados:  
- `POST` → Criar recursos  
- `GET` → Consultar dados  
- `PUT` → Atualizar registros  
- `DELETE` → Remover registros  

---

## ▶️ Como executar o projeto  

```bash
# Clonar o repositório
git clone https://github.com/MartnsDev/voll_med.git
cd voll_med

# Gerar o build
mvn clean install

# Rodar a aplicação
java -jar target/api.jar
```
✅ Status Atual

 Login e cadastro de usuários

 Cadastro e atualização de médicos e pacientes

 Agendamento e cancelamento de consultas

 Validações completas e tratamento de erros

 Testes automatizados implementados

 🏗️ Arquitetura

O projeto segue a arquitetura MVC (Model-View-Controller):
```
src/main/java/br/com/vollmed
│
├── controller    → entrada de requisições REST
├── service       → regras de negócio
├── repository    → comunicação com o banco (JPA)
├── entity/model  → entidades do domínio
├── dto           → objetos de transferência de dados
└── config        → configurações (segurança, CORS, etc.)
```

👨‍💻 Autor

Feito por Matheus Martins durante a formação Spring Boot Web Developer - Alura.
```
📬 Contato

📧 Email: mtz.martinss03@gmail.com

💼 LinkedIn: linkedin.com/in/martnsdeveloper
```
📦 Como Instalar

Este repositório contém:
```
BackEnd_VollMed

FrontEnd_VollMed
```
Consulte os READMEs de cada módulo para instruções detalhadas.
