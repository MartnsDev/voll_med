const API_URL = "http://localhost:8080/pacientes";

function getToken() {
  return localStorage.getItem("token");
}

async function listarPacientes() {
  const response = await fetch(API_URL, {
    headers: {
      "Authorization": "Bearer " + getToken()
    }
  });
  const pacientes = await response.json();
  
  const lista = document.getElementById("lista");
  lista.innerHTML = "";
  pacientes.forEach(p => {
    const li = document.createElement("li");
    li.textContent = `${p.id} - ${p.nome} (${p.email})`;
    lista.appendChild(li);
  });
}

async function cadastrarPaciente() {
  const nome = document.getElementById("nome").value;
  const email = document.getElementById("email").value;

  await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + getToken()
    },
    body: JSON.stringify({ nome, email })
  });

  listarPacientes();
}
