# Frontend do Projeto - Guia Rápido

## Pré-requisitos
- Navegador atualizado (Chrome, Firefox, Edge).
- Backend da API rodando em `http://localhost:8080` ou URL pública (Render, Railway etc.).

## Passos para rodar o Frontend

### 1) Clonar o repositório
```bash
git clone https://github.com/seu-usuario/seu-projeto-frontend.git
cd seu-projeto-frontend
```

### 2) Abrir os arquivos HTML
- `index.html` → Tela de login
- `cadastrar.html` → Tela de cadastro
- `dashboard.html` → Painel principal

> Dica: clique com o botão direito e selecione **Abrir com → Navegador**

### 3) Rodar com servidor local (opcional, recomendado)
- No VS Code, instale a extensão **Live Server**
- Clique em **Go Live** no rodapé do VS Code
- O projeto abrirá em `http://localhost:5500`

Alternativas sem VS Code:
```bash
python -m http.server 5500
# ou
npx http-server -p 5500
```

## Conexão com a API
No frontend, configure a URL da API no JavaScript:
```javascript
const API_URL = "http://localhost:8080";
```
Se a API estiver hospedada, troque para:
```javascript
const API_URL = "https://seu-projeto.onrender.com";
```

## Testando
1. Abra `index.html`
2. Faça login ou cadastre um usuário
3. Se a API estiver rodando corretamente, os dados aparecerão no dashboard

## Configurar CORS no Spring Boot
Por padrão, navegadores bloqueiam requisições cross-origin. Crie a classe `CorsConfig.java`:
```java
package br.com.seuprojeto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5500", "https://seu-front.netlify.app")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
```

Se usar Spring Security, habilite CORS na configuração de segurança:
```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors().and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/publico/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
```
> Atenção: em produção, restrinja `allowedOrigins` aos domínios reais do seu frontend

## Exemplo de envio de dados (fetch)
```javascript
fetch("http://localhost:8080/api/usuarios", {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({ nome: "Matheus", email: "teste@teste.com" })
})
.then(res => res.json())
.then(data => console.log("Usuário cadastrado:", data))
.catch(err => console.error("Erro:", err));
```
