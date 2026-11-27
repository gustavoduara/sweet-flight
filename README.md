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

## Dados da demonstração

| Voo | Origem | Destino | Partida | Capacidade | Tarifa |
|---|---|---|---|---|---|
| SF101 | Lages | São Paulo | 15/12/2026 08:00 | 1 | 250,00 |

| Passageiro | Nome (fictício) |
|---|---|
| P1 | Ana Souza |
| P2 | Bruno Lima |

Roteiro:

1. Cadastrar o voo (opção 1) e os dois passageiros (opção 2).
2. Vender SF101 para P1 (opção 3). É gerada a venda `V001`.
3. Ver o resumo (opção 7): 0 vagas e total de R$ 250,00.
4. Tentar vender SF101 para P2: recusado por falta de vagas.
5. Consultar por voo (opção 4) e por passageiro (opção 5, com P1 e P2).
6. Cancelar `V001` (opção 6).
7. Ver o resumo: 1 vaga e total de R$ 0,00. A consulta mostra a venda como CANCELADA.
8. Cancelar `V001` de novo: recusado. O resumo continua com 1 vaga.
