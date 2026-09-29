package br.edu.fatec.catalogo.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String sku, BigDecimal price, Integer stock) {
    static ProductResponse from(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getSku(), p.getPrice(), p.getStock());
    }
}
