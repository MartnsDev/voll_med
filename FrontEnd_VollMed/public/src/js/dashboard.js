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

// ================================================
// AGENDAR CONSULTA
// ================================================
document.getElementById("agendarConsultaBtn").addEventListener("click", async () => {
    const msg = document.getElementById("consultaMsg");
    msg.textContent = "";

    const idPaciente = document.getElementById("consultaIdPaciente").value;
    if (!idPaciente) {
        msg.textContent = "Informe o ID do paciente!";
        msg.className = "error";
        return;
    }

    const consulta = {
        idPaciente: parseInt(idPaciente),
        especialidade: document.getElementById("consultaEspecialidade").value,
        data: document.getElementById("consultaData").value.replace(" ", "T")
    };

    try {
        const res = await postData("http://localhost:8080/consultas", consulta);
        const { ok, data } = await handleResponse(res);

        if (ok) {
            msg.textContent = "Consulta agendada com sucesso!";
            msg.className = "success";
        } else {
            msg.textContent = data.erro || data.mensagem || "Erro ao agendar consulta.";
            msg.className = "error";
        }
    } catch (e) {
        msg.textContent = e.message || "Erro ao agendar consulta.";
        msg.className = "error";
        console.error(e);
    }
});

// ================================================
// CADASTRAR MÉDICO
// ================================================
document.getElementById("cadastrarMedicoBtn").addEventListener("click", async () => {
    const msg = document.getElementById("medicoMsg");
    msg.textContent = "";

    const medico = {
        nome: document.getElementById("medicoNome").value,
        email: document.getElementById("medicoEmail").value,
        telefone: document.getElementById("medicoTelefone").value,
        crm: document.getElementById("medicoCRM").value,
        especialidade: document.getElementById("medicoEspecialidade").value,
        endereco: {
            logradouro: document.getElementById("medicoLogradouro").value,
            bairro: document.getElementById("medicoBairro").value,
            cep: document.getElementById("medicoCEP").value,
            cidade: document.getElementById("medicoCidade").value,
            uf: document.getElementById("medicoUF").value,
            numero: document.getElementById("medicoNumero").value,
            complemento: document.getElementById("medicoComplemento").value
        }
    };

    try {
        const res = await postData("http://localhost:8080/medicos", medico);
        const { ok, data } = await handleResponse(res);

        if (ok) {
            msg.textContent = "Médico cadastrado com sucesso!";
            msg.className = "success";
        } else {
            msg.textContent = data.erro || data.mensagem || "Erro ao cadastrar médico.";
            msg.className = "error";
        }
    } catch (e) {
        msg.textContent = e.message || "Erro ao cadastrar médico.";
        msg.className = "error";
        console.error(e);
    }
});

// ================================================
// CADASTRAR PACIENTE
// ================================================
document.getElementById("cadastrarPacienteBtn").addEventListener("click", async () => {
    const msg = document.getElementById("pacienteMsg");
    msg.textContent = "";

    const paciente = {
        nome: document.getElementById("pacienteNome").value,
        email: document.getElementById("pacienteEmail").value,
        telefone: document.getElementById("pacienteTelefone").value,
        cpf: document.getElementById("pacienteCPF").value,
        endereco: {
            logradouro: document.getElementById("pacienteLogradouro").value,
            bairro: document.getElementById("pacienteBairro").value,
            cep: document.getElementById("pacienteCEP").value,
            cidade: document.getElementById("pacienteCidade").value,
            uf: document.getElementById("pacienteUF").value,
            numero: document.getElementById("pacienteNumero").value,
            complemento: document.getElementById("pacienteComplemento").value
        }
    };

    try {
        const res = await postData("http://localhost:8080/pacientes", paciente);
        const { ok, data } = await handleResponse(res);

        if (ok) {
            msg.textContent = "Paciente cadastrado com sucesso!";
            msg.className = "success";
        } else {
            msg.textContent = data.erro || data.mensagem || "Erro ao cadastrar paciente.";
            msg.className = "error";
        }
    } catch (e) {
        msg.textContent = e.message || "Erro ao cadastrar paciente.";
        msg.className = "error";
        console.error(e);
    }
});
