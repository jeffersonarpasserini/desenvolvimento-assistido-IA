# Proposal: add-product-catalog

## Why
A disciplina precisa de uma API de referência para cadastro de produtos.
Hoje o projeto só tem o esqueleto do Spring Boot, sem nenhum endpoint.

## What Changes
- Novo recurso REST `/api/products` com criar, consultar, listar, atualizar e excluir
- Validação de entrada (nome obrigatório, preço positivo, SKU único)
- Respostas de erro padronizadas em `application/problem+json`
- Nova tabela `product` no banco

## Capabilities

### New Capabilities
- `product-catalog`: cadastro e consulta de produtos via API REST

### Modified Capabilities
- (nenhuma)

## Impact
- API: 5 endpoints novos sob `/api/products`
- Banco: migração Flyway `V1__create_product.sql`
- Dependências: adiciona `flyway-core` e `flyway-database-postgresql`
- Sem impacto em clientes existentes (primeira versão da API)
