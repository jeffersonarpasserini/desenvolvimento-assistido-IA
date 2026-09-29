package br.edu.fatec.catalogo.product;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final ProductService service;

    ProductController(ProductService service) { this.service = service; }

    @PostMapping
    ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req, UriComponentsBuilder uri) {
        ProductResponse created = service.create(req);
        return ResponseEntity
                .created(uri.path("/api/products/{id}").buildAndExpand(created.id()).toUri())  // 201 + Location
                .body(created);
    }

    @GetMapping("/{id}")
    ProductResponse findById(@PathVariable Long id) { return service.findById(id); }        // 200 / 404

    @GetMapping
    List<ProductResponse> findAll() { return service.findAll(); }                            // 200

    @PutMapping("/{id}")
    ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest req) {
        return service.update(id, req);                                                      // 200 / 400 / 404
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();                                           // 204
    }
}
