package br.edu.fatec.catalogo.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductUpdateRequest(          // sem SKU: o contrato impede alterá-lo
        @NotBlank @Size(max = 120) String name,
        @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal price,
        @NotNull @PositiveOrZero Integer stock) { }
