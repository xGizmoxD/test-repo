package com.example.insurance.web;

import com.example.insurance.repository.PolicyTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) //  wylaczanie filltrow
class PolicyTypeControllerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;
  @Autowired PolicyTypeRepository repo;

  @BeforeEach
  void cleanDb() {
    repo.deleteAll();
  }

  @Test
  void create_returns201_whenValid() throws Exception {
    var body = """
      {"code":"CAR","name":"Car Insurance","basePremium":120.50}
      """;
    mvc.perform(post("/api/policy-types")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").exists())
      .andExpect(jsonPath("$.code").value("CAR"));
  }

  @Test
  void create_returns400_onValidationErrors() throws Exception {
    var body = """
      {"code":"","name":"","basePremium":null}
      """;
    mvc.perform(post("/api/policy-types")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"));
  }

  @Test
  void create_returns400_onDuplicateCode() throws Exception {
    var ok = """
      {"code":"CAR","name":"Car Insurance","basePremium":120.50}
      """;
    // pierwszy OK
    mvc.perform(post("/api/policy-types")
        .contentType(MediaType.APPLICATION_JSON)
        .content(ok))
      .andExpect(status().isCreated());

    // drugi taki sam -> 400
    mvc.perform(post("/api/policy-types")
        .contentType(MediaType.APPLICATION_JSON)
        .content(ok))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.error").value("BAD_REQUEST"));
  }

  @Test
  void get_returns404_whenNotFound() throws Exception {
    mvc.perform(get("/api/policy-types/{id}", 999999))
      .andExpect(status().isNotFound());
  }
}