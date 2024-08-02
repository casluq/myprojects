package com.test.rpinfotest.enums;

public enum OrdemDeServicoStatus {
    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em andamento"),
    INTERROMPIDO("Interrompido"),
    CONCLUIDO("Concluido");   

    private String descricao;

    private OrdemDeServicoStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
