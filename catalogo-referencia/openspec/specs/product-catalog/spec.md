# product-catalog Specification

## Purpose
Permite cadastrar, consultar, atualizar e excluir produtos do catálogo
por meio de uma API REST pública em JSON.

## Requirements

### Requirement: Cadastro de produto
O sistema SHALL criar um produto a partir de nome, SKU, preço e quantidade em estoque,
retornando o produto criado com um identificador gerado.

#### Scenario: Cadastro válido
- **GIVEN** que não existe produto com SKU "CAN-001"
- **WHEN** o cliente envia POST /api/products com nome "Caneta azul", SKU "CAN-001", preço 2.50 e estoque 100
- **THEN** o sistema responde 201 Created
- **AND** o cabeçalho Location aponta para /api/products/{id}
- **AND** o corpo contém o produto com o id gerado

#### Scenario: Preço inválido
- **WHEN** o cliente envia POST /api/products com preço 0 ou negativo
- **THEN** o sistema responde 400 Bad Request em application/problem+json
- **AND** o campo errors identifica "price" como inválido

#### Scenario: SKU duplicado
- **GIVEN** que já existe produto com SKU "CAN-001"
- **WHEN** o cliente envia POST /api/products com SKU "CAN-001"
- **THEN** o sistema responde 409 Conflict em application/problem+json

### Requirement: Consulta de produto por id
O sistema SHALL retornar os dados de um produto existente pelo seu identificador.

#### Scenario: Produto existente
- **WHEN** o cliente envia GET /api/products/{id} com id existente
- **THEN** o sistema responde 200 OK com o produto

#### Scenario: Produto inexistente
- **WHEN** o cliente envia GET /api/products/{id} com id inexistente
- **THEN** o sistema responde 404 Not Found em application/problem+json

### Requirement: Listagem de produtos
O sistema SHALL listar todos os produtos cadastrados ordenados por nome.

#### Scenario: Listagem com itens
- **WHEN** o cliente envia GET /api/products
- **THEN** o sistema responde 200 OK com um array de produtos ordenado por nome

### Requirement: Atualização de produto
O sistema SHALL substituir nome, preço e estoque de um produto existente, mantendo o SKU imutável.

#### Scenario: Atualização válida
- **WHEN** o cliente envia PUT /api/products/{id} com dados válidos
- **THEN** o sistema responde 200 OK com o produto atualizado

### Requirement: Exclusão de produto
O sistema SHALL excluir um produto existente pelo identificador.

#### Scenario: Exclusão de produto existente
- **WHEN** o cliente envia DELETE /api/products/{id} com id existente
- **THEN** o sistema responde 204 No Content
- **AND** um GET subsequente no mesmo id responde 404
