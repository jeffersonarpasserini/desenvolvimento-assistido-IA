# Design: add-product-catalog

## Context
Projeto novo, sem persistência configurada. Motivação: ver proposal.md - Why.

## Goals / Non-Goals
**Goals:** CRUD em camadas; esquema versionado; erros RFC 9457.
**Non-Goals:** autenticação, paginação, cache (mudanças futuras).

## Decisions

### D1. DTOs como Java records
`ProductRequest` e `ProductResponse` separados da entidade `Product`.
- Alternativa: expor a entidade JPA → rejeitada (acopla API ao esquema, risco de lazy loading).

### D2. Flyway para o esquema
`spring.jpa.hibernate.ddl-auto=validate` + `db/migration/V1__create_product.sql`.
- Alternativa: `ddl-auto=update` → rejeitada (não reprodutível em produção).

### D3. SKU único no banco e no serviço
`UNIQUE (sku)` na tabela; o serviço checa `existsBySku` e lança `DuplicateSkuException` → 409.

### D4. Tratamento global de erros
`@RestControllerAdvice` estendendo `ResponseEntityExceptionHandler`, retornando `ProblemDetail`.

## Risks / Trade-offs
- [Corrida em cadastros simultâneos com mesmo SKU] → constraint UNIQUE captura; traduzir `DataIntegrityViolationException` em 409.
- [BigDecimal serializado com notação científica] → `@JsonFormat(shape = STRING)` não é necessário; usar `scale 2` na coluna.

## Migration Plan
Primeira migração; rollback = dropar a tabela `product` (sem dados em produção).
