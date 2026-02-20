Atividade de Matheus Rocha

## How to run (CLI)

### 1. Compile
From the project root (`ToDo app`):

```bash
javac -d out $(find src -name "*.java")
```
2. Run
```bash
java -cp out com.rocha.app.main
```
🔔 Nova Feature: Alarme de Tarefa

Agora o sistema permite adicionar um alarme opcional ao criar uma tarefa.

Como funciona:

Ao criar uma nova tarefa, o sistema pergunta se você deseja ativar um alarme.

Caso escolha "y", você deve informar a data e hora no formato:

yyyy-MM-dd HH:mm

Quando o horário definido for alcançado:

Uma mensagem será exibida no terminal indicando que o alarme foi ativado.

Um bip sonoro será emitido.

Observações

O alarme é opcional.

Caso a tarefa seja removida ou marcada como DONE, o alarme é automaticamente cancelado.

O sistema continua funcionando normalmente enquanto aguarda o disparo do alarme (execução assíncrona).