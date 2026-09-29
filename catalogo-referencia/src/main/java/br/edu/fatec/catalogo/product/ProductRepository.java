package br.edu.fatec.catalogo.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);   // query derivada: SELECT count(*) > 0 ... WHERE sku = ?
}
