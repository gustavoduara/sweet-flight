# Sweet Flight - Controle de Vendas de Passagens Aéreas

3ª Avaliação (Situação de Aprendizagem) de Programação Orientada a Objetos.
IFSC Lages, Ciência da Computação, GCC2, 2026/2. Professor Edinilson da Silva Vida.

## Integrantes (Grupo 5)

- Daniel José Palumbo Rodrigues
- Gustavo Duara
- Mateus Munaretto Fogaça de Almeida
- Matheus Camilo Belli
- Murilo Sander de Lima Cruz

## Requisitos

- JDK 17 ou superior (testado com o JDK 21)
- Classe de entrada: `br.edu.ifsc.sweetflight.Main`

## Como compilar e executar

Os comandos são executados na pasta do projeto.

Windows (PowerShell):

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out br.edu.ifsc.sweetflight.Main
```

Linux ou macOS:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out br.edu.ifsc.sweetflight.Main
```

No NetBeans: **File > Open Project**, escolher a pasta e executar a classe `Main`.

## Organização

| Pacote / classe | Responsabilidade |
|---|---|
| `Main` | Inicia o programa |
| `model.Voo` | Dados do voo e controle de assentos |
| `model.Passageiro` | Dados do passageiro |
| `model.Venda` | Liga voo e passageiro, guarda valor e situação |
| `model.SituacaoVenda` | Confirmada ou cancelada |
| `service.CentralVendas` | Regras de negócio e coleções em memória |
| `service.ResumoVoo` | Resultado do resumo por voo |
| `service.RegraNegocioException` | Motivo de uma operação recusada |
| `ui.MenuConsole` | Menu e exibição |
| `ui.LeitorEntrada` | Leitura e conversão do que é digitado |

## Demonstração

As imagens abaixo foram capturadas de uma janela de console real do Windows, executando o programa com os dados a seguir.

| Voo | Origem | Destino | Partida | Capacidade | Tarifa |
|---|---|---|---|---|---|
| SF101 | Lages | São Paulo | 15/12/2026 08:00 | 1 | 250,00 |

| Passageiro | Nome |
|---|---|
| P1 | Gustavo Duara |
| P2 | Daniel José Palumbo Rodrigues |

O voo tem só um assento, para mostrar o que acontece quando ele lota e quando uma venda é cancelada.

### 1. Cadastrar o voo (opção 1)

![Cadastro do voo SF101](docs/img/01-cadastrar-voo.png)

### 2. Cadastrar os passageiros (opção 2)

![Cadastro dos passageiros P1 e P2](docs/img/02-cadastrar-passageiros.png)

### 3. Registrar uma venda (opção 3)

A venda de SF101 para P1 gera o código `V001`.

![Venda V001 registrada para Gustavo Duara](docs/img/03-registrar-venda.png)

### 4. Resumo do voo lotado (opção 7)

O único assento está ocupado e o total confirmado é R$ 250,00.

![Resumo do voo com 0 assentos disponíveis](docs/img/04-resumo-voo-lotado.png)

### 5. Venda recusada por falta de vagas (opção 3)

![Venda para Daniel recusada porque não há vagas](docs/img/05-venda-sem-vaga.png)

### 6. Consultar vendas por voo (opção 4)

![Consulta das vendas do voo SF101](docs/img/06-consulta-por-voo.png)

### 7. Consultar vendas por passageiro (opção 5)

P1 tem a venda `V001`; P2 não tem nenhuma.

![Consulta das vendas de P1 e de P2](docs/img/07-consulta-por-passageiro.png)

### 8. Cancelar a venda (opção 6)

O cancelamento libera o assento.

![Cancelamento da venda V001](docs/img/08-cancelar-venda.png)

### 9. Resumo e consulta depois do cancelamento

O voo volta a ter 1 assento livre, o total confirmado volta a R$ 0,00 e a venda aparece como `CANCELADA`.

![Resumo com 1 assento livre e consulta mostrando a venda cancelada](docs/img/09-resumo-apos-cancelamento.png)

### 10. Cancelar a mesma venda de novo

O sistema recusa e o resumo não muda.

![Segundo cancelamento recusado](docs/img/10-cancelar-de-novo.png)

### 11. Entradas inválidas

Uma opção que não existe no menu e uma data impossível (31/02) são recusadas sem encerrar o programa.

![Opção inválida e data inválida recusadas](docs/img/11-entradas-invalidas.png)
