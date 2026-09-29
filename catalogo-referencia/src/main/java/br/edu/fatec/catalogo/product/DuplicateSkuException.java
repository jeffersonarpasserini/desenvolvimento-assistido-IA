package br.edu.fatec.catalogo.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class DuplicateSkuException extends RuntimeException {
    public DuplicateSkuException(String sku) { super("Já existe produto com SKU " + sku); }
}
