// Função genérica para POST com token
async function postData(url = '', data = {}, token = '') {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(data)
    });
}

// CADASTRAR MÉDICO
document.getElementById("cadastrarMedicoBtn").addEventListener("click", async () => {
    const msg = document.getElementById("medicoMsg");
    msg.textContent = "";

    const token = localStorage.getItem("token"); // ou de onde você estiver guardando

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
        const response = await postData("http://localhost:8080/medicos", medico, token);

        // Tenta ler JSON, mas se falhar, pega texto simples
        let data;
        try {
            data = await response.json();
        } catch {
            data = { mensagem: await response.text() };
        }

        if (response.ok) {
            msg.textContent = "Médico cadastrado com sucesso!";
            msg.className = "success";
        } else {
            msg.textContent = data.mensagem || "Erro ao cadastrar médico.";
            msg.className = "error";
        }

    } catch (e) {
        msg.textContent = "Erro ao cadastrar médico.";
        msg.className = "error";
        console.error(e);
    }
});
