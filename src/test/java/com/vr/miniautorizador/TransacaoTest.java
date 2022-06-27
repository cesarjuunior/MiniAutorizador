package com.vr.miniautorizador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.miniautorizador.domain.cartao.Cartao;
import com.vr.miniautorizador.domain.transacao.TransacaoDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TransacaoTest {

    private static final String PATH = "/transacoes";

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setup() {
        Cartao cartao = new Cartao();
        cartao.setSenha("1235");
        cartao.setNumeroCartao("6549873025634777");
        em.persist(cartao);
    }

    @Test
    public void efetivarTransacao() throws Exception {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6549873025634777");
        transacaoDTO.setSenhaCartao("1235");
        transacaoDTO.setValor(BigDecimal.TEN);

        mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transacaoDTO)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void cartaoNaoEncontrado() throws Exception {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6549873025634555");
        transacaoDTO.setSenhaCartao("1235");
        transacaoDTO.setValor(BigDecimal.TEN);

        MvcResult result = mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transacaoDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        assertEquals("CARTAO_INEXISTENTE", Objects.requireNonNull(result.getResolvedException()).getLocalizedMessage());

    }

    @Test
    public void senhaInvalida() throws Exception {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6549873025634777");
        transacaoDTO.setSenhaCartao("5555");
        transacaoDTO.setValor(BigDecimal.TEN);

        MvcResult result = mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transacaoDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        assertEquals("SENHA_INVÁLIDA", Objects.requireNonNull(result.getResolvedException()).getLocalizedMessage());

    }

    @Test
    public void saldoInsuficiente() throws Exception {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6549873025634777");
        transacaoDTO.setSenhaCartao("1235");
        transacaoDTO.setValor(BigDecimal.valueOf(900));

        MvcResult result = mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transacaoDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        assertEquals("SALDO_INSUFICIENTE", Objects.requireNonNull(result.getResolvedException()).getLocalizedMessage());

    }

}
