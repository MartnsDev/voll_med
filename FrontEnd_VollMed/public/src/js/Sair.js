// ================================================
// Função genérica para POST com token
// ================================================
async function postData(url = '', data = {}) {
    const token = localStorage.getItem("token");
    if (!token) throw new Error("Usuário não está logado");

    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(data)
    });
}

// ================================================
// Função de tratamento de respostas
// ================================================
async function handleResponse(res) {
    const text = await res.text(); // lê o body uma vez
    let data;
    try {
        data = JSON.parse(text);
    } catch {
        data = { mensagem: text || "Erro desconhecido" };
    }
    return { ok: res.ok, data };
}

// ================================================
// Inicializar dashboard: menu + logout
// ================================================
document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    const userNome = localStorage.getItem("userNome");
    const userEmail = localStorage.getItem("userEmail");

    if (!token) {
        alert("Você precisa estar logado!");
        window.location.href = "/index.html";
        return;
    }

    const nomeEl = document.getElementById("userNome");
    const emailEl = document.getElementById("userEmail");
    if (nomeEl) nomeEl.textContent = userNome || "Usuário";
    if (emailEl) emailEl.textContent = userEmail || "";

    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            localStorage.removeItem("token");
            localStorage.removeItem("userNome");
            localStorage.removeItem("userEmail");
            window.location.href = "/index.html";
        });
    }
});

