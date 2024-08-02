package com.test.rpinfotest.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.test.rpinfotest.enums.OrdemDeServicoStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class OrdemDeServico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @Column(name = "ordem_de_servico_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "equipamento_id")
    private Equipamento equipamento;

    @Enumerated(EnumType.STRING)
    private OrdemDeServicoStatus status = OrdemDeServicoStatus.PENDENTE;
    private Instant inicioDaManutencao;
    private Instant fimDaManutencao;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Anotacao> anotacoes = new ArrayList<Anotacao>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public String getStatus() {
        return this.status.getDescricao();
    }

    public void setStatus(OrdemDeServicoStatus status) {
        this.status = status;
    }

    public Instant getInicioDaManutencao() {
        return inicioDaManutencao;
    }

    public void setInicioDaManutencao(Instant inicioDaManutencao) {
        this.inicioDaManutencao = inicioDaManutencao;
    }

    public Instant getFimDaManutencao() {
        return fimDaManutencao;
    }

    public void setFimDaManutencao(Instant fimDaManutencao) {
        this.fimDaManutencao = fimDaManutencao;
    }

    public List<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(List<Anotacao> anotacoes) {
        this.anotacoes = anotacoes;
    }
}
