package br.edu.fatec.catalogo.product;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 40, unique = true, updatable = false) // SKU imutável
    private String sku;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    protected Product() { }  // exigido pelo JPA

    public Product(String name, String sku, BigDecimal price, Integer stock) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.stock = stock;
    }

    /** Requirement: Atualização de produto — substitui nome, preço e estoque; SKU não muda. */
    public void update(String name, BigDecimal price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSku() { return sku; }
    public BigDecimal getPrice() { return price; }
    public Integer getStock() { return stock; }
}
