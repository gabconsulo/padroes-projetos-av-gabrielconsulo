# Padrões de Projeto — AV

Autor: Gabriel Ravaglio Consulo — Universidade Positivo

| Questão | Padrões | Requisitos | Por quê |
|---|---|---|---|
| 1 — Provisionamento Multinuvem | **Abstract Factory** | RNF01, RNF02, RNF03, RNF06 | `FabricaNuvem` cria a família `MaquinaVirtual` + `Armazenamento` + `Fila` de um único provedor. `Ambiente` recebe uma fábrica de fora, então não conhece provedor concreto, não usa `if`/`switch` e nunca mistura provedores. Um novo provedor exige só classes novas (e o registro em `SeletorProvedor`). |
| | **Singleton** | RNF04 | `Configuracao` lê o `config.properties` uma única vez (inicialização sob demanda, thread-safe) e é acessada por `getInstance()`. |
| | **Singleton** (thread-safe) | RNF05 | `HistoricoAuditoria` é único, acessível sem parâmetro e com métodos sincronizados: provisionamentos simultâneos não perdem nem corrompem registros, e a ordem é preservada. |
| 2 — Casa de Leilões Online | **Observer** | RNF01 | `Lote` (Subject) só conhece `Interessado` e `Licitante`. Participante, painel e auditor entram e saem em tempo de execução; um novo interessado é só uma classe nova. |
| | **Strategy** | RNF02 | Cada comissão é uma `RegraComissao`. `Categoria` informa a regra padrão (sem `if`/`switch`) e o lote troca de regra em tempo de execução (RF07). |
| | **Decorator** | RNF03, RNF04 | Cada serviço embrulha uma `Fatura` e soma descrição e custo, em qualquer combinação, ordem e quantidade. `ImpressoraFatura` trata todas as faturas igual. |

Na Questão 2 as três estruturas não se conhecem entre si (RNF05).

## Estrutura

```
questao1/
├── config.properties   (região padrão e orçamento mensal máximo)
├── diagrama/           (diagrama.puml + diagrama.png)
└── src/                (código-fonte, incluindo Main)
questao2/
├── diagrama/           (diagrama.puml + diagrama.png)
└── src/                (código-fonte, incluindo Main)
```

## Valores definidos (constantes nomeadas)

- **Questão 1:** `regiao.padrao = sa-east-1` e `orcamento.mensal.maximo = 1000.00` em `config.properties`; preços e prefixos nas constantes de cada recurso (`MaquinaVirtualAws.CUSTO_POR_HORA`, `ArmazenamentoAzure.PREFIXO`, ...); `Precos.HORAS_POR_MES = 730`.
- **Questão 2:** lances com incremento mínimo de 5% (`Lote.FATOR_LANCE_MINIMO`); comissões em `ComissaoArte`, `ComissaoVeiculos`, `ComissaoImoveis`, `ComissaoEletronicos`; serviços em `SeguroTransporte.PERCENTUAL`, `CertificadoAutenticidade.CUSTO`, `EmbalagemEspecial.CUSTO`, `EntregaExpressa.CUSTO`.

## Como executar (Java 17+, apenas biblioteca padrão)

```bash
# Questão 1 (rodar de dentro de questao1/, onde está o config.properties)
cd questao1
javac -encoding UTF-8 -d out src/*.java
java -cp out Main

# Questão 2
cd ../questao2
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```
