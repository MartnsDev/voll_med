// Função para exibir mensagens
function setMessage(errorMsg, successMsg, error = "", success = "") {
    if (errorMsg) errorMsg.textContent = error;
    if (successMsg) successMsg.textContent = success;
}

// Mostrar/esconder formulários
const loginForm = document.getElementById("loginForm");
const cadastroForm = document.getElementById("cadastroForm");
const showCadastro = document.getElementById("showCadastro");
const showLogin = document.getElementById("showLogin");

showCadastro.addEventListener("click", () => {
    loginForm.classList.remove("active");
    cadastroForm.classList.add("active");
    setMessage(document.getElementById("error"), document.getElementById("success"));
});

showLogin.addEventListener("click", () => {
    cadastroForm.classList.remove("active");
    loginForm.classList.add("active");
    setMessage(document.getElementById("error"), document.getElementById("success"));
});

// LOGIN
if (loginForm) {
    loginForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const login = document.getElementById("login").value;
        const senha = document.getElementById("senha").value;
        const errorMsg = document.getElementById("error");
        const successMsg = document.getElementById("success");

        setMessage(errorMsg, successMsg);

        try {
            const response = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ login, senha })
            });

            let data;
            try { 
                data = await response.json(); 
            } catch { 
                data = { mensagem: "Email ou Senha Invalidos" }; 
            }

            if (!response.ok) {
                throw new Error(data.mensagem || "Login ou senha inválidos");
            }

            // Salvar token e dados do usuário
            localStorage.setItem("token", data.token);
            localStorage.setItem("userNome", data.nome || login);
            localStorage.setItem("userEmail", data.email || "");

            setMessage(errorMsg, successMsg, "", "Login realizado com sucesso!");
            window.location.href = "dashboard.html";

        } catch (error) {
            setMessage(errorMsg, successMsg, error.message);
        }
    });
}

// CADASTRO
if (cadastroForm) {
    cadastroForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const login = document.getElementById("novoLogin").value;
        const senha = document.getElementById("novaSenha").value;
        const errorMsg = document.getElementById("error");
        const successMsg = document.getElementById("success");

        setMessage(errorMsg, successMsg);

        try {
            const response = await fetch("http://localhost:8080/cadastro", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ login, senha })
            });

            let data;
            try { 
                data = await response.json(); 
            } catch { 
                data = { mensagem: "Erro desconhecido" }; 
            }

            if (!response.ok) {
                throw new Error(data.mensagem || "Erro no cadastro");
            }

            // Salvar token e dados do usuário
            localStorage.setItem("token", data.token);
            localStorage.setItem("userNome", data.nome || login);
            localStorage.setItem("userEmail", data.email || "");

            setMessage(errorMsg, successMsg, "", "Cadastro realizado com sucesso!");
            window.location.href = "dashboard.html";

        } catch (error) {
            setMessage(errorMsg, successMsg, error.message);
        }
    });
}
