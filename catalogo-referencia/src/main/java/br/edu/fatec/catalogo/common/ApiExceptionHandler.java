package br.edu.fatec.catalogo.common;

import br.edu.fatec.catalogo.product.DuplicateSkuException;
import br.edu.fatec.catalogo.product.ProductNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)                         // 404
    ProblemDetail notFound(ProductNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Produto não encontrado");
        return pd;
    }

    @ExceptionHandler(DuplicateSkuException.class)                            // 409
    ProblemDetail conflict(DuplicateSkuException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        pd.setTitle("SKU duplicado");
        return pd;
    }

    @Override                                                                  // 400 com campo "errors"
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(fe -> fe.getField(),
                                          fe -> String.valueOf(fe.getDefaultMessage()),
                                          (a, b) -> a));
        ProblemDetail pd = ex.getBody();
        pd.setTitle("Dados inválidos");
        pd.setProperty("errors", errors);                                     // Scenario: Preço inválido
        return handleExceptionInternal(ex, pd, headers, status, request);
    }
}
