# Vollmed API - Backend

Este README explica como configurar e rodar o **backend da Vollmed API** desenvolvido em **Java 17 + Spring Boot**.  

---

## 🔹 Pré-requisitos

Antes de começar, verifique se você possui:  
- **Java 17** instalado  
- **Maven** instalado  
- **Banco de dados MySQL** (ou compatível) rodando localmente ou em servidor  
- IDE opcional (IntelliJ, VS Code, Eclipse) para facilitar o desenvolvimento  

---

## 🔹 Configuração do Banco de Dados

1. Crie um banco chamado `vollmed_api`.  
2. Configure um usuário com permissões de leitura e escrita.  
3. Crie um arquivo `.env` ou configure variáveis de ambiente com as informações do banco:

```Aplication.Properties
DATASOURCE_URL=jdbc:mysql://localhost:3306/vollmed_api
DATASOURCE_USERNAME=root
DATASOURCE_PASSWORD=sua_senha
```
🔹 Obs: O JWT_SECRET é usado para assinar tokens de autenticação.

🔹 Rodando a Aplicação

Dentro da pasta backend:

# Baixar dependências e gerar o build
mvn clean install

# Rodar a aplicação
java -jar target/api.jar


A API estará disponível em: http://localhost:8080/**

🔹 Configuração de Perfis

O projeto possui diferentes profiles para ambientes:

application-dev.properties → ambiente de desenvolvimento

application-prod.properties → ambiente de produção

Você pode alternar o profile usando:

# Rodando com profile dev
java -jar target/api.jar --spring.profiles.active=dev

🔹 Testes Automatizados

Repository Tests → executados com banco real em profile isolado

Controller Tests → executados com MockMvc + JacksonTester

Execute todos os testes com:

mvn test

---
🔹 Configuração de CORS

Para permitir que o frontend acesse a API sem problemas de CORS, adicione a seguinte configuração:

// src/main/java/br/com/vollmed/config/CorsConfig.java
package br.com.vollmed.config;

```
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
                registry.addMapping("/**")        // Permite todas as rotas
                        .allowedOrigins("http://localhost:5500") // Endereço do frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
```

🔹 Obs: Substitua "http://localhost:5500" pelo endereço real do seu frontend, se necessário.

🔹 Endpoints Importantes

Documentação interativa com Swagger:

👉 http://localhost:8080/swagger-ui/index.html

Exemplos de endpoints principais:

Recurso	Método	Endpoint
Logar Usuario   	POST /login
Cadastrar Usuario	POST	/cadastrar
Médicos	GET/POST	/medicos
Pacientes	GET/POST	/pacientes
Consultas	GET/POST/PUT/DELETE	/consultas
<br>

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

