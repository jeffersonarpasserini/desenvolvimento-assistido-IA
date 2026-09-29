# Catálogo de Produtos — repositório de referência

Implementação de referência do **[Módulo 7 — OpenSpec](../tutorial/modulo-7-openspec.md)** do curso *Engenharia de Software com Programação Assistida por IA*. É o estado do projeto depois da mudança `add-product-catalog` percorrer o ciclo completo: propor, revisar, aplicar e arquivar.

Use para comparar com o seu projeto, não para copiar: o valor do módulo está em percorrer o ciclo com o agente.

## Stack

Java 21, Spring Boot 3.5.6 (Web, Data JPA, Validation), Flyway, H2 (desenvolvimento e testes), PostgreSQL (perfil `prod`), OpenSpec 1.13.2.

## Como executar

```bash
./mvnw verify                          # compila, roda testes unitários (Surefire) e de integração (Failsafe)
python3 scripts/check_traceability.py  # confere se cada cenário da spec tem teste
npx @fission-ai/openspec@1.13.2 validate --all --strict
./mvnw spring-boot:run                 # API em http://localhost:8080/api/products
```

## O que há aqui

| Caminho | Conteúdo | Seção do Módulo 7 |
| --- | --- | --- |
| `openspec/config.yaml` | Contexto e regras por artefato | 3.1 |
| `openspec/specs/product-catalog/spec.md` | Spec vigente, gerada pelo `openspec archive` | 3.7 |
| `openspec/changes/archive/2026-09-27-add-product-catalog/` | Proposta, delta, design e tarefas arquivados | 3.3 a 3.6 |
| `.agents/skills/openspec-*` | Skills geradas por `openspec init --tools agents` | 6.5 |
| `src/main/java/br/edu/fatec/catalogo/` | Entidade, repositório, DTOs, serviço, controller, tratamento de erros | 11.5 a 11.9 |
| `src/main/resources/` | `application.yml` e a migração `V1__create_product.sql` | 11.3 e 11.4 |
| `src/test/java/` | Testes por camada, um por cenário da spec | 11.10 |
| `scripts/check_traceability.py` | Rastreabilidade cenário → teste | 12.3 |
| `.github/workflows/openspec.yml` | Portões de CI | 14 |

## Resultado verificado (27/09/2026, OpenJDK 21.0.10, Maven 3.9.13)

- `./mvnw verify`: 10 testes, 0 falhas (`ProductServiceTest` 2, `ProductControllerTest` 2, `ProductRepositoryTest` 1, `ProductApiIT` 5).
- `check_traceability.py`: 8/8 cenários com teste.
- `openspec validate --all --strict`: 1 spec válida.

O workflow de CI não foi executado no GitHub Actions; os mesmos comandos foram executados localmente.

## Diferenças em relação ao texto do Módulo 7

- **Três testes a mais em `ProductApiIT`** (cenários "Produto existente", "Listagem com itens" e "Atualização válida"). O módulo mostra uma amostra dos testes; aqui todos os cenários da spec têm prova, como pede a tarefa 5.1.
- **`maven-failsafe-plugin` no `pom.xml`**, para que as classes `*IT` rodem no `./mvnw verify` (recomendação da seção 11.10).
- **Imports e a classe `CatalogoApplication`**, que o texto omite para economizar espaço.

## O que não está aqui

O kit de contexto dos Módulos 1 a 6 (`.agents/AGENTS.md`, rules e skills de terceiros) não está incluído: cada aluno monta o seu seguindo os módulos. Só estão as skills geradas pelo próprio OpenSpec.
