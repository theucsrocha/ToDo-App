var tarefasCadastradas = [];
var contadorTarefas = 0;
var tarefaEmEdicao = null;

if(window.localStorage.getItem('tarefasArmazenadas')){
    tarefasCadastradas = JSON.parse(window.localStorage.getItem('tarefasArmazenadas'));
    contadorTarefas = parseInt(window.localStorage.getItem('contadorTarefaArmazenado'));
    atualizarListagem();
}


class Task{
    constructor(nome,descricao,expiracao,prioridade,categoria,status){
        this.id = contadorTarefas++;
        this.nome = nome;
        this.descricao = descricao;
        this.expiracao = expiracao;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.status = status;
    }
}

document.getElementById("submitBtn").onclick = function(){
    let nome = document.getElementById("taskName").value;
    let descricao = document.getElementById("taskDescription").value;
    let expiracao = document.getElementById("taskExpiration").value;
    let prioridade = document.getElementById("taskPriority").value;
    let categoria = document.getElementById("taskCategory").value;
    let status = document.getElementById("taskStatus").value;

    if (tarefaEmEdicao === null) {
        // CRIAR NOVA TAREFA
        let novaTask = new Task(nome,descricao,expiracao,prioridade,categoria,status);
        tarefasCadastradas.push(novaTask);
    } else {
        // ATUALIZAR TAREFA EXISTENTE
        const indice = tarefasCadastradas.findIndex((tarefa) => tarefa.id === tarefaEmEdicao);
        if (indice !== -1) {
            tarefasCadastradas[indice].nome = nome;
            tarefasCadastradas[indice].descricao = descricao;
            tarefasCadastradas[indice].expiracao = expiracao;
            tarefasCadastradas[indice].prioridade = prioridade;
            tarefasCadastradas[indice].categoria = categoria;
            tarefasCadastradas[indice].status = status;
        }
        tarefaEmEdicao = null;  // Volta ao estado normal
    }
    
    limparFormulario();
    atualizarListagem();
    window.localStorage.setItem('tarefasArmazenadas',JSON.stringify(tarefasCadastradas));
    window.localStorage.setItem('contadorTarefaArmazenado',contadorTarefas);
    
}

function atualizarListagem() {
    let corpoTabela = document.getElementById("tasksBody");
    corpoTabela.innerHTML = "";

    if (tarefasCadastradas.length === 0) {
        corpoTabela.innerHTML = '<tr class="empty-state"><td colspan="8">Nenhuma tarefa criada ainda</td></tr>';
        return;
    }

    for (let i = 0; i < tarefasCadastradas.length; i++) {
        corpoTabela.innerHTML +=
            "<tr>" +
                // 1. Coluna do Checkbox (Fundamental para a feature Multi-Status)
                '<td><input type="checkbox" class="task-checkbox" data-id="' + tarefasCadastradas[i].id + '"></td>' +
                
                // 2. Colunas de dados da tarefa
                "<td>" + tarefasCadastradas[i].nome + "</td>" +
                "<td>" + tarefasCadastradas[i].descricao + "</td>" +
                "<td>" + tarefasCadastradas[i].expiracao + "</td>" +
                "<td>" + tarefasCadastradas[i].prioridade + "</td>" +
                "<td>" + tarefasCadastradas[i].categoria + "</td>" +
                "<td>" + tarefasCadastradas[i].status + "</td>" +
                
                // 3. Coluna de Ações (Atualizar e Remover)
                '<td class="actions-cell">' +
                    '<button type="button" class="btn-update" title="Atualizar" data-id="' + tarefasCadastradas[i].id + '">↻</button>' +
                    '<button type="button" class="btn-remove" title="Remover" data-id="' + tarefasCadastradas[i].id + '">✕</button>' +
                "</td>" +
            "</tr>";
    }
}

document.getElementById("tasksBody").addEventListener("click", function(evento) {
    // REMOVER
    if (evento.target.classList.contains("btn-remove")) {
        const idTarefa = Number(evento.target.dataset.id);
        const indice = tarefasCadastradas.findIndex((tarefa) => tarefa.id === idTarefa);

        if (indice !== -1) {
            tarefasCadastradas.splice(indice, 1);
            atualizarListagem();
        }
    }

    // ATUALIZAR (↻)
    if (evento.target.classList.contains("btn-update")) {
        const idTarefa = Number(evento.target.dataset.id);
        const tarefa = tarefasCadastradas.find((t) => t.id === idTarefa);

        if (tarefa) {
            // Preenche o formulário com os dados da tarefa
            document.getElementById("taskName").value = tarefa.nome;
            document.getElementById("taskDescription").value = tarefa.descricao;
            document.getElementById("taskExpiration").value = tarefa.expiracao;
            document.getElementById("taskPriority").value = tarefa.prioridade;
            document.getElementById("taskCategory").value = tarefa.categoria;
            document.getElementById("taskStatus").value = tarefa.status;

            // Muda a interface para modo de edição
            document.getElementById("formTitle").innerText = "Editar Tarefa";
            document.getElementById("submitBtn").innerText = "Salvar Alterações";
            document.getElementById("cancelBtn").style.display = "inline-block";

            // Marca qual tarefa está sendo editada
            tarefaEmEdicao = idTarefa;
        }
    }
});

function limparFormulario(){
    document.getElementById("taskName").value = "";
    document.getElementById("taskDescription").value = "";
    document.getElementById("taskExpiration").value = "";
    document.getElementById("taskPriority").value = "";
    document.getElementById("taskCategory").value = "";
    document.getElementById("taskStatus").value = "";
    document.getElementById("formTitle").innerText = "Nova Tarefa";
    document.getElementById("submitBtn").innerText = "Adicionar Tarefa";
    document.getElementById("cancelBtn").style.display = "none";
    tarefaEmEdicao = null;
}

document.getElementById("cancelBtn").onclick = function(){
    limparFormulario();
}

// Função para mudar o status de várias tarefas ao mesmo tempo
document.getElementById("executarMultiStatus").onclick = function() {
    // 1. Pega o status que o usuário escolheu no select
    const novoStatus = document.getElementById("multiStatusSelect").value;
    
    if (!novoStatus) {
        alert("Por favor, selecione um status para aplicar.");
        return;
    }

    // 2. Filtra apenas os checkboxes que estão marcados (:checked)
    const checkboxesMarcados = document.querySelectorAll('.task-checkbox:checked');
    
    if (checkboxesMarcados.length === 0) {
        alert("Selecione pelo menos uma tarefa na lista.");
        return;
    }

    // 3. Itera sobre os selecionados e atualiza o array de tarefas
    let tarefasAtualizadas = 0;
    checkboxesMarcados.forEach(checkbox => {
        const idParaMudar = Number(checkbox.dataset.id);
        const tarefa = tarefasCadastradas.find(t => t.id === idParaMudar);
        if (tarefa) {
            console.log("Tarefa encontrada:", tarefa.nome, "- Status anterior:", tarefa.status);
            tarefa.status = novoStatus;
            tarefasAtualizadas++;
        }
    });


    // 4. Salva a nova lista no LocalStorage e atualiza a tela
    window.localStorage.setItem('tarefasArmazenadas', JSON.stringify(tarefasCadastradas));
    atualizarListagem();
    
    alert(tarefasAtualizadas + " tarefa(s) atualizada(s) para o status: " + novoStatus);
};