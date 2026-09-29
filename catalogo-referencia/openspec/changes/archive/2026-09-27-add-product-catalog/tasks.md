## 1. Infraestrutura

- [x] 1.1 Adicionar flyway-core e flyway-database-postgresql ao pom.xml e verificar que ./mvnw -q compile passa
- [x] 1.2 Configurar application.yml (H2 em dev, ddl-auto=validate) e verificar que a aplicação sobe
- [x] 1.3 Criar db/migration/V1__create_product.sql com UNIQUE(sku) e verificar a migração no log de inicialização

## 2. Domínio e persistência

- [x] 2.1 Criar entidade Product e ProductRepository com existsBySku; verificar com ProductRepositoryTest (@DataJpaTest)

## 3. Serviço

- [x] 3.1 Implementar ProductService (create, findById, findAll, update, delete); verificar com ProductServiceTest (Mockito)
- [x] 3.2 Lançar DuplicateSkuException e ProductNotFoundException; verificar cenários "SKU duplicado" e "Produto inexistente" nos testes

## 4. API

- [x] 4.1 Criar records ProductRequest (com @NotBlank, @Positive) e ProductResponse; verificar validação em teste
- [x] 4.2 Implementar ProductController com os 5 endpoints; verificar com ProductControllerTest (@WebMvcTest)
- [x] 4.3 Implementar ApiExceptionHandler com ProblemDetail (400, 404, 409); verificar content-type application/problem+json

## 5. Verificação integrada

- [x] 5.1 Rodar ./mvnw test e confirmar que todos os cenários da spec têm teste correspondente
