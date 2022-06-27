package com.vr.miniautorizador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.miniautorizador.domain.cartao.Cartao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CartaoTest {

    private static final String PATH = "/cartoes";

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private Cartao cartao;

    @Before
    public void setup() {
        Cartao cartao = new Cartao();
        cartao.setSenha("1234");
        cartao.setNumeroCartao("6549873025634509");
        em.persist(cartao);
    }

    @Test
    public void criarCartao() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setSenha("1233");
        cartao.setNumeroCartao("6549873025634500");

        mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cartao)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getSaldo() throws Exception {
        mockMvc.perform(get(PATH.concat("/6549873025634509"))
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cartao)))
                .andExpect(status().isOk())
                .andReturn();
    }
}
