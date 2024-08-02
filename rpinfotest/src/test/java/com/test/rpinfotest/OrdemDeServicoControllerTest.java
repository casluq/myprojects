package com.test.rpinfotest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.test.rpinfotest.controllers.OrdemDeServicoController;
import com.test.rpinfotest.entity.Anotacao;
import com.test.rpinfotest.entity.Cliente;
import com.test.rpinfotest.entity.Equipamento;
import com.test.rpinfotest.entity.OrdemDeServico;
import com.test.rpinfotest.enums.OrdemDeServicoStatus;
import com.test.rpinfotest.repository.OrdemDeServicoRepository;


@WebMvcTest(OrdemDeServicoController.class)
public class OrdemDeServicoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdemDeServicoRepository mockRepository;

    @Test
    public void deveListarTodasAsOrdensDeServico() throws Exception {
        var ordensDeServicoTest = IntStream
            .range(1, 3)
            .mapToObj(index -> {
                var ordemDeServico = new OrdemDeServico();
                ordemDeServico.setId(Long.valueOf(index));

                return ordemDeServico;
            }).collect(Collectors.toList());

        when(this.mockRepository.findAll()).thenReturn(ordensDeServicoTest);

        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/ordensDeServico"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));

        verify(this.mockRepository, times(1)).findAll();
    }

    @Test
    public void deveSalvarUmaOrdemDeServicoNaBase() throws Exception {
        var ordemDeServicoTest = new OrdemDeServico();
        ordemDeServicoTest.setId(Long.valueOf(1));
        ordemDeServicoTest.setStatus(OrdemDeServicoStatus.PENDENTE);

        var cliente = new Cliente();
        cliente.setNome("Jozivardo");
        cliente.setTelefone("41 99999-8888");
        cliente.setEmail("jozivardo@gmail.com");

        var equipamento = new Equipamento();
        equipamento.setMarca("Iphone");
        equipamento.setTipo("Iphone 8");
        equipamento.setProblema("Bateria estufada");

        ordemDeServicoTest.setCliente(cliente);
        ordemDeServicoTest.setEquipamento(equipamento);
        
        when(this.mockRepository.save(ArgumentMatchers.any(OrdemDeServico.class)))
            .thenReturn(ordemDeServicoTest);

        var jsonBody = "{" +
            "\"cliente\" :" + "{" +
                "\"nome\" :" + "\"Jozivardo\"," +
                "\"telefone\" :" + "\"41 99999-8888\"," +
                "\"email\" :" + "\"jozivardo@gmail.com\"" +
            "}," +
            "\"equipamento\" :" + "{" +
                "\"marca\" :" + "\"Iphone\"," +
                "\"tipo\" :" + "\"Iphone 8\"," +
                "\"problema\" :" + "\"Bateria estufada\"" +
            "}" +
        "}";

        this.mockMvc
            .perform(MockMvcRequestBuilders.post("/ordemDeServico")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Pendente"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cliente.nome").value("Jozivardo"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cliente.telefone").value("41 99999-8888"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cliente.email").value("jozivardo@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.equipamento.marca").value("Iphone"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.equipamento.tipo").value("Iphone 8"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.equipamento.problema").value("Bateria estufada"));

        verify(this.mockRepository, times(1))
            .save(ArgumentMatchers.any(OrdemDeServico.class));
    }

    @Test
    public void deveAdicionarUmaAnotacaoNaOrdemDeServico() throws Exception {
        var ordemDeServicoTest = new OrdemDeServico();
        ordemDeServicoTest.setId(Long.valueOf(1));
        ordemDeServicoTest.setStatus(OrdemDeServicoStatus.EM_ANDAMENTO);
        
        var anotacao = new Anotacao();
        anotacao.setDescricao("nova anotacao");
        
        var anotacoes = new ArrayList<Anotacao>();
        
        anotacoes.add(anotacao);
        ordemDeServicoTest.setAnotacoes(anotacoes);

        when(this.mockRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(ordemDeServicoTest));

        when(this.mockRepository.save(ArgumentMatchers.any(OrdemDeServico.class)))
            .thenReturn(ordemDeServicoTest);


        var jsonBody = "{ \"descricao\" : \" nova anotacao\"}";

        this.mockMvc
            .perform(MockMvcRequestBuilders.post("/ordemDeServico")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Em andamento"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.anotacoes[0].descricao").value("nova anotacao"));
    }

    @Test
    public void deveIniciarAManutencao() throws Exception {
        var ordemDeServicoTest = new OrdemDeServico();
        ordemDeServicoTest.setId(Long.valueOf(1));
        ordemDeServicoTest.setStatus(OrdemDeServicoStatus.PENDENTE);

        var jsonFromBody = "{\"descricao\" : \"Iniciando a manutencao\" }";
        var anotacaoTest = new Anotacao();
        anotacaoTest.setDescricao("Iniciando a manutencao");
        
        ordemDeServicoTest.setAnotacoes(List.of(anotacaoTest));

        when(this.mockRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(ordemDeServicoTest));

        when(this.mockRepository.save(ArgumentMatchers.any(OrdemDeServico.class)))
            .thenReturn(ordemDeServicoTest);

        this.mockMvc
            .perform(MockMvcRequestBuilders
                .put("/ordemDeServico/{id}/iniciarManutencao", ordemDeServicoTest.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonFromBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Em andamento"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.anotacoes[0].descricao").value("Iniciando a manutencao"));
    }

    @Test
    public void deveFinalizarAManutencao() throws Exception {
        var ordemDeServicoTest = new OrdemDeServico();
        ordemDeServicoTest.setId(Long.valueOf(1));
        ordemDeServicoTest.setStatus(OrdemDeServicoStatus.EM_ANDAMENTO);

        var jsonFromBody = "{\"descricao\" : \"Finalizando a manutencao\" }";

        when(this.mockRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(ordemDeServicoTest));

        when(this.mockRepository.save(ArgumentMatchers.any()))
            .thenReturn(ordemDeServicoTest);

        this.mockMvc
            .perform(MockMvcRequestBuilders
                .put("/ordemDeServico/{id}/finalizarManutencao", ordemDeServicoTest.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonFromBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Concluido"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.anotacoes[0].descricao").value("Finalizando a manutencao"));
    }
}


