package com.example.insurance.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 🔹 Błędy walidacji (@Valid)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
    List<Map<String, String>> details = ex.getBindingResult().getFieldErrors().stream()
        .map(fe -> Map.of("field", fe.getField(), "message", fe.getDefaultMessage()))
        .toList();

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", Instant.now().toString());
    body.put("status", 400);
    body.put("error", "VALIDATION_ERROR");
    body.put("details", details);

    return ResponseEntity.badRequest().body(body);
  }

  // 🔹 Nieprawidłowe dane biznesowe (np. duplikaty kodu)
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
    Map<String, Object> body = Map.of(
        "timestamp", Instant.now().toString(),
        "status", 400,
        "error", "BAD_REQUEST",
        "message", ex.getMessage(),
        "path", req.getRequestURI()
    );
    return ResponseEntity.badRequest().body(body);
  }

  // 🔹 Zasób nie znaleziony (np. 404)
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(NoSuchElementException ex, HttpServletRequest req) {
    Map<String, Object> body = Map.of(
        "timestamp", Instant.now().toString(),
        "status", 404,
        "error", "NOT_FOUND",
        "message", ex.getMessage(),
        "path", req.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  // 🔹 Wszystkie inne wyjątki (np. błędy systemowe)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex, HttpServletRequest req) {
    Map<String, Object> body = Map.of(
        "timestamp", Instant.now().toString(),
        "status", 500,
        "error", "INTERNAL_SERVER_ERROR",
        "message", ex.getMessage(),
        "path", req.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}