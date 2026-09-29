package br.edu.fatec.catalogo.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 40)  String sku,
        @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal price,  // Scenario: Preço inválido
        @NotNull @PositiveOrZero Integer stock) { }
