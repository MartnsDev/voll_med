# Vollmed API - Frontend

Este README explica como configurar e rodar o **frontend da Vollmed API**, desenvolvido com **HTML, CSS e JavaScript**, consumindo a API do backend.

---

## 🔹 Pré-requisitos

Antes de começar, verifique se você possui:  
- **Navegador moderno** (Chrome, Edge, Firefox, etc.)  
- **Servidor local** para servir os arquivos HTML (opcional, mas recomendado)  
  - Exemplo: **Live Server** no VS Code  
- **Backend rodando** em **http://localhost:8080** (conforme README do backend)

---

## 🔹 Estrutura do Frontend

A pasta frontend contém:  

```
frontend/
│
├── public/ → páginas HTML (Medicos, Pacientes, Consultas...)
├── assets/
│ ├── css/ → arquivos de estilo
│ ├── js/ → scripts JavaScript
│
└── dashboard.html → Pagina Inicial   
└── index.html → página inicial (login/Cadastro)
```

---

## 🔹 Configuração da Conexão com Backend

No arquivo `assets/js/auth.js ou assets/js/auth.js` (ou equivalente), configure a URL da API:

```javascript
// URL base do backend
const API_URL = "http://localhost:8080";

🔹 Obs: Se você estiver rodando o backend em outro host ou porta, altere aqui.

Todos os scripts de requisições (fetch ou axios) devem usar API_URL para consumir os endpoints da API.
```
🔹 Rodando o Frontend
Opção 1: Abrir HTML diretamente
Abra qualquer arquivo .html no navegador

Funciona, mas algumas funções podem ser limitadas por CORS

Opção 2: Usando Live Server (recomendado)
Abra a pasta do frontend no VS Code

Clique com o botão direito no index.html → "Open with Live Server"

O navegador abrirá com a URL local (ex: http://127.0.0.1:5500/index.html)

Certifique-se que o backend está rodando e que o CORS está configurado

🔹 Testando Funcionalidades
Login de usuário → POST /usuarios/login

Cadastro de usuário → POST /usuarios

Listagem de médicos → GET /medicos

Listagem de pacientes → GET /pacientes

Agendamento de consultas → POST /consultas

Todos os formulários e botões do frontend estão configurados para consumir a API usando API_URL.

🔹 Observações
Certifique-se de que o backend esteja rodando antes de acessar o frontend

Caso haja erro de CORS, verifique a configuração do CorsConfig no backend

Para desenvolvimento rápido, o Live Server do VS Code é a forma mais prática de testar
👨‍💻 Autor

Matheus Martins

Email: 
```
mtz.martinss03@gmail.com
```
LinkedIn: 
```
linkedin.com/in/matheusmartnsdev/
```
