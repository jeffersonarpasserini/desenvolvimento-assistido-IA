package br.edu.fatec.catalogo.product;

import br.edu.fatec.catalogo.common.ApiExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional   // desfaz os dados após cada teste
class ProductApiIT {

    @Autowired MockMvc mvc;

    @Test
    @DisplayName("Scenario: Cadastro válido")
    void createReturns201WithLocation() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"Caneta azul","sku":"CAN-001","price":2.50,"stock":100}
                    """))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", matchesPattern(".*/api/products/\\d+")))
           .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @DisplayName("Scenario: Exclusão de produto existente")
    void deleteThenGetReturns404() throws Exception {
        String location = mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"Lápis","sku":"LAP-001","price":1.20,"stock":5}
                    """))
            .andReturn().getResponse().getHeader("Location");

        mvc.perform(delete(location)).andExpect(status().isNoContent());
        mvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Scenario: Produto existente")
    void getExistingReturns200() throws Exception {
        String location = criar("Borracha", "BOR-001", "0.90", 30);
        mvc.perform(get(location))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.sku").value("BOR-001"));
    }

    @Test
    @DisplayName("Scenario: Listagem com itens")
    void listIsOrderedByName() throws Exception {
        criar("Régua", "REG-001", "3.00", 10);
        criar("Apontador", "APO-001", "1.50", 10);
        mvc.perform(get("/api/products"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].name").value("Apontador"))
           .andExpect(jsonPath("$[1].name").value("Régua"));
    }

    @Test
    @DisplayName("Scenario: Atualização válida")
    void updateKeepsSku() throws Exception {
        String location = criar("Caderno", "CAD-001", "12.00", 5);
        mvc.perform(put(location).contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"Caderno 96 folhas","price":14.00,"stock":8}
                    """))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("Caderno 96 folhas"))
           .andExpect(jsonPath("$.sku").value("CAD-001"));
    }

    private String criar(String nome, String sku, String preco, int estoque) throws Exception {
        return mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"%s","sku":"%s","price":%s,"stock":%d}
                    """.formatted(nome, sku, preco, estoque)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getHeader("Location");
    }
}
