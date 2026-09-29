package br.edu.fatec.catalogo.product;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) { this.repository = repository; }

    @Transactional
    public ProductResponse create(ProductRequest req) {
        if (repository.existsBySku(req.sku())) {                    // Scenario: SKU duplicado
            throw new DuplicateSkuException(req.sku());
        }
        try {
            Product saved = repository.saveAndFlush(
                    new Product(req.name(), req.sku(), req.price(), req.stock()));
            return ProductResponse.from(saved);
        } catch (DataIntegrityViolationException e) {                 // design: risco de corrida
            throw new DuplicateSkuException(req.sku());
        }
    }

    public ProductResponse findById(Long id) {
        return ProductResponse.from(load(id));                        // Scenario: Produto inexistente
    }

    public List<ProductResponse> findAll() {
        return repository.findAll(Sort.by("name")).stream()          // Requirement: Listagem ordenada por nome
                .map(ProductResponse::from).toList();
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest req) {
        Product p = load(id);
        p.update(req.name(), req.price(), req.stock());               // dirty checking grava no commit
        return ProductResponse.from(p);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(load(id));
    }

    private Product load(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
}
