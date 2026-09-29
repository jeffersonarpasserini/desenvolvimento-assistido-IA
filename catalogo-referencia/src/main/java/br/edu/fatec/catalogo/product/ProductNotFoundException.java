package br.edu.fatec.catalogo.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) { super("Produto " + id + " não encontrado"); }
}
