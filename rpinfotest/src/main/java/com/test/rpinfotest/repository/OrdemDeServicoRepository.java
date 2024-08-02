package com.test.rpinfotest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.rpinfotest.entity.OrdemDeServico;

public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Long>{

    @Query("Select ods from OrdemDeServico ods WHERE ods.status = \"Pendente\"")
    List<OrdemDeServico> buscarOrdemsDeServicoPendentes();
    
}
